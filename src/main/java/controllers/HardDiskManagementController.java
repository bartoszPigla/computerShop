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

import models.HardDisk;
import repositories.HardDiskRepository;

@Controller
@RequestMapping(value = "/adminPage/product/hardDisk")
public class HardDiskManagementController {
	private static final int pageSize=10;
	@Autowired
	HardDiskRepository hardDiskService;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addHardDisk(Model model) {
		model.addAttribute("hardDisk", new HardDisk());
		model.addAttribute("operation", "add");
		return "adminPage/productForms/hardDisk";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveHardDisk(@Valid HardDisk hardDisk, BindingResult result, Model model) {
		model.addAttribute("operation", "add");
		if (result.hasErrors()) {
			model.addAttribute("successfulInsertion", false);
			model.addAttribute("hardDisk",hardDisk);
		}
		else{
			hardDiskService.save(hardDisk);
			model.addAttribute("successfulInsertion", true);
			model.addAttribute("hardDisk",new HardDisk());
		}
		return "adminPage/productForms/hardDisk";
	}
	
	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.POST)
	public String editHardDisk(@Valid HardDisk hardDisk, BindingResult result, @PathVariable Long productId, Model model) {
		model.addAttribute("operation", "edit");
		if (result.hasErrors()) {
			model.addAttribute("successfulEdition", false);
			model.addAttribute("hardDisk",hardDisk);
		}
		else{
			hardDisk.setId(productId);
			hardDiskService.save(hardDisk);
			model.addAttribute("successfulEdition", true);
		}
		return "adminPage/productForms/hardDisk";
	}

	@RequestMapping(value = "/showAll", method = RequestMethod.GET)
	public String editHardDisk(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, Model model) {
		List<HardDisk>list=hardDiskService.findAll(new PageRequest((pageNumber==null)?0:pageNumber-1, pageSize)).getContent();
		model.addAttribute("hardDiskList", list);
		model.addAttribute("numberOfPages", hardDiskService.count()/pageSize+1);
		return "adminPage/productsView/hardDisksView";
	}
}
