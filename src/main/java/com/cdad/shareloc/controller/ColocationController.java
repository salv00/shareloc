package com.cdad.shareloc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.User;
import com.cdad.shareloc.dto.ColocationDto;
import com.cdad.shareloc.dto.UserDto;
import com.cdad.shareloc.repository.ColocationRepository;
import com.cdad.shareloc.service.ColocationService;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

@CrossOrigin("*")
@RestController
public class ColocationController {
	
	final Logger logger = LoggerFactory.getLogger(ColocationController.class);	
	
	@Autowired
	private ColocationRepository colocationRepository;

	@Autowired
	ColocationService colocationService;
	
	
	/*
	 * Creation d'une colocation à partir de l'id utilisateur et du nom de la colocation
	 * WORK
	 */
	@PostMapping("me/create-colocation")
	public Colocation createColocationByUser(@RequestBody Colocation colocation)
	{
		return colocationService.createColocationByUser(colocation);	
	}
	
	
	/*
	 * Permet de rejoindre une colocation à partir de son nom
	 * WORK
	 */
	@PostMapping("me/join-colocation")
	public Colocation joinColocationByNameColoc(@RequestBody Colocation colocation)
	{
		return colocationService.joinColocationByNameColocation(colocation);
	}
	
		
	/*
	 * Permet à un utilisateur de quitter une colocation à partir de l'id de la colocation
	 * WORK
	 */
	@PostMapping("me/colocation/exit")
	public Colocation exitFromColocation(@RequestBody Colocation colocationRequest)
	{
	
		return colocationService.exitFromColocationByIdColocAndIdUser(colocationRequest);
	}
	
	
	/*
	 * Recupere tous les utilisateurs qui font parti d'une colocation
	 * WORK
	 */
	@GetMapping("/colocation/{colocationId}/list-users")
	public List<UserDto> findAllUsersInColocation(@PathVariable("colocationId") long colocationId)
	{
		return colocationService.findUsersByColocationsId(colocationId);
	}
	
	
	/*
	 * Recupere la liste des colocations qui appartiennet à un utilisateur
	 * WORK
	 */
	@GetMapping("me/my-colocations")
	public List<ColocationDto> findAllColocationsByUser()
	{
		return colocationService.findColocationsByUserId();
	}
	
	
	/*
	 * Recupere la liste de tous les colocations
	 * 	WORK
	 */
	@GetMapping("list-colocations")
	public List<ColocationDto> findColocationList()
	{
		return colocationService.findColocationList();
	}
	
	/*
	 * Recupere la liste de tous les colocations
	 * 	WORK
	 */
	@GetMapping("list-colocations-by-service/{serviceId}")
	public List<Colocation> findColocationListByService(@PathVariable("serviceId") long serviceId)
	{
		return colocationRepository.findColocationsByServiceId(serviceId);
	}
	
	
	
	/*
	 * Recupere une colocation à partir de son Id
	 * WORK
	 */
	@GetMapping("/find-colocation/{id}")
	public Colocation findColocationById(@RequestBody Colocation colocation, @PathVariable("id") long colocationId)
	{
		return colocationService.findColocationById(colocation, colocationId);
	}
	

	/*
	 * Changer le nom d'une colocation
	 */
	@PutMapping("/colocation/{id}")
	public Colocation editNameColocation(@RequestBody Colocation colocationRequest, @PathVariable("id") Long colocationId)
	{
		return colocationService.updateColocation(colocationRequest, colocationId);
	}
	
	/*
	 * Permet de supprimer une colocation
	 */
	@DeleteMapping("/colocation/{id}")
	public String deleteColocation(@PathVariable(value = "id") long colocationId)
	{
		colocationRepository.deleteById(colocationId);
		
		return "Colocation has been deleted";
	}
	
}
