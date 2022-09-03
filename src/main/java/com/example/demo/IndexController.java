package com.example.demo;

import org.springframework.stereotype.Controller;

@Controller
public class IndexController {

	@GetMapping("/cliente")
	public void getCliente() {
		ModelAndView mv = new ModelAndView(viewName:"index");
		
		return mv;
	}
	
}
