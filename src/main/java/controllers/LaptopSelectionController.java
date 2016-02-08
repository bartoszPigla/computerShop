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
import models.Laptop;
import productFilters.LaptopFilter;
import repositories.LaptopRepository;
import specifications.LaptopSpecification;

@Controller
@RequestMapping(value = "/laptop")
public class LaptopSelectionController {
private static final int pageSize=12;
	
	@Autowired
	private LaptopRepository laptopRepository;



	public void eraseLaptopFilter(LaptopFilter laptopFilter) {
		laptopFilter.setProducerList(laptopRepository.getLaptopProducers());
		laptopFilter.setDisplayResolutionList(laptopRepository.getDisplayResolutions());
		laptopFilter.setProcessorNameList(laptopRepository.getProcessorNames());
	}
	
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public String getProduct(@PathVariable Long productId, Model model, HttpServletRequest request){
		Laptop laptop=laptopRepository.findOne(productId);
		if(laptop==null)
			return "redirect:"+request.getHeader("Referer");
		model.addAttribute("product", laptop);
		return "productPages/laptop";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getLaptopView(Model model) {
		//laptopRepository.save(ExampleEntityGenerator.createExampleLaptopList());
		
		List<Laptop> laptopList = laptopRepository
				.findAll(new PageRequest(0, pageSize)).getContent();
		
		long count=laptopRepository.count();
		
		model.addAttribute("productList", laptopList);
		model.addAttribute("numberOfPages", count / pageSize + 1);

		LaptopFilter laptopFormFilter =new LaptopFilter();
		eraseLaptopFilter(laptopFormFilter);
		model.addAttribute("filter", laptopFormFilter);
		
		LaptopFilter laptopPaginationFilter=new LaptopFilter();
		eraseLaptopFilter(laptopPaginationFilter);
		model.addAttribute("laptopPaginationFilter", laptopPaginationFilter);
		
		return "productSelectionSites/laptop";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String filterLaptopView(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@ModelAttribute("filter") @Valid LaptopFilter laptopFormFilter, BindingResult bindingResult, @ModelAttribute("laptopPaginationFilter") LaptopFilter laptopPaginationFilter,Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/laptop";
		}
		Specification<Laptop> spec = new LaptopSpecification<Laptop>(laptopPaginationFilter);
		
		List<Laptop> laptopList = laptopRepository.findAll(spec, new PageRequest(pageNumber == null ? 0 : pageNumber - 1, pageSize)).getContent();
		
		model.addAttribute("productList", laptopList);
		model.addAttribute("numberOfPages", laptopRepository.count(spec) / pageSize + 1);
		
		model.addAttribute("laptopPaginationFilter", laptopPaginationFilter);
		
		laptopFormFilter=new LaptopFilter();
		eraseLaptopFilter(laptopFormFilter);
		model.addAttribute("filter", laptopFormFilter);

		return "productSelectionSites/laptop";
	}
}
