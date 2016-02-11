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
import models.SsdDisk;
import productFilters.SsdDiskFilter;
import repositories.SsdDiskRepository;
import specifications.SsdDiskSpecification;

@Controller
@RequestMapping(value = "/components/disk/ssdDisk")
public class SsdDiskSelectionController {
	private static final int pageSize = 12;
	@Autowired
	private SsdDiskRepository ssdDiskRepository;
	@Autowired
	private static Specification<SsdDisk> spec;

	public void eraseSsdDiskFilter(SsdDiskFilter ssdDiskFilter) {
		ssdDiskFilter.setProducerList(ssdDiskRepository.getSsdDiskProducers());
		ssdDiskFilter.setDiskSizeList(ssdDiskRepository.getSsdDiskSizes());
		ssdDiskFilter.setDiskInterfaceList(ssdDiskRepository.getSsdDiskInterfaces());
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public String getProduct(@PathVariable Long productId, Model model, HttpServletRequest request){
		SsdDisk ssdDisk=ssdDiskRepository.findOne(productId);
		if(ssdDisk==null)
			return "redirect:"+request.getHeader("Referer");
		model.addAttribute("product", ssdDisk);
		return "productPages/ssdDisk";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getSsdDiskView(Model model) {
		//ssdDiskRepository.save(ExampleEntityGeneratorcreateExampleDiskList());
		
		List<SsdDisk> ssdDiskList = ssdDiskRepository
				.findAll(new PageRequest(0, pageSize)).getContent();
		
		model.addAttribute("productList", ssdDiskList);
		model.addAttribute("numberOfPages", ssdDiskRepository.count(spec) / pageSize + 1);

		SsdDiskFilter ssdDiskFormFilter =new SsdDiskFilter();
		eraseSsdDiskFilter(ssdDiskFormFilter);
		model.addAttribute("filter", ssdDiskFormFilter);
		
		SsdDiskFilter ssdDiskPaginationFilter=new SsdDiskFilter();
		eraseSsdDiskFilter(ssdDiskPaginationFilter);
		model.addAttribute("ssdDiskPaginationFilter", ssdDiskPaginationFilter);
		
		return "productSelectionSites/components/disk/ssdDisk";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String filterSsdDiskView(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@ModelAttribute("filter") @Valid SsdDiskFilter ssdDiskFormFilter, BindingResult bindingResult, @ModelAttribute("ssdDiskPaginationFilter") SsdDiskFilter ssdDiskPaginationFilter,Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/components/disk/ssdDisk";
		}
		Specification<SsdDisk> spec = new SsdDiskSpecification<>(ssdDiskPaginationFilter);
		List<SsdDisk> ssdDiskList = ssdDiskRepository
				.findAll(spec, new PageRequest(pageNumber == null ? 0 : pageNumber - 1, pageSize)).getContent();

		model.addAttribute("productList", ssdDiskList);
		model.addAttribute("numberOfPages", ssdDiskRepository.count(spec) / pageSize + 1);
		
		model.addAttribute("ssdDiskPaginationFilter", ssdDiskPaginationFilter);
		
		ssdDiskFormFilter=new SsdDiskFilter();
		eraseSsdDiskFilter(ssdDiskFormFilter);
		model.addAttribute("filter", ssdDiskFormFilter);

		return "productSelectionSites/components/disk/ssdDisk";
	}
}
