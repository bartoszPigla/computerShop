package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import models.Customer;
import models.Product;
import models.ProductOrder;
import repositories.CustomerRepository;
import repositories.ProductOrderRepository;
import repositories.ProductRepository;

@Controller
@RequestMapping(value = "/adminPage/product")
public class ProductManagementController {
	private static final int 
		pageSize = 10;
	
	private static String getProductType(Product product){
		String productTypeName=product.getClass().getSimpleName();
		char c[] = productTypeName.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		return new String(c);
	}
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired 
	private CustomerRepository customerRepository;
	
	@Autowired 
	private ProductOrderRepository orderRepository;
	
	@RequestMapping(value="/showAll", method=RequestMethod.GET)
	public String showAllProducts(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, Model model){
		List<Product> productList=productRepository.findAll(new PageRequest((pageNumber == null) ? 0 : pageNumber - 1, pageSize)).getContent();
		model.addAttribute("productList", productList);
		long numberOfPages =  productRepository.count()/ pageSize + 1;
		model.addAttribute("numberOfPages", numberOfPages);
		return  "adminPage/productsView";
	}
	
	@RequestMapping(value="/showAll/forOrder/{orderId}", method=RequestMethod.GET)
	public String showProductsForOrder(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,@PathVariable Long orderId, Model model){
		ProductOrder order=orderRepository.findOne(orderId);
		List<Product> productList=productRepository.getProductByOrder(order,new PageRequest((pageNumber == null) ? 0 : pageNumber - 1, pageSize)).getContent();
		model.addAttribute("productList", productList);
		long numberOfPages =  productRepository.countOfProductsFromOrder(order)/ pageSize + 1;
		model.addAttribute("numberOfPages", numberOfPages);
		return  "adminPage/productsView";
	}
	
	@RequestMapping(value="/showAll/forCustomer/{customerId}", method=RequestMethod.GET)
	public String showProductsForCustomer(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,@PathVariable Long customerId, Model model){		
		List<Product> productList=productRepository.getProductsFromCustomer(customerId,new PageRequest((pageNumber == null) ? 0 : pageNumber - 1, pageSize)).getContent();
		
		model.addAttribute("productList", productList);
		long numberOfPages=productRepository.countOfProductsFromCustomer(customerId)/pageSize + 1;
		model.addAttribute("numberOfPages", numberOfPages);
		return "adminPage/productsView";
	}
	
	@RequestMapping(value="/edit/{productId}", method=RequestMethod.GET)
	public String editProduct(@PathVariable Long productId, Model model, HttpServletRequest request){
				Product productToEdit=null;
		if(productId==null || (productToEdit=productRepository.findOne(productId))==null)
			return "redirect:" + request.getHeader("Referer");
		else{
			String productTypeName=getProductType(productToEdit);
			model.addAttribute("operation", "edit");
			model.addAttribute(productTypeName,productToEdit);
			return "adminPage/productForms/"+productTypeName;
		}
	}
	
	@RequestMapping(value="/showAll/forCustomer/{customerId}/{productId}", method=RequestMethod.DELETE)
	public String deleteProductFromAllOrdersForCustomer(@PathVariable Long customerId, @PathVariable Long productId, HttpServletRequest request){
		Customer customer=customerRepository.findOne(customerId);		
		Product productToDelete=productRepository.findByProductIdAndFetchOrdersEagerly(productId);
		List<ProductOrder> productOrders=productToDelete.getOrders();
		
		ProductOrder order=null;
		while(productOrders.size()!=0){
			order=productOrders.get(0);
			if(order.getPurchasingCustomer().equals(customer)){
				order=orderRepository.findOrderByIdAndFetchProductsEagerly(order.getId());
				order.removeProduct(productToDelete);
				orderRepository.save(order);
				productRepository.save(productToDelete);				
			}
			System.out.println();
		}
		
		return "redirect:"+request.getHeader("Referer");
	}
	@RequestMapping(value="/showAll/forOrder/{orderId}/{productId}", method=RequestMethod.DELETE)
	public String deleteProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId, HttpServletRequest request){
		ProductOrder order=orderRepository.findOrderByIdAndFetchProductsEagerly(orderId);
		Product product = productRepository.findByProductIdAndFetchOrdersEagerly(productId);
		if(order!=null&&product!=null){
			order.removeProduct(product);
			productRepository.save(product);
			orderRepository.save(order);
		}
		return "redirect:"+request.getHeader("Referer");
	}
}
