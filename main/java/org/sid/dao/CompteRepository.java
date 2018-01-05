package org.sid.dao;

import java.util.List;

import org.sid.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompteRepository extends JpaRepository<Compte, String>{
	
	@Query("select c from Compte c WHERE c.client.code=:x")
	public List<Compte> getComptes(@Param("x")Long codeClient);}
