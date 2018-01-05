package org.sid.metier;

import java.util.List;

import org.sid.dao.ClientRepository;
import org.sid.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
@Service
public class ClientMetieImpl implements ClientMetier{
	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public Client addClient(Client c) {
		// TODO Auto-generated method stub
		return clientRepository.save(c);
	}

	@Override
	public Page<Client> clientParMC(String mc,int page,int size) {
		// TODO Auto-generated method stub
		return clientRepository.clientParMC(mc,new PageRequest(page, size,new Sort(Direction.DESC, "code")));
	}

	@Override
	public Client ConsulteClient(Long code) {
		// TODO Auto-generated method stub
		return clientRepository.findOne(code);
	}

	@Override
	public List<Client> listClient() {
		// TODO Auto-generated method stub
		return clientRepository.findAll();
	}
	
}
