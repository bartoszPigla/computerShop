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

import models.Laptop;
import repositories.LaptopRepository;

@Controller
@RequestMapping(value = "/adminPage/product/laptop")
public class LaptopManagementController {
	private static final int pageSize=10;
	@Autowired
	LaptopRepository laptopService;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addLaptop(Model model) {
		model.addAttribute("laptop", new Laptop());
		model.addAttribute("operation", "add");
		return "adminPage/productForms/laptop";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveLaptop(@Valid Laptop laptop, BindingResult result, Model model) {
		model.addAttribute("operation", "add");
		if (result.hasErrors()) {
			model.addAttribute("successfulInsertion", false);
			model.addAttribute("laptop",laptop);
		}
		else{
			laptopService.save(laptop);
			model.addAttribute("successfulInsertion", true);
			model.addAttribute("laptop",new Laptop());
		}
		return "adminPage/productForms/laptop";
	}
	
	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.POST)
	public String editLaptop(@Valid Laptop laptop, BindingResult result, @PathVariable Long productId, Model model) {
		model.addAttribute("operation", "edit");
		if (result.hasErrors()) {
			model.addAttribute("successfulEdition", false);
			model.addAttribute("laptop",laptop);
		}
		else{
			laptop.setId(productId);
			laptopService.save(laptop);
			model.addAttribute("successfulEdition", true);
		}
		return "adminPage/productForms/laptop";
	}

	@RequestMapping(value = "/showAll", method = RequestMethod.GET)
	public String editLaptop(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, Model model) {
		List<Laptop>list=laptopService.findAll(new PageRequest((pageNumber==null)?0:pageNumber-1, pageSize)).getContent();
		model.addAttribute("laptopList", list);
		model.addAttribute("numberOfPages", laptopService.count()/pageSize+1);
		return "adminPage/productsView/laptopsView";
	}
}
