package com.cdad.shareloc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdad.shareloc.dao.Colocation;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

public interface ColocationRepository extends JpaRepository<Colocation, Long>{
	
	/**
	 * Recupere toute la liste de la BDD
	 */
	public List<Colocation> findAll();
	
	/**
	 * cherche une coloc à partir de son nom
	 * @param name
	 * @return
	 */
	//Optional<Colocation> findByName(String name);
	
	Optional<Colocation> findByUsersId(Long userId);
		
	
	Long deleteByName(String name);
	
    Boolean existsByUsersId(Long userId);
    
    Colocation findByName(String name);
	
	
	List<Colocation> findColocationsByUsersId(Long userId);
	
	@Query("SELECT c FROM Colocation c JOIN c.services s WHERE s.id = :serviceId")
	List<Colocation> findColocationsByServiceId(@Param("serviceId") Long serviceId);

	

}
