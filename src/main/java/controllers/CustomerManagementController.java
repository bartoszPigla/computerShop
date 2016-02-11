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
import repositories.CustomerRepository;

@Controller
@RequestMapping(value = "/adminPage/customer")
public class CustomerManagementController {
	private static final int 
		pageSize = 10;
	private static final String 
		addCustomer = "addCustomer",
		editCustomer = "editCustomer";
	
	@Autowired
	CustomerRepository customerService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addCustomer(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		model.addAttribute("operation", "add");
		return "adminPage/customerForm";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveCustomer(@Valid Customer customer, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("operation", "add");
			model.addAttribute("successfulEdition", false);
			return "adminPage/customerForm";
		}
		customerService.save(customer);
		return "adminPage/customerForm";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editCustomer(@PathVariable Long id, Model model, HttpServletRequest request) {
		Customer customer = customerService.findOne(id);
		if (customer == null)
			return "redirect:" + request.getHeader("Referer");
		model.addAttribute("customer", customer);
		model.addAttribute("operation", "edit");
		return "adminPage/customerForm";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveEditedCustomer(@PathVariable Long id, @Valid Customer customer, BindingResult result, Model model, HttpServletRequest request) {
		model.addAttribute("operation", "edit");
		if (result.hasErrors()) {
			model.addAttribute("successfulEdition", false);
		}
		else{
			customer.setId(id);
			customerService.save(customer);
			model.addAttribute("successfulEdition", true);
		}
		return "adminPage/customerForm";
	}

	@RequestMapping(value = "/showAll", method = RequestMethod.GET)
	public String showCustomers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, Model model) {

		List<Customer> list= customerService.findAll(new PageRequest((pageNumber == null) ? 0 : pageNumber - 1, pageSize))
				.getContent();

		model.addAttribute("customerList", list);
		model.addAttribute("numberOfPages", customerService.count() / pageSize + 1);
		return "adminPage/customersView";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String deleteCustomer(@PathVariable Long id, Model model, HttpServletRequest request) {
		customerService.delete(id);
		return "redirect:" + request.getHeader("Referer");
	}
}
