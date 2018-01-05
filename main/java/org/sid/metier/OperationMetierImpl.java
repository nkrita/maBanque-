package org.sid.metier;

import java.util.Date;

import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Compte;
import org.sid.entities.Operation;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OperationMetierImpl implements OperationMetier{
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private CompteMetier compteMetier;
	@Transactional
	@Override
	public void verser(String codeCompte, double montant) {
		if (montant < 0) {
			throw new RuntimeException("le montant saisi est negative");
		}
		Compte cp=compteMetier.consulterCompte(codeCompte);
		Operation operation=new Versement(new Date(), montant, cp);
		operationRepository.save(operation);
		cp.setSolde(cp.getSolde()+montant);
	}
	@Transactional
	@Override
	public void retirer(String codeCompte, double montant) {
		Compte cp=compteMetier.consulterCompte(codeCompte);
		if(cp.getSolde() < montant){
			throw new RuntimeException("solde isuffisant");
		}
		Operation operation=new Retrait(new Date(), montant, cp);
		operationRepository.save(operation);
		cp.setSolde(cp.getSolde()-montant);
	}
	@Transactional
	@Override
	public void virment(String codeCompte1, String codeCompte2, double montant) {
		retirer(codeCompte1,montant);
		verser(codeCompte2,montant);
	}

	@Override
	public Page<Operation> listOperations(String codeCompte, int page, int size) {
		
		return operationRepository.listOperation(codeCompte, 
				new PageRequest(page, size,new Sort(Direction.DESC, "dateOperation")));
	}

}
