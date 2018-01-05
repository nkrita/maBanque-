package org.sid.metier;

import org.sid.entities.Operation;
import org.springframework.data.domain.Page;

public interface OperationMetier {
	public void verser(String codeCompte,double montant);
	public void retirer(String codeCompte,double montant);
	public void virment(String codeCompte1,String codeCompte2,double montant);
	public Page<Operation> listOperations(String codeCompte,int page,int size);
}
