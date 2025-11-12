package com.cdad.shareloc.service;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.UserRegistryMessageHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cdad.shareloc.controller.ColocationController;
import com.cdad.shareloc.dao.AchievedService;
import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.repository.AchievedServiceRepository;
import com.cdad.shareloc.repository.UserRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Service
public class CsvService {

	
	private static final Logger logger = LoggerFactory.getLogger(CsvService.class);	
	
	@Autowired
	private AchievedServiceRepository achievedServiceRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
    private static final String RAPPORT_USER_ACHIEVED_SERVICE_CSV_FILE = "src/main/resources/Rapport-Achieved-Services/Rapport_User_Achieved_Services.csv";

	
	public void writerCsvAchievedServices()
	{
		try(
	            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(RAPPORT_USER_ACHIEVED_SERVICE_CSV_FILE));

				CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, 
				CSVFormat.DEFAULT
				.withDelimiter(';')
				.withHeader("ID_Achieved_Service", "Valid", "Date_Valid", "User_Firstname", "User_Lastname", "User_Email", "User_Cost")
				.withIgnoreEmptyLines()
			
				))
		{
			
			//Recupere le mail de la personne actuellement connectée
			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			
			//Cherche dans la bdd l'user connecté à partir de son mail
			User currentUserConnected = userRepository.findByEmail(currentUser);
			
			//recupere l'id de la personne actuellement connectée
			long currentUserConnetedID = currentUserConnected.getId();
			
			//Recupere la liste des achievedService à partir de l'id user
			List<AchievedService> listAchievedSByConnectedUserId = achievedServiceRepository.findAchievedServiceByUserId(currentUserConnetedID);
			
			
			for(AchievedService achievedServices : listAchievedSByConnectedUserId)
			{
				long idAchievedService = achievedServices.getId();
				String valid = achievedServices.getValid();
				String dateValidAchievedService = achievedServices.getDate().toString();
				String userFirstName = achievedServices.getUser().getFirstname();
				String userLastName = achievedServices.getUser().getLastname();
				String userEmail = achievedServices.getUser().getEmail();
				long userAccumulatedPoints;
				if(achievedServices.getUser().getAccumulated_points() == null)
				{
					userAccumulatedPoints = 0; achievedServices.getUser().getAccumulated_points();
				}
				else
				{
					userAccumulatedPoints = achievedServices.getUser().getAccumulated_points();
				}
				
				csvPrinter.printRecord(idAchievedService, valid, dateValidAchievedService, userFirstName, userLastName, userEmail, userAccumulatedPoints);
			}
			
			
		}
		
		 catch (Exception e) {
			 logger.error("Erreur dans l'ecriture du CSV",e );
		 }
	}
	

	
	
	
}
