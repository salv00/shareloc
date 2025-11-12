package com.cdad.shareloc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cdad.shareloc.repository.ColocationRepository;
import com.cdad.shareloc.repository.ServiceRepository;

@Controller
public class HomeController {

	@Autowired
	ColocationRepository colocationRepository;
	
	@Autowired
	ServiceRepository serviceRepository;
	
	
	@GetMapping("/homepage")
	public String homePage() {
		return "index";
	}
	
}
