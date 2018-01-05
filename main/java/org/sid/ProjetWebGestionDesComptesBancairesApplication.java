package org.sid;

import java.util.Date;
import java.util.List;

import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.CompteEpargne;
import org.sid.entities.Operation;
import org.sid.metier.ClientMetier;
import org.sid.metier.CompteMetier;
import org.sid.metier.OperationMetier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;

@SpringBootApplication
public class ProjetWebGestionDesComptesBancairesApplication {

	public static void main(String[] args) {
		ApplicationContext ctx= 
			SpringApplication.run(ProjetWebGestionDesComptesBancairesApplication.class, args);
		/*
		ClientMetier clientMetier =ctx.getBean(ClientMetier.class);
		clientMetier.addClient(new Client("A"));
		clientMetier.addClient(new Client("B"));
		clientMetier.addClient(new Client("CA"));
		System.out.println("----------------------------------");
		List<Client> clients=clientMetier.listClient();
		clients.forEach(c->System.out.println(c.getNom()));
		/*for(Client c:clients){
			System.out.println(c.getNom());
		}*/
		/*System.out.println("----------------------------------");
		
		List<Client> clientMC=clientMetier.clientParMC("%A%");
		clientMC.forEach(c->System.out.println(c.getNom()));
		
		System.out.println("----------------compte------------------");
		CompteMetier compteMetier=ctx.getBean(CompteMetier.class);
		compteMetier.addCompte(new CompteCourant("CC1", 
				new Date(), 9000, new Client(1L), 8000));
		compteMetier.addCompte(new CompteCourant("CC2", 
				new Date(), 6000, new Client(2L), 4000));
		compteMetier.addCompte(new CompteEpargne("CE1", 
				new Date(), 7000, new Client(1L), 5.5));
		
		Compte cp=compteMetier.consulterCompte("CC1");
		System.out.println("solde = "+cp.getSolde());
		System.out.println("solde = "+cp.getDateCreation());
		System.out.println("----------------operations------------------");
		
		OperationMetier operationMetier=ctx.getBean(OperationMetier.class);
		operationMetier.verser("CC1", 90000);
		operationMetier.retirer("CC1", 6000);
		operationMetier.verser("CC1", 20000);
		operationMetier.retirer("CC1", 6000);
		operationMetier.verser("CC1", 900000);
		operationMetier.retirer("CC1", 6000);
		operationMetier.virment("CC1", "CE1", 100);
		Page<Operation> ops=operationMetier.listOperations("CC1", 0, 3);
		for(Operation o:ops.getContent()){
			System.out.println(o.getNumeroOperation());
			System.out.println(o.getMontant());
			System.out.println(o.getDateOperation());
		}
		*/
	}
}
