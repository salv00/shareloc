package com.cdad.shareloc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.dto.ColocationDto;
import com.cdad.shareloc.dto.UserDto;
import com.cdad.shareloc.exception.CustomUnauthorizedResponseStatus;
import com.cdad.shareloc.exception.ResourceNotFoundException;
import com.cdad.shareloc.repository.ColocationRepository;
import com.cdad.shareloc.repository.UserRepository;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@Service
//Class implementing ColocationService class
public class ColocationServiceImpl implements ColocationService {

	@Autowired
	ColocationRepository colocationRepository;

	@Autowired
	UserRepository userRepository;

	/*
	 * Creation d'une colocation à partir de l'id user
	 */
	@Override
	public Colocation createColocationByUser(Colocation colocationRequest) {

		// Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

		// Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);

		// recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();

		if (colocationRequest.getName() == null) {
			throw new CustomUnauthorizedResponseStatus("You are not authorized to create null colocation");
		}

		Colocation colocation = userRepository.findById(currentUserConnetedID).map(user -> {

			user.addColocation(colocationRequest);
			return colocationRepository.save(colocationRequest);

		}).orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + currentUserConnetedID));

		return colocation;
	}

	/*
	 * Rejoint une colocation à partir du nom de la colocation
	 */
	@Override
	public Colocation joinColocationByNameColocation(Colocation colocationRequest) {

		// Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

		// Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);

		// recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();

		String nameColocation = colocationRequest.getName();

		Colocation findColocation = colocationRepository.findByName(nameColocation);

		Colocation colocation = userRepository.findById(currentUserConnetedID).map(user -> {

			long idColocation = findColocation.getId();

			if (idColocation != 0L) {
				Colocation _colocation = colocationRepository.findById(idColocation).get();

				user.addColocation(_colocation);
				userRepository.save(user);
				return _colocation;
			}

			return colocationRequest;

		}).orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + currentUserConnetedID));

		return colocation;
	}

	/*
	 * Permet à un user de quitter la colocation
	 */
	@Override
	public Colocation exitFromColocationByIdColocAndIdUser(Colocation colocationRequest) {
		// Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

		// Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);

		// recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();

		User user = userRepository.findById(currentUserConnetedID).get();

		String nomColocation = colocationRequest.getName();

		Colocation colocation = colocationRepository.findByName(nomColocation);

		long colocationId = colocation.getId();

		user.removeColocation(colocationId);
		userRepository.save(user);

		return null;
	}

	/*
	 * Cherche tous les users qui sont presents dans une colocation à partir de l'id
	 * de la coloc
	 */
	@Override
	public List<UserDto> findUsersByColocationsId(long colocationId) {

		if (!colocationRepository.existsById(colocationId)) {
			throw new ResourceNotFoundException("Not found colocation with id = " + colocationId);
		}

		List<UserDto> listUserInColocation = new ArrayList<>();
		List<User> listUsersPresentsInColocation = userRepository.findUsersByColocationsId(colocationId);
		
		List<UserDto> listUserDto = listUsersPresentsInColocation.stream().map(users -> {
			
			UserDto userDto = new UserDto();
			userDto.setId(users.getId());
			userDto.setEmail(users.getEmail());
			userDto.setFirstname(users.getFirstname());
			userDto.setLastname(users.getLastname());
			userDto.setCost(users.getAccumulated_points());
			return userDto;
	      }).collect(Collectors.toList());

		listUserInColocation.addAll(listUserDto);
		
		return listUserInColocation;
	}

	/*
	 * Cherche toutes les colocations qui sont rattachés à un user à partir de l'id
	 * de l'user
	 */
//	@Override
//	public List<Colocation> findColocationsByUserId() {
//
//		// Recupere le mail de la personne actuellement connectée
//		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//
//		// Cherche dans la bdd l'user connecté à partir de son mail
//		User currentUserConnected = userRepository.findByEmail(currentUser);
//
//		// recupere l'id de la personne actuellement connectée
//		long currentUserConnetedID = currentUserConnected.getId();
//
//		List<Colocation> listColocation = colocationRepository.findColocationsByUsersId(currentUserConnetedID);
//
//		return listColocation;
//	}
	
	
	
	@Override
	public List<ColocationDto> findColocationsByUserId() {

		// Recupere le mail de la personne actuellement connectée
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

		// Cherche dans la bdd l'user connecté à partir de son mail
		User currentUserConnected = userRepository.findByEmail(currentUser);

		// recupere l'id de la personne actuellement connectée
		long currentUserConnetedID = currentUserConnected.getId();
		
		
		List<ColocationDto> listColocationsDto = new ArrayList();

		List<Colocation> listColocations = colocationRepository.findColocationsByUsersId(currentUserConnetedID);
		
		List<ColocationDto> colocationsDto = listColocations.stream().map(colocation -> {
			ColocationDto colocationDto = new ColocationDto();
			colocationDto.setColocationId(colocation.getId());
			colocationDto.setNameColocation(colocation.getName());
			return colocationDto;
	      }).collect(Collectors.toList());

		
		listColocationsDto.addAll(colocationsDto);
		
		
		return listColocationsDto;
	}
	
	
	
	
	
	
	

	/*
	 * Cherche une colocation à partir de son id
	 */
	@Override
	public Colocation findColocationById(Colocation colocation, long colocationId) {

		Colocation colocationBDD = colocationRepository.findById(colocationId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found colocation with id : " + colocationId));

		return colocationBDD;
	}

	/*
	 * Cherche liste des colocation
	 */
	
	@Override
	public List<ColocationDto> findColocationList() {
		
		List<ColocationDto> listColocationsDto = new ArrayList<>();
		
		List<Colocation> listColocations = colocationRepository.findAll();
		
		List<ColocationDto> colocationsDto = listColocations.stream().map(colocations -> {
			
			ColocationDto colocationDto = new ColocationDto();
			colocationDto.setColocationId(colocations.getId());
			colocationDto.setNameColocation(colocations.getName());
			return colocationDto;
	      }).collect(Collectors.toList());

			listColocationsDto.addAll(colocationsDto);
		
		return listColocationsDto;
	}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*************************************
	 * CODE POUR LE GESTIONAIRE DE LA COLOCATION
	 *************************************/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * Update(Edit) name colocation
	 */
	@Override
	public Colocation updateColocation(Colocation colocation, Long colocationId) {
		Colocation colocationBDD = colocationRepository.findById(colocationId).get();

		if (Objects.nonNull(colocation.getName()) && !"".equalsIgnoreCase(colocation.getName())) {
			colocationBDD.setName(colocation.getName());
		}

		return colocationRepository.save(colocationBDD);
	}

	/*
	 * Delete colocation
	 */
	@Override
	public void deleteColocationById(Long colocationId) {
		colocationRepository.deleteById(colocationId);
	}

}
