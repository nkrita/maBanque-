package org.sid.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;


@Entity
public class Client implements Serializable{
	
	@Id @GeneratedValue
	private Long code;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String adress;
	private String photo;

	
	public Client(String nom, String prenom, String email, String telephone, String adress, String photo) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adress = adress;
		this.photo = photo;
	}

	
	
	
	public String getPhoto() {
		return photo;
	}




	public void setPhoto(String photo) {
		this.photo = photo;
	}




	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@OneToMany(mappedBy="client",fetch=FetchType.LAZY)
	private Collection<Compte> comptes;
		
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Client(Long code, String nom, Collection<Compte> comptes) {
		super();
		this.code = code;
		this.nom = nom;
		this.comptes = comptes;
	}
	public Client(Long code) {
		super();
		this.code = code;
	}

	public Client(String nom) {
		super();
		this.nom = nom;
	}
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Collection<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(Collection<Compte> comptes) {
		this.comptes = comptes;
	}

	
	
	
}
