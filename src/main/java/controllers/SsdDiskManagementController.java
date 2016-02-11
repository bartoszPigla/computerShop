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

import models.SsdDisk;
import repositories.CustomerRepository;
import repositories.ProductOrderRepository;
import repositories.SsdDiskRepository;

@Controller
@RequestMapping(value = "/adminPage/product/ssdDisk")
public class SsdDiskManagementController {
	private static final int pageSize=10;
	@Autowired
	SsdDiskRepository ssdDiskService;
	@Autowired
	CustomerRepository customerService;
	@Autowired
	ProductOrderRepository orderService;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addSsdDisk(Model model) {
		model.addAttribute("ssdDisk", new SsdDisk());
		model.addAttribute("operation", "add");
		return "adminPage/productForms/ssdDisk";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveSsdDisk(@Valid SsdDisk ssdDisk, BindingResult result, Model model) {
		model.addAttribute("operation", "add");
		if (result.hasErrors()) {
			model.addAttribute("successfulInsertion", false);
			model.addAttribute("ssdDisk",ssdDisk);
		}
		else{
			ssdDiskService.save(ssdDisk);
			model.addAttribute("successfulInsertion", true);
			model.addAttribute("ssdDisk",new SsdDisk());
		}
		return "adminPage/productForms/ssdDisk";
	}
	
	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.POST)
	public String editSsdDisk(@Valid SsdDisk ssdDisk, BindingResult result, @PathVariable Long productId, Model model) {
		model.addAttribute("operation", "edit");
		if (result.hasErrors()) {
			model.addAttribute("successfulEdition", false);
			model.addAttribute("ssdDisk",ssdDisk);
			return "adminPage/productForms/ssdDisk";
		}
		else{
			ssdDisk.setId(productId);
			ssdDiskService.save(ssdDisk);
			model.addAttribute("successfulEdition", true);
		}
		return "adminPage/productForms/ssdDisk";
	}

	@RequestMapping(value = "/showAll", method = RequestMethod.GET)
	public String editSsdDisk(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, Model model) {
		List<SsdDisk>list=ssdDiskService.findAll(new PageRequest((pageNumber==null)?0:pageNumber-1, pageSize)).getContent();
		model.addAttribute("ssdDiskList", list);
		model.addAttribute("numberOfPages", ssdDiskService.count()/pageSize+1);
		return "adminPage/productsView/ssdDisksView";
	}
}
