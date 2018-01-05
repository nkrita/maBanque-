package org.sid.dao;

import java.util.List;

import org.sid.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClientRepository extends JpaRepository<Client, Long>{
	@Query("select c from Client c where c.nom like :x")
	public Page<Client> clientParMC(@Param("x")String mc,Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("update Client c set c.nom=:nom,c.prenom=:prenom,c.email=:email,"
			+ "c.telephone=:telephone,c.adress=:adress WHERE c.code=:code")
	public void modifierClient(@Param("code")Long code, @Param("nom")String nom, 
			@Param("prenom")String prenom, @Param("email")String email, 
			@Param("telephone")String telephone, @Param("adress")String adresse);
}
