package com.cdad.shareloc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdad.shareloc.dao.User;

/*
 * Author : salvatore COVALEA
 * Project name : ShareLoc 
 * Version : Pre-release
 * Date : 18/01/2022
 */

public interface UserRepository extends JpaRepository<User, Long>{

    Boolean existsByEmail(String email);

	User findByEmail(String email);
	
	List<User> findUsersByColocationsId(Long colocationId);
	
	List<User> findByColocationsId(long colocationId);
	
	List<User> findAllByColocationsId(long colocationId);

	long countUsersByColocationsId(long colocationId);
	
	public List<User> findAll();
	

	
}
