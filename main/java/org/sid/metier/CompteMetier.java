package org.sid.metier;

import java.util.List;

import org.sid.entities.Compte;
import org.springframework.data.domain.Page;

public interface CompteMetier {
	public Compte addCompte(Compte cp);
	public List<Compte> listCompte();
	public Compte consulterCompte(String codeCompte);
	public Page<Compte> listCompte(int page,int size);
}
