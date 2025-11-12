package com.cdad.shareloc.service;

import java.util.List;

import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.ServiceShareloc;
import com.cdad.shareloc.dto.ServiceShareLocDTO;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */


public interface ServiceShareLocService {

	//Creation d'un service "Proposition d'un service"
	ServiceShareloc createService (ServiceShareloc service, Long colocationId);
	
	//Modification des informations d'un service
	ServiceShareloc editService (ServiceShareloc serviceRequest, Long colocationId, Long serviceId);
	
	//Supression d'un service
	void deleteService (Long colocationId, Long serviceId);
	
	//Recuperation de la liste des services qui sont rattaches à un user
	List<ServiceShareLocDTO> findServiceByUserId();

	
	//Recupere la liste des services qui sont rattachés à une colocation
	List<ServiceShareLocDTO> findServiceByColocationId();
	
	
	
	
	
}
