package org.sid.metier;

import java.util.List;

import org.sid.dao.CompteRepository;
import org.sid.entities.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
@Service
public class CompteMetierImpl implements CompteMetier{
	@Autowired
	private CompteRepository compteRepositroy;
	@Override
	public Compte addCompte(Compte cp) {
		// TODO Auto-generated method stub
		return compteRepositroy.save(cp);
	}

	@Override
	public List<Compte> listCompte() {
		// TODO Auto-generated method stub
		return compteRepositroy.findAll();
	}

	@Override
	public Compte consulterCompte(String codeCompte) {
		// TODO Auto-generated method stub
		Compte cp=compteRepositroy.findOne(codeCompte);
		if(cp == null){
			throw new RuntimeException("compte innexistant");
		}
		return cp;
	}

	@Override
	public Page<Compte> listCompte(int page, int size) {
		
		Page<Compte> listComptes=compteRepositroy.findAll(new PageRequest(page, size, new Sort(Direction.DESC, "dateCreation")));
		return listComptes;
	}

}
