package com.cdad.shareloc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cdad.shareloc.dao.AchievedService;
import com.cdad.shareloc.dto.AchievedServiceDto;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

public interface AchievedServiceService {
	
	
	//Creation d'un service terminé
	AchievedService createAchiviedService(MultipartFile file ,Long colocationId, Long serviceId);
	
	//Validation d'un achieved service
	AchievedService validateAchievedService(AchievedService achievedService, Long colocationId, Long achievedServiceId);
	
	//Cherche la liste des achieved Services de mes colocation
	List<AchievedServiceDto> findAchievedServicesOfMyColocation();

	//Cherche à partir de son id
	AchievedService findById(Long id);
}
