package com.cdad.shareloc.repository;

import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdad.shareloc.dao.Colocation;
import com.cdad.shareloc.dao.ServiceShareloc;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

public interface ServiceRepository extends JpaRepository<ServiceShareloc, Long>{

	
	/**
	 * Recupere toute la liste des services en BDD
	 */
	public List<ServiceShareloc> findAll();
	
	/**
	 * Cherche les service à partir de l'userId
	 */
	public List<ServiceShareloc> findServiceByUserId(Long userId);
	
	
	
	/**
	 * Recupere le nombre de point d'un service
	 * @param Score
	 * @return
	 */
	public List<ServiceShareloc> findByCost(String cost);
	
	public List<ServiceShareloc> findServiceByColocationId(Long colocationId);


	@Query("SELECT s FROM ServiceShareloc s WHERE s.colocation.id = :colocationId")
	List<ServiceShareloc> findServicesByColocationId(@Param("colocationId") Long colocationId);




}
