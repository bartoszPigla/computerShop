package controllers;

import javax.servlet.http.HttpServletRequest;

import models.Customer;
import models.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import models.Product;
import repositories.CustomerRepository;
import repositories.ProductOrderRepository;
import repositories.ProductRepository;
import services.ShoppingCart;

import java.security.Principal;

@Controller
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartController {
	
	@Autowired 
	private ShoppingCart shoppingCart;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductOrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@RequestMapping(value="/add/{productId}", method=RequestMethod.GET)
	public String addToCart(@PathVariable Long productId, HttpServletRequest request){
		Product product=productRepository.findOne(productId);
		shoppingCart.addProduct(product);
		return "redirect:"+request.getHeader("Referer");
	}

	@RequestMapping(value = "/orderProducts", method = RequestMethod.GET)
	public String checkout(Principal principal){
		String eMail=principal.getName();
		Customer customer=customerRepository.findByeMail(principal.getName());
		ProductOrder order=new ProductOrder();
		order.setProducts(shoppingCart.getProductList());
		order.setPurchasingCustomer(customer);
		customer.addOrder(order);
		customerRepository.save(customer);
		orderRepository.save(order);
		return "orderAcceptedPage";
	}
	
	@RequestMapping(value="/delete/{productId}", method=RequestMethod.GET)
	public String deleteFromCart(@PathVariable Long productId, HttpServletRequest request){
		shoppingCart.removeProduct(productId);
		return "redirect:"+request.getHeader("Referer");
	}
	
	@RequestMapping(value="/showAll", method=RequestMethod.GET)
	public String showAllProductsFromCart(){
		return "shoppingCartPage";
	}
}
