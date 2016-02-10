package controllers;

import models.Product;
import models.SsdDisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import productFilters.ProductFilter;
import repositories.ProductRepository;
import specifications.SsdDiskSpecification;

/**
 * Created by bartek on 09.02.16.
 */
@Controller
@RequestMapping("/")
public class HomePageController {
    @Autowired
    private ProductRepository productRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getHomePage(Model model){

        return "home";
    }
}
