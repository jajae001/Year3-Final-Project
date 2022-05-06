package com.webapp.final_project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FinalProjectApplicationController {
    
 @GetMapping("/index")
    public String index() {

		return "index";
	}
  @GetMapping("/birthmonth")
  public String birthmonth() {

      return "birthmonth";
  }
  @GetMapping("/contactus")
  public String contactus() {

      return "contactus";
  }
  @GetMapping("/horoscope")
  public String horoscope() {

      return "horoscope";
  }
  @GetMapping("/terms")
  public String terms() {

      return "terms";
  }
}
