package br.edu.fateczl.agisSpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
	public ModelAndView get() {
		return new ModelAndView("index");
	}
}
