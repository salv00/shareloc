package com.cdad.shareloc.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.ServiceShareloc;
import com.cdad.shareloc.dto.ServiceShareLocDTO;
import com.cdad.shareloc.repository.ColocationRepository;
import com.cdad.shareloc.repository.ServiceRepository;
import com.cdad.shareloc.repository.UserRepository;
import com.cdad.shareloc.service.ServiceShareLocService;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@CrossOrigin("*")
@RestController
public class ServiceController {

	
	@Autowired
	ServiceShareLocService sharelocService;	
	
	@Autowired
	ServiceRepository serviceRepository;
		
	@Autowired
	ColocationRepository colocationRepository;
	
	@Autowired
	UserRepository userRepository;	
	
	@Autowired
	EntityManager entityManager;
	
		
	/*
	 * Permet de creer/proposer un nouveau service
	 * WORK
	 */
	@PostMapping("/me/colocation/{colocationId}/create-service")
	public ServiceShareloc createService(@RequestBody ServiceShareloc serviceRequest, 
			@PathVariable(value = "colocationId") Long colocationId)
	{
		return sharelocService.createService(serviceRequest, colocationId);
	}
	
	/*
	 * Permet de modifier un service deja proposé
	 * WORK
	 */
	@PutMapping("/me/colocation/{colocationId}/edit-service/{serviceId}")
	public ServiceShareloc editService(@RequestBody ServiceShareloc serviceRequest,
			@PathVariable(value = "serviceId")Long serviceId,
			@PathVariable(value = "colocationId") Long colocationId)
	{
		return sharelocService.editService(serviceRequest, colocationId, serviceId);
	}

	/*
	 * Permet de supprimer un service deja proposé
	 * WORK
	 */
	@DeleteMapping("/me/colocation/{colocationId}/delete-service/{serviceId}")
	public String deleteService(
			@PathVariable(value = "serviceId")Long serviceId,
			@PathVariable(value = "colocationId") Long colocationId)
	{		
		sharelocService.deleteService(colocationId, serviceId);
		
		return "You have been deleted service with id : " + serviceId;
	}
	
	
	/*
	 * Permet de recuperer la liste des services rattaches à l'user
	 * WORK
	 */
	@GetMapping("me/my-list-services")
	public List<ServiceShareLocDTO> findListServicesByUserId()
	{
		return sharelocService.findServiceByUserId();
	}
	
	/*
	 * Permet de recuperer la liste des services des colocations de l'user
	 */
	@GetMapping("me/list-services-of-my-colocations")
	public List<ServiceShareLocDTO> findListServicesByColocationIdOfUser()
	{
		return sharelocService.findServiceByColocationId();
	}
	
	
	
	
	
	@GetMapping("list-all-services")
	public List<ServiceShareloc> findListAllServices()
	{
		
		
		return null;
	}
	
	
	
	
	
	
	
	
}
