package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import models.Product;
import repositories.ProductRepository;
import services.ShoppingCart;

@Controller
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartController {
	
	@Autowired 
	private ShoppingCart shoppingCart;
	
	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping(value="/add/{productId}", method=RequestMethod.GET)
	public String addToCart(@PathVariable Long productId, HttpServletRequest request){
		Product product=productRepository.findOne(productId);
		shoppingCart.addProduct(product);
		return "redirect:"+request.getHeader("Referer");
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
