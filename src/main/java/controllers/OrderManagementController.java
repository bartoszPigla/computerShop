package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import models.Customer;
import models.ProductOrder;
import repositories.CustomerRepository;
import repositories.ProductOrderRepository;
import repositories.ProductRepository;

@Controller
@RequestMapping(value = "/adminPage/order")
public class OrderManagementController {
	private static final int pageSize=10;
	
	@Autowired
	ProductOrderRepository orderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@RequestMapping(value = "/add/{customerId}", method = RequestMethod.GET)
	public String addOrder(@PathVariable Long customerId, Model model){
		ProductOrder order=new ProductOrder();
		model.addAttribute("order",order);
		model.addAttribute("operation", "add");
		model.addAttribute("customerId", customerId);
		return "adminPage/orderForm";
	}
	
	@RequestMapping(value = "/add/{customerId}", method = RequestMethod.POST)
	public String saveOrder(@PathVariable Long customerId,@Valid ProductOrder order, BindingResult result, Model model,HttpServletRequest request){
		model.addAttribute("operation", "add");
		if (result.hasErrors()) {
			model.addAttribute("successfulInsertion", false);
			model.addAttribute("order",order);
			return "adminPage/orderForm";
		}
		else if(customerId==null){
			orderRepository.save(order);
		}
		else{
			Customer customer= customerRepository.findOne(customerId);
			customer.addOrder(order);
			customerRepository.save(customer);
			order.setPurchasingCustomer(customer);
			orderRepository.save(order);
		}
		model.addAttribute("successfulInsertion", true);
		return "redirect:" + request.getHeader("Referer");
	}
	
	@RequestMapping(value = "/edit/{orderId}", method = RequestMethod.GET)
	public String editOrder(@PathVariable Long orderId, Model model,HttpServletRequest request){
		ProductOrder order = orderRepository.findOne(orderId);
		if (order == null)
			return "redirect:" + request.getHeader("Referer");
		model.addAttribute("order", order);
		model.addAttribute("operation", "edit");
		return "adminPage/orderForm";
	}
	
	@RequestMapping(value = "/edit/{orderId}", method = RequestMethod.POST)
	public String saveEditedOrder(@PathVariable Long orderId, @Valid ProductOrder order, BindingResult result, Model model){
		model.addAttribute("operation", "edit");
		if (result.hasErrors()) {
			model.addAttribute("successfulEdition", false);
			model.addAttribute("order",order);
		}
		else{
			order.setId(orderId);
			orderRepository.save(order);
			model.addAttribute("successfulEdition", true);
		}
		return "adminPage/orderForm";
	}
	
	@RequestMapping(value = "/showAll", method = RequestMethod.GET)
	public String showOrders(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, Model model) {
		List<ProductOrder> orderList = orderRepository.findAll(new PageRequest((pageNumber == null) ? 0 : pageNumber - 1, pageSize))
				.getContent();

		model.addAttribute("orderList", orderList);
		model.addAttribute("numberOfPages", orderRepository.count() / pageSize + 1);
		
		return "adminPage/ordersView";
	}
	@RequestMapping(value="/showAll/forCustomer/{customerId}", method=RequestMethod.GET)
	public String showOrdersForCustomer(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,@PathVariable Long customerId, Model model){
		List<ProductOrder>  orderList= orderRepository.getOrdersForCustomer(customerId, new PageRequest((pageNumber == null) ? 0 : pageNumber - 1, pageSize)).getContent();
		model.addAttribute("orderList", orderList);
		model.addAttribute("numberOfPages", orderRepository.countOfOrdersForCustomer(customerId) / pageSize + 1);
		return "adminPage/ordersView";
	}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String deleteOrder(@PathVariable Long id, HttpServletRequest request) {
		orderRepository.delete(id);
		return "redirect:" + request.getHeader("Referer");
	}

}
