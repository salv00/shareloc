package com.cdad.shareloc.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cdad.shareloc.dao.AchievedService;
import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.dto.AchievedServiceDto;
import com.cdad.shareloc.repository.AchievedServiceRepository;
import com.cdad.shareloc.repository.UserRepository;
import com.cdad.shareloc.service.AchievedServiceService;
import com.cdad.shareloc.service.CsvService;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */


@RestController
public class AchievedServiceController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	AchievedServiceService achievedServiceService;
	
	@Autowired
	private AchievedServiceRepository achievedServiceRepository;
	
	@Autowired
	CsvService csvService;
	
	
	
	
	
	/*
	 * Permet d'achevier un service
	 */
	//@PostMapping("colocation/{colocationId}/service/{serviceId}/achieveService/user/{userId}")
	@RequestMapping(value = "colocation/{colocationId}/service/{serviceId}/achieveService/me" , 
	method = RequestMethod.POST)
	public AchievedService achieveTheService(
			@RequestParam("file") final MultipartFile file,
			@PathVariable("colocationId") Long colocationId, 
			@PathVariable("serviceId")Long serviceId)
	{
		
		return achievedServiceService.createAchiviedService(file , colocationId, serviceId);
	}
	
	/*
	 * Permet à un membre de la colocation de valider la fin d'un service
	 * Demande en body :
	 * valid : yes,Yes,YES,oui,Oui,OUI, not,Not,NOT,non,Non,NON
	 */
	@PostMapping("colocation/{colocationId}/valid-achieved-service/{achievedServiceId}/me")
	public AchievedService validTheAchievedService(@RequestBody AchievedService achievedServiceRequest,
			@PathVariable("colocationId")Long colocationId,
			@PathVariable("achievedServiceId")Long achievedServiceId)
	{
		return achievedServiceService.validateAchievedService(achievedServiceRequest, colocationId, achievedServiceId);	
	}
	
	
	@GetMapping("me/list-achievedService-of-my-colocation")
	public List<AchievedServiceDto> getListAchievedService()
	{	
		return achievedServiceService.findAchievedServicesOfMyColocation();
	}
	
	
	
	
	
	/*
	 * Generation du rapport des achieved services avec info de l'user et ses points cumulés
	 */
	@GetMapping("me/achievedService/export-rapport-csv")
	public void getAchievedServiceByUserIdCSV(HttpServletResponse response) 
			throws IOException
	{
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);
				
		//recupere le nom de la personne actuellement connectée
		String currentUserConnetedName = currentUserConnected.getFirstname() + "_" + currentUserConnected.getLastname();

		
		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		String currentDateTime = dateFormatter.format(new Date());
		
		String nomFichier = "Rapport_Des_Services_Rendu_" + currentUserConnetedName + "_";
		
		
		response.setContentType("text/csv; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-disposition", "attachement;filename=" + nomFichier + currentDateTime + ".csv");
		
		
		csvService.writerCsvAchievedServices();
	}
	
	
	
	

}
