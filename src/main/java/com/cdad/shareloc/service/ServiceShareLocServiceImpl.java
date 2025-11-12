package com.cdad.shareloc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.ServiceShareloc;
import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.dto.ServiceShareLocDTO;

import org.springframework.stereotype.Service;
import com.cdad.shareloc.exception.ResourceNotFoundException;
import com.cdad.shareloc.repository.ColocationRepository;
import com.cdad.shareloc.repository.ServiceRepository;
import com.cdad.shareloc.repository.UserRepository;
import com.cdad.shareloc.status.StatusService;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */


@Service
public class ServiceShareLocServiceImpl implements ServiceShareLocService{

	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	ColocationRepository colocationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private StatusService statusService;
	
	
	/*
	 * Permet de creer/proposer un service
	 */
	@Override
	public ServiceShareloc createService(ServiceShareloc serviceRequest, Long colocationId) {
		
		//Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		//recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();
		
		//Permet de rattacher ce service à un user
		ServiceShareloc service_userId = userRepository.findById(currentUserConnetedID).map(user -> {
			
			serviceRequest.setUser(user);
			
			//Permet de set l'id de l'user qui beneficie du service
			//La personne qui beneficie du service est automatiquement la personne qui cree/propose un service
			user.addService(serviceRequest);
			
			return serviceRepository.save(serviceRequest);

		}).orElseThrow(() -> new ResourceNotFoundException("Not found user with userId = " + currentUserConnetedID));
		
		//Et permet de rattacher ce service à une colocation egalemnt
		ServiceShareloc service = colocationRepository.findById(colocationId).map(colocation -> {
			
			serviceRequest.setColocation(colocation);
			
			//Permet d'affecter à ce service le statut attende de validation de la creation
			serviceRequest.setStatusService(statusService.WAITING_FOR_VALIDATION.name());
			
			return serviceRepository.save(serviceRequest);
	
		}).orElseThrow(() -> new ResourceNotFoundException("Not found with colocationId = " + colocationId));

		
		return service;
	}

	/*
	 * Permet de modifier les informations d'un service
	 */
	@Override
	public ServiceShareloc editService(ServiceShareloc serviceRequest,Long colocationId, Long serviceId) {

		//Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		//recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();
		
		
		User user = userRepository.findById(currentUserConnetedID)
				.orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + currentUserConnetedID));
		
		Colocation colocation = colocationRepository.findById(colocationId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found colocation with id = " + colocationId));
		
		ServiceShareloc service = serviceRepository.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found service with id = " + serviceId));
		
		service.setTitle(serviceRequest.getTitle());
		service.setDescription(serviceRequest.getDescription());
		service.setCost(serviceRequest.getCost());	
		
		serviceRepository.save(service);
		
		return service;
	}


	/*
	 * Permet de supprimer un service
	 */
	@Override
	public void deleteService(Long colocationId, Long serviceId) {

		//Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		//recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();
		
		
		
		User user = userRepository.findById(currentUserConnetedID)
				.orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + currentUserConnetedID));
		
		Colocation colocation = colocationRepository.findById(colocationId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found colocation with id = " + colocationId));
		
		ServiceShareloc service = serviceRepository.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found service with id = " + serviceId));
		
		user.removeService(serviceId);
		userRepository.save(user);
		
		//Permet de set le statut supprime au service une fois supprimé
		service.setStatusService(statusService.DELETED.name());
		serviceRepository.save(service);
	}

	
	/*
	 * PErmet de recuperer la liste des services qui sont rattachés à l'user
	 */
	@Override
	public List<ServiceShareLocDTO> findServiceByUserId() {

		//Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		//recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();
		
		  List<ServiceShareloc> listServicesAttachedUser = serviceRepository.findServiceByUserId(currentUserConnetedID);
		  
		// Transformez la liste de ServiceShareloc en une liste de ServiceShareLocDTO
		  List<ServiceShareLocDTO> listServices = new ArrayList<>();
		  for (ServiceShareloc service : listServicesAttachedUser) {
			  listServices.add(new ServiceShareLocDTO(service.getId(), service.getTitle(), service.getDescription(),
		      service.getCost(),service.getStatusService() ,service.getColocation().getId(), service.getColocation().getName()));
		  }

		
		return listServices;
	}

	/*
	 * Recupere la liste des services de tous mes colocations
	 */
	@Override
	public List<ServiceShareLocDTO> findServiceByColocationId() {
		
		//Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		
		List<ServiceShareLocDTO> listServicesByUserColocations = new ArrayList<>();

		
		
		currentUserConnected.getColocations().forEach(colocation -> {

		      List<ServiceShareloc> services = serviceRepository.findServiceByColocationId(colocation.getId());
		      
		      List<ServiceShareLocDTO> servicesDTO = services.stream().map(service -> {
		    	  
		            ServiceShareLocDTO serviceDTO = new ServiceShareLocDTO();
		            serviceDTO.setId(service.getId());
		            serviceDTO.setTitle(service.getTitle());
		            serviceDTO.setDescription(service.getDescription());
		            serviceDTO.setCost(service.getCost());
		            serviceDTO.setStatusService(service.getStatusService());
		            serviceDTO.setColocationId(service.getColocation().getId());
		            serviceDTO.setNameColocation(service.getColocation().getName());
		            return serviceDTO;

		      }).collect(Collectors.toList());


		      listServicesByUserColocations.addAll(servicesDTO);
		});


		
		return listServicesByUserColocations;
	}

	
	
	
	
}
