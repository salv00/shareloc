package com.cdad.shareloc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.ServiceShareloc;
import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.dao.Vote;
import com.cdad.shareloc.exception.CustomUnauthorizedResponseStatus;
import com.cdad.shareloc.exception.ResourceNotFoundException;
import com.cdad.shareloc.repository.ColocationRepository;
import com.cdad.shareloc.repository.ServiceRepository;
import com.cdad.shareloc.repository.UserRepository;
import com.cdad.shareloc.repository.VoteRepository;
import com.cdad.shareloc.status.ChoixVote;
import com.cdad.shareloc.status.StatusService;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Service
public class VoteServiceImpl implements VoteService{
	
	
	@Autowired
	private ColocationService colocationService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ColocationRepository colocationRepository;
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	private StatusService statusService;
	
	private ChoixVote choixVote;

	
	/*
	 *Permet de voter pour accepter un service
	 */
	@Override
	public Vote votingForAddService(Vote voteRequest, Long colocationId, Long serviceId) {
		
		
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User currentUserConnected = userRepository.findByEmail(currentUser);
		
		long currentUserConnetedID = currentUserConnected.getId();
		
		
			//Verifie que colocationId existe bien dans la bdd
			Colocation colocationVerif = colocationRepository.findById(colocationId)
					.orElseThrow(() -> new ResourceNotFoundException("Not found Colocation with id : " + colocationId));
			
			//Verifie que userId existe bien dans la BDD
			User userVerif = userRepository.findById(currentUserConnetedID)
					.orElseThrow(() -> new ResourceNotFoundException("Not found user with id : " + currentUserConnetedID));

			//Verifie que serviceId existe bien dans la BDD
			ServiceShareloc serviceVerif = serviceRepository.findById(serviceId)
					.orElseThrow(() -> new ResourceNotFoundException("Not found service with id : " + serviceId));
			
			
			//Recupere la liste des users presents dans la colocation à partir de l'id qui est dans la route de l'api
			List<User> listUsersPresentsInColocation = userRepository.findUsersByColocationsId(colocationId);
			
		
			//Verifie si l'id de l'user qui à été renseigné dans la route est bien present dans la coloc qui est renseigne dans la route
			boolean userIsPresentInColocation = listUsersPresentsInColocation.contains(userVerif);
			

			//Si l'user qui tente de voter fait bien parti de la colocation (colocationId) alors il peut voter
			if (userIsPresentInColocation == true) {
				
				Vote vote_userId = userRepository.findById(currentUserConnetedID).map(user ->{
					
					voteRequest.setUser(user);
					
					return voteRepository.save(voteRequest);
					
				}).orElseThrow(() -> new ResourceNotFoundException("Not found user with userId = " + currentUserConnetedID));	
			}
			
			else {
				
				throw new ResourceNotFoundException("User id : " + currentUserConnetedID + " not found in colocation id : " + colocationId);
			}
			
			
			
				//Recupere la liste des servies qui sont associés à une colocation
				List<ServiceShareloc> listServiceAssociedColocation = serviceRepository.findServiceByColocationId(colocationId);		

				//Verifie que le service (serviceId) est bien associé à la colocation (colocationId)
				boolean serviceIsAssociedAtColocation = listServiceAssociedColocation.contains(serviceVerif);
				
				//Si serviceId est bien associé à la colocation (colocationId) alors l'user peut continuer à voter
				if (serviceIsAssociedAtColocation == true) {
					
					Vote vote_serviceId = serviceRepository.findById(serviceId).map(service -> {
						
						voteRequest.setService(service);
						
						return voteRepository.save(voteRequest);
					}).orElseThrow(() -> new ResourceNotFoundException("Not Service with serviceId = " + serviceId));
				}

				else {
					throw new ResourceNotFoundException("Service id : " + serviceId + " not found in colocation id : " + colocationId);

				}
				
				
				if(voteRequest.getVote().contentEquals("pour") || voteRequest.getVote().contentEquals("Pour") || voteRequest.getVote().contentEquals("POUR"))
				{
					voteRequest.setVote(choixVote.POUR.name());
					voteRepository.save(voteRequest);
				}
				
				if(voteRequest.getVote().contentEquals("contre")|| voteRequest.getVote().contentEquals("Contre") || voteRequest.getVote().contentEquals("CONTRE"))
				{
					voteRequest.setVote(choixVote.CONTRE.name());
					voteRepository.save(voteRequest);
				}
						
				
				/*
				 * Chaque fois qu'il y a un vote le code verifie si le nombre de vote "POUR" 
				 * pour le service concerné est superieur à la moyenne du nombre de colocataires
				 * Dans le cas ou le nombre d'users qui ont voté "POUR" pour le service concerné
				 * alors dans ce cas le status du service dans table "Service" vas changer en approuvé
				 * Sinon rejeté
				 */
				
				//Recupere le nombre d'users presents dans la colocation
				long nbUsersPresentsInColocation = userRepository.countUsersByColocationsId(colocationId);

				//Recupere le nombre de vote "POUR" pour le service concerné
				long nbVotePour = voteRepository.countNbVoteByDecision(choixVote.POUR.name(), serviceVerif);
				
				//Recupere le nombre de vote "CONTRE" pour le service cocerné
				long nbVoteContre = voteRepository.countNbVoteByDecision(choixVote.CONTRE.name(), serviceVerif);
				
				//On recupere le nombre de colocataires on le divise par 2
				long calculMoyenneNbUsersPresentsInColocation = nbUsersPresentsInColocation / 2;
				
				//Si le nombre de vote "Pour" est superieur a la moitié des colocataires
				if(nbVotePour > calculMoyenneNbUsersPresentsInColocation)
				{
					serviceVerif.setStatusService(statusService.VALIDATED.name());
					serviceRepository.save(serviceVerif);
					
					userVerif.addService(serviceVerif);
					userRepository.save(userVerif);
				}
				
				//Sinon si nbVoteContre est superieur à la motiée de du nombre de colocataires
				//Alors l'ajout du service est rejeté
				else if (nbVoteContre > calculMoyenneNbUsersPresentsInColocation) 
				{
					serviceVerif.setStatusService(statusService.REJECTED.name());
					serviceRepository.save(serviceVerif);
				}
				
				//Sinon si le nbDeVotePour est egale au nbVoteContre alors
				//Faudra reproposer le service en changent quelque chose pour que les colocataires soient daccord
				else if(nbVotePour == nbVoteContre)
				{
					serviceVerif.setStatusService(statusService.REPROPOSE.name());
					serviceRepository.save(serviceVerif);
				}
				
				//Sinon le vote sera encore en votation en cours jusqu'au moment que les colocataires ayent tous voté
				else {
					serviceVerif.setStatusService(statusService.VOTE_IN_PROGRESS_FOR_ADDING.name());
					serviceRepository.save(serviceVerif);
				}	
		
		
		
		return voteRequest;
	}
	
	
	
	
	
	
	
	
	
	

}
