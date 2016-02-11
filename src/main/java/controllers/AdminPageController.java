package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/adminPage")
public class AdminPageController {
	@RequestMapping
	public String getAdminPage() {
		return "adminPage/adminPage";
	}
}
