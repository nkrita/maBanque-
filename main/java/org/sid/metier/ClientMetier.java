package org.sid.metier;

import java.util.List;

import org.sid.entities.Client;
import org.sid.entities.Operation;
import org.springframework.data.domain.Page;

public interface ClientMetier {
	public Client addClient(Client c);
	public Page<Client> clientParMC(String mc,int page,int size);
	public Client ConsulteClient(Long code);
	public List<Client> listClient();


}
