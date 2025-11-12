package com.cdad.shareloc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdad.shareloc.dao.AchievedService;
import com.cdad.shareloc.dao.ServiceShareloc;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

public interface AchievedServiceRepository extends JpaRepository<AchievedService, Long>{	
		
	@Query("SELECT a FROM AchievedService a WHERE  a.serviceShareloc=:service")
	public long getServiceIdByAchievedService(@Param("service") ServiceShareloc service);

	
	public List<AchievedService> findAchievedServiceByColocationId(Long colocationId);
	
	public List<AchievedService> findAchievedServiceByUserId(Long userId);
	
	
}
