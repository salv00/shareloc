package com.cdad.shareloc.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cdad.shareloc.ImageUtil.ImageUtil;
import com.cdad.shareloc.dao.AchievedService;
import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.ServiceShareloc;
import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.dto.AchievedServiceDto;
import com.cdad.shareloc.dto.ServiceShareLocDTO;
import com.cdad.shareloc.exception.CustomUnauthorizedResponseStatus;
import com.cdad.shareloc.exception.ResourceNotFoundException;
import com.cdad.shareloc.repository.AchievedServiceRepository;
import com.cdad.shareloc.repository.ColocationRepository;
import com.cdad.shareloc.repository.ServiceRepository;
import com.cdad.shareloc.repository.UserRepository;
import com.cdad.shareloc.status.StatusAchievedService;
import com.cdad.shareloc.status.StatusService;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Service
public class AchiviedServiceServiceImpl implements AchievedServiceService{
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ColocationRepository colocationRepository;
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private AchievedServiceRepository achievedServiceRepository;
	
	StatusAchievedService statusAchievedService;
	
	StatusService statusService;
	

	
	/*
	 * Creation d'un achievedService avec :
	 *  l'upload de l'image,
	 *  le set de l'user qui fait le service
	 *  le set de la colocation qui beneficie du service
	 *  la date et l'heure du moment que le service à ete declaré fini
	 *  le status valid en attente de validation
	 */
	@Override
	public AchievedService createAchiviedService(MultipartFile file ,Long colocationIdBenefitToService,
			Long serviceId) {
		
		//Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		//recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();
		
		
		AchievedService achievedServiceRequest = new AchievedService();

		/*
		 * Si le status 'valid' est null alors set en attente de validation
		 */
		if(achievedServiceRequest.getValid() == null)
		{
			achievedServiceRequest.setValid(statusAchievedService.WAITING_FOR_VALIDATION.name());
			achievedServiceRepository.save(achievedServiceRequest);
		}
		
			/*
			 * Dans la table AchievedService, colonne fromm-userId, j'enregistre l'user qui fait le service 
			 */
			AchievedService achievedServiceUserMake = userRepository.findById(currentUserConnetedID).map(user -> {
				
				achievedServiceRequest.setUser(user);
				
				return achievedServiceRepository.save(achievedServiceRequest);
				
			}).orElseThrow(() -> new ResourceNotFoundException("Not find user with userId : " + currentUserConnetedID));
					
			/*
			 * Dans la table AchievedService, colonne service_id, j'enregistre le service terminé
			 */
			AchievedService achievedServiceId = serviceRepository.findById(serviceId).map(service -> {
				
				achievedServiceRequest.setServiceShareloc(service);
				
				return achievedServiceRepository.save(achievedServiceRequest);
				
			}).orElseThrow(() -> new ResourceNotFoundException("Not find service with serviceId : " + serviceId));
		
			/*
			 * Dans la table AchievedService, colonne to-colocation_id, j'enregistre la colocation qui beneficie du service
			 * parce tous les users presents dans la colocation doivent beneficier du service 
			 */
			AchievedService achievedServiceColocationBenefit = colocationRepository.findById(colocationIdBenefitToService).map(colocation -> {
			
				achievedServiceRequest.setColocation(colocation);
			
				return achievedServiceRepository.save(achievedServiceRequest);
			
			}).orElseThrow(() -> new ResourceNotFoundException("Not find colocation with colocationId : " + colocationIdBenefitToService));
		
			/*
			 * On set la date et l'heure que le service à été declaré fait
			 */
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime currentLocalDateTime = LocalDateTime.now();
			
			achievedServiceRequest.setDate(currentLocalDateTime);
			achievedServiceRepository.save(achievedServiceRequest);
						
			/*
			 * Set de l'image dans la bdd
			 */
			try 
			{
				achievedServiceRequest.setPicture(ImageUtil.compressImage(file.getBytes()));
				achievedServiceRepository.save(achievedServiceRequest);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
		return achievedServiceRequest;
	}


	
	/*
	 * Permet à un membre de la colocation de valider un service declaré achieved
	 */
	@Override
	public AchievedService validateAchievedService(AchievedService achievedServiceRequest, Long colocationId,
			Long achievedServiceId) {

		//Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		//recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();
		
		
		/*
		 * Je verifie que les id presents en parametre existent bien dans la bdd
		 * Sinon je renvoie une erreur
		 */
			User userVerif = userRepository.findById(currentUserConnetedID)
					.orElseThrow(() -> new ResourceNotFoundException("Not find user with id : " + currentUserConnetedID));
			
			Colocation colocationVerif = colocationRepository.findById(colocationId)
					.orElseThrow(() -> new ResourceNotFoundException("Not find colocation with id : " + colocationId));

			AchievedService achievedServiceVerif = achievedServiceRepository.findById(achievedServiceId)
				.orElseThrow(() -> new ResourceNotFoundException("Not find achieved service with id : " + achievedServiceId));
		
			
				//Recupere la liste des users presents dans la colocation à partir de l'id de la colocation
				List<User> listUsersPresentsInColocation = userRepository.findUsersByColocationsId(colocationId);
				
				//Verifie si l'id de l'user qui à été renseigné dans la route est bien present dans la coloc qui est renseigne dans la route
				boolean userIsPresentInColocation = listUsersPresentsInColocation.contains(userVerif);
				
				//Recupere la liste des achievedService que sont associées à l'id de la colocation
				List<AchievedService> listAchievedServicesByColocationId = achievedServiceRepository.findAchievedServiceByColocationId(colocationId);
				
				//Verifie si l'id de l'achievedService que l'on tente de valider est bien associés à cette colocation
				boolean achievedServiceIsAssociedColocation = listAchievedServicesByColocationId.contains(achievedServiceVerif);
				
				//Verifie que l'user qui tente de valider l'achievedService fait bien parti de la colocation 
				//ET
				//Si l'achievedService est bien associes à cette colocation
				if(userIsPresentInColocation == true && achievedServiceIsAssociedColocation == true)
				{ 
					
					//Si le service est deja validé il ne pourra plus etre validé
					if(!achievedServiceVerif.getValid().contentEquals("YES")
					  || achievedServiceVerif.getValid().contentEquals("NOT"))
					{
						if(achievedServiceRequest.getValid().contentEquals("yes") 
								|| achievedServiceRequest.getValid().contentEquals("Yes")
								|| achievedServiceRequest.getValid().contentEquals("YES")
								|| achievedServiceRequest.getValid().contentEquals("oui")
								|| achievedServiceRequest.getValid().contentEquals("Oui")
								|| achievedServiceRequest.getValid().contentEquals("OUI"))
						{
							
							achievedServiceVerif.setValid(statusAchievedService.YES.name());
							achievedServiceRepository.save(achievedServiceVerif);
							
							//Je recupere l'id du service associé avec achievedService
							long idServiceByAchievedService = achievedServiceVerif.getServiceShareloc().getId();
							
							//Je cherche dans la BDD le service à partir de l'id service present dans achievedService
							ServiceShareloc service = serviceRepository.findById(idServiceByAchievedService).get();
							
							//Je set le status du service concerné à 'ACHIVIED'
							service.setStatusService(statusService.ACHIEVED.name());
							serviceRepository.save(service);
							
							
							//Recupere le nombre de point associes au service
							long nbPointAssociedService = service.getCost();
							
							long nbPointUserHasAccumuled;
							
							//Recupere le nombre de points accumulés par l'user
							if(userVerif.getAccumulated_points() == null)
							{
								nbPointUserHasAccumuled = 0;
							}
							else 
							{
								nbPointUserHasAccumuled = userVerif.getAccumulated_points();
							}
							
							//Ajoute le nombre de point associées au service à l'user qui fait le service
							userVerif.setAccumulated_points(nbPointUserHasAccumuled + nbPointAssociedService);
							
							userRepository.save(userVerif);
						}
						
						else if(achievedServiceRequest.getValid().contentEquals("not")
								|| achievedServiceRequest.getValid().contentEquals("Not")
								|| achievedServiceRequest.getValid().contentEquals("NOT")
								|| achievedServiceRequest.getValid().contentEquals("non")
								|| achievedServiceRequest.getValid().contentEquals("Non")
								|| achievedServiceRequest.getValid().contentEquals("NON"))
						{
							achievedServiceVerif.setValid(statusAchievedService.NOT.name());
							achievedServiceRepository.save(achievedServiceVerif);
						}
						
						else 
						{
							achievedServiceVerif.setValid(statusAchievedService.INCONNU.name());
							achievedServiceRepository.save(achievedServiceVerif);
						}
					}
					else
					{
						throw new CustomUnauthorizedResponseStatus("UNAUTHORIZED : this achievedService is alredy validate achievedServiceId : " + achievedServiceId ); 
					}
				}
				else
				{
					throw new CustomUnauthorizedResponseStatus("UNAUTHORIZED : you are not part of this colocation or the achievedService is not associated with this colocation : " 
															   + "colocationId : " + colocationId 
															   + " or "
															   + "achievedServiceId : " + achievedServiceId);
				}
		
		return achievedServiceVerif;
	}



	@Override
	public AchievedService findById(Long id) {

		return achievedServiceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found achieviedService with id : " + id));
	}



	@Override
	public List<AchievedServiceDto> findAchievedServicesOfMyColocation() {
		
		//Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		List<AchievedServiceDto> listAchievedServicesMyColocations = new ArrayList<>();
		
		currentUserConnected.getColocations().forEach(colocation -> {
			
		      List<AchievedService> achievedServices = achievedServiceRepository.findAchievedServiceByColocationId(colocation.getId());

		      List<AchievedServiceDto> achievedServicesDTO = achievedServices.stream().map(achievedService -> {
		    	  
		    	  AchievedServiceDto achievedServiceDto = new AchievedServiceDto();
		    	  achievedServiceDto.setId(achievedService.getId());
		    	  String dateRealisedService = achievedService.getDate().toString();
		    	  DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
		    	  LocalDateTime date = LocalDateTime.parse(dateRealisedService, formatter1);
		    	  DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    	  String formattedDate = date.format(formatter2);
		    	  achievedServiceDto.setDate(formattedDate);
		    	  achievedServiceDto.setPicture(ImageUtil.decompressImage(achievedService.getPicture()));
		    	  achievedServiceDto.setValid(achievedService.getValid());
		    	  achievedServiceDto.setServiceId(achievedService.getServiceShareloc().getId());
		    	  achievedServiceDto.setNameService(achievedService.getServiceShareloc().getTitle());
		    	  achievedServiceDto.setCostService(achievedService.getServiceShareloc().getCost());
		    	  achievedServiceDto.setUserId(achievedService.getUser().getId());
		    	  achievedServiceDto.setEmail(achievedService.getUser().getEmail());
		    	  achievedServiceDto.setFirstName(achievedService.getUser().getFirstname());
		    	  achievedServiceDto.setLastName(achievedService.getUser().getLastname());
		    	  achievedServiceDto.setColocationId(achievedService.getColocation().getId());
		    	  achievedServiceDto.setNameColocation(achievedService.getColocation().getName());
		    	  return achievedServiceDto;
		    	  
		      }).collect(Collectors.toList());
			
		      listAchievedServicesMyColocations.addAll(achievedServicesDTO);
		});

		return listAchievedServicesMyColocations;
	}
}
