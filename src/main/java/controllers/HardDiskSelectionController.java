package controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import configurations.ExampleEntityGenerator;
import models.HardDisk;
import productFilters.HardDiskFilter;
import repositories.HardDiskRepository;
import specifications.HardDiskSpecification;

@Controller
@RequestMapping(value = "/components/disk/hardDisk")
public class HardDiskSelectionController {
	private static final int pageSize=12;
	
	@Autowired
	private HardDiskRepository hardDiskRepository;

	public void eraseHardDiskFilter(HardDiskFilter hardDiskFilter) {
		hardDiskFilter.setProducerList(hardDiskRepository.getHardDiskDiskProducers());
		hardDiskFilter.setDiskSizeList(hardDiskRepository.getHardDiskSizes());
		hardDiskFilter.setDiskInterfaceList(hardDiskRepository.getHardDiskInterfaces());
		hardDiskFilter.setRotationSpeedList(hardDiskRepository.getHardDiskRotationSpeeds());
	}
	
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public String getProduct(@PathVariable Long productId, Model model, HttpServletRequest request){
		HardDisk hardDisk=hardDiskRepository.findOne(productId);
		if(hardDisk==null)
			return "redirect:"+request.getHeader("Referer");
		model.addAttribute("product", hardDisk);
		return "productPages/hardDisk";
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String getHardDiskView(Model model) {
		//hardDiskRepository.save(ExampleEntityGenerator.createExampleHardDiskList());
		
		List<HardDisk> hardDiskList = hardDiskRepository
				.findAll(new PageRequest(0, pageSize)).getContent();
		
		long count=hardDiskRepository.count();
		
		model.addAttribute("productList", hardDiskList);
		model.addAttribute("numberOfPages", count / pageSize + 1);

		HardDiskFilter hardDiskFormFilter =new HardDiskFilter();
		eraseHardDiskFilter(hardDiskFormFilter);
		model.addAttribute("filter", hardDiskFormFilter);
		
		HardDiskFilter hardDiskPaginationFilter=new HardDiskFilter();
		eraseHardDiskFilter(hardDiskPaginationFilter);
		model.addAttribute("hardDiskPaginationFilter", hardDiskPaginationFilter);
		
		return "productSelectionSites/components/disk/hardDisk";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String filterHardDiskView(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@ModelAttribute("filter") @Valid HardDiskFilter hardDiskFormFilter, BindingResult bindingResult, @ModelAttribute("hardDiskPaginationFilter") HardDiskFilter hardDiskPaginationFilter,Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/components/disk/hardDisk";
		}
		Specification<HardDisk> spec = new HardDiskSpecification<HardDisk>(hardDiskPaginationFilter);
		
		List<HardDisk> hardDiskList = hardDiskRepository.findAll(spec, new PageRequest(pageNumber == null ? 0 : pageNumber - 1, pageSize)).getContent();
		
		model.addAttribute("productList", hardDiskList);
		model.addAttribute("numberOfPages", hardDiskRepository.count(spec) / pageSize + 1);
		
		model.addAttribute("hardDiskPaginationFilter", hardDiskPaginationFilter);
		
		hardDiskFormFilter=new HardDiskFilter();
		eraseHardDiskFilter(hardDiskFormFilter);
		model.addAttribute("filter", hardDiskFormFilter);

		return "productSelectionSites/components/disk/hardDisk";
	}
}
