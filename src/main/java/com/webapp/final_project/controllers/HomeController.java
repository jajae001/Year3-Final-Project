package com.webapp.final_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/index")
	public String getindex() {
		
		return "index";
	}
	@GetMapping("/about")
	public String getabout() {

		return "about";
	}
	@GetMapping("/birthmonth")
	public String getbirthmonth() {

		return "birthmonth";
	}
	@GetMapping("/horoscope")
	public String gethoroscope() {

		return "horoscope";
	}
	@GetMapping("/contactus")
	public String getcontactus() {

		return "contactus";
	}

}

