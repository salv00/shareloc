package com.cdad.shareloc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cdad.shareloc.dao.ServiceShareloc;
import com.cdad.shareloc.dao.Vote;

/*
 * Author : Salvatore COVALEA
 * Project name : ShareLoc
 * Context : Licence Pro CDAD 2022/2023
 * Date : 04/01/2023
 * Version : Beta 1.5
 */

public interface VoteRepository extends JpaRepository<Vote, Long>{


	
	@Query("SELECT COUNT(*) FROM Vote v WHERE v.vote=:decision AND v.service=:service")
	long countNbVoteByDecision(@Param("decision") String decision, @Param("service") ServiceShareloc service);
	
	
	public long countAllByVoteLike(String vote);
	
	public long countByServiceIsNotNull();
	
	public long countByServiceId(long idService);

	public List<Vote> findByServiceId(long serviceId);
	
	
}
