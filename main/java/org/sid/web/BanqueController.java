package org.sid.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.UserRepository;
import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.entities.Users;
import org.sid.entities.CompteCourant;
import org.sid.entities.CompteEpargne;
import org.sid.entities.Operation;
import org.sid.metier.ClientMetier;
import org.sid.metier.CompteMetier;
import org.sid.metier.OperationMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BanqueController {
	@Autowired
	private CompteMetier compteMetier;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OperationMetier operationMetier;
	@Autowired
	private ClientMetier clientMetier;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Value("${dir.images}")
	private String imageDir;
	
	
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("codeCompte","");
		return "operations";
	}
	
	
	@RequestMapping("/users")
	public String Users(Model model,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="8")int size){
		
			Page<Compte> listComptes=compteMetier.listCompte(page, size);
			model.addAttribute("listComptes", listComptes);
			int[] pages=new int[listComptes.getTotalPages()];
			model.addAttribute("pageCourante", page);
			model.addAttribute("size", size);
			model.addAttribute("pages",pages);
			
			List<Client> listClient=clientMetier.listClient();
			model.addAttribute("listClient",listClient);
		return "users";
	}
	
	
	@RequestMapping("/comptes")
	public String Comptes(Model model,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="8")int size){
		
			Page<Compte> listComptes=compteMetier.listCompte(page, size);
			model.addAttribute("listComptes", listComptes);
			int[] pages=new int[listComptes.getTotalPages()];
			model.addAttribute("pageCourante", page);
			model.addAttribute("size", size);
			model.addAttribute("pages",pages);
			
			List<Client> listClient=clientMetier.listClient();
			model.addAttribute("listClient",listClient);
		return "comptes";
	}
	

	
	@RequestMapping("/ajouterCompte")
	public String ajouterCompte(Model model,
			String type_compte, String code_compte, Double solde, Double taux, Double decouvert, Long code_client){
		
		Compte cp=null;
		System.out.println("typeCompte : " + type_compte);
		System.out.println("codeCompte : " + code_compte);
		System.out.println("solde : " + solde);
		System.out.println("taux : " + taux);
		System.out.println("decouvert : " + decouvert);
		System.out.println("code_cli : " + code_client);
		
		if(decouvert != null )
			cp = new CompteCourant(code_compte, new Date(), solde, new Client(code_client), decouvert);
		if(taux != null)
			cp = new CompteEpargne(code_compte, new Date(), solde, new Client(code_client), taux);
		try{
			compteMetier.addCompte(cp);
		}
		catch(Exception e){
			
		}
		return "redirect:/comptes";
	}
	
	
	@RequestMapping(value="/ajouterUser",method=RequestMethod.POST)
	public String ajouterUser(Model model,Users user,String role){
		if(role.equals("User")) {
		user.setEnabled(true);
		user.setRole("ROLE_USER");
		userRepository.save(user);}
		if(role.equals("Admin")) {
		user.setEnabled(true);
		user.setRole("ROLE_ADMIN");
		userRepository.save(user);
		
		}
		return "redirect:/users";
	}


	
	
	
	
	@RequestMapping(value="/getClients",method=RequestMethod.GET)
	public String getClients(Model model,
			@RequestParam(name="motCle",defaultValue="")String mc,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="4")int size,
			Long code,Long codeUP){
		
		Page<Client> pageClient=clientMetier.clientParMC("%"+mc+"%", page, size);
		model.addAttribute("pageClient",pageClient);
		int[] pages=new int[pageClient.getTotalPages()];
		model.addAttribute("pages",pages);
		model.addAttribute("size",size);
		model.addAttribute("motCle",mc);
		model.addAttribute("pageCourante",page);
		if (code != null) {
			
			Client client=clientMetier.ConsulteClient(code);
			model.addAttribute("client",client);
			
			List<Compte> listCompte=compteRepository.getComptes(code);
			model.addAttribute("listCompte", listCompte);
			
		}
		if (codeUP != null) {
			Client client=clientMetier.ConsulteClient(codeUP);
			model.addAttribute("clientUP",client);
			model.addAttribute("client",client);
		}
		
		return "clients";
	}

	
	@RequestMapping(value="/getPhoto",produces={MediaType.IMAGE_JPEG_VALUE})
	@ResponseBody
	public byte[] getPhoto(Long id) throws FileNotFoundException, IOException {
		
		File f = new File(imageDir+id);
		return IOUtils.toByteArray(new FileInputStream(f));
	}
	
	
	@RequestMapping(value="/saveClient",method=RequestMethod.POST)
	public String saveClient(Model model,Client client,
			@RequestParam(name="pic") MultipartFile file) throws Exception{
		
		if(!(file.isEmpty())) {
			client.setPhoto(file.getOriginalFilename());
		}
	
			model.addAttribute("client", client);
			clientMetier.addClient(client);
			String nomClient=client.getNom();
			Long code=client.getCode();
			if(!(file.isEmpty())) {
				client.setPhoto(file.getOriginalFilename());
				file.transferTo(new File(imageDir+client.getCode()));
			}
		
			return "redirect:/getClients?motCle="+nomClient+"&page=0&size=4&code="+code;
	}
	
	

	
	@RequestMapping(value="/editClient",method=RequestMethod.POST)
	public String editClient(Model model,Client clientUP,@RequestParam(name="pic") MultipartFile file) throws IllegalStateException, IOException{		
		Long code=clientUP.getCode();
		String nomClient=clientUP.getNom();
		String prenomClient=clientUP.getPrenom();
		String emailClient=clientUP.getEmail();
		String teleClient=clientUP.getTelephone();
		String adressClient=clientUP.getAdress();
		clientRepository.modifierClient(code, nomClient,prenomClient, emailClient, teleClient, adressClient);
		if(!(file.isEmpty())) {
			clientUP.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+clientUP.getCode()));
		}
		return "redirect:/getClients?motCle="+""+"&page=0&size=4&code="+code;
	}
	
	@RequestMapping(value="/deleteClient",method=RequestMethod.GET)
	public String deleteClient(Model model,Long code){		
		Client c=clientRepository.findOne(code);
		clientRepository.delete(c);
		return "redirect:/getClients?motCle="+""+"&page=0&size=4&code="+code;
	}
	
	@RequestMapping("/operations")
	public String Operation(Model model){
		model.addAttribute("codeCompte","");
		return "operations";
	}
	
	@RequestMapping(value="/getCompte",method=RequestMethod.GET)
	public String getCompte(Model model,String codeCompte,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="size",defaultValue="5")int size){
		try {
			Compte cpCompte=compteMetier.consulterCompte(codeCompte);
			Page<Operation> operation=
					operationMetier.listOperations(codeCompte,page, size);
			model.addAttribute("pageOperation",operation);
			model.addAttribute("compte",cpCompte);
			model.addAttribute("codeCompte",cpCompte.getCodeCompte());
			int[] pages=new int[operation.getTotalPages()];
			model.addAttribute("pages",pages);
			model.addAttribute("size",size);
			model.addAttribute("pageCourante",page);
			
		} catch (Exception e) {
			model.addAttribute("exception",e.getMessage());
		}
		
		return "operations";
	}

	@RequestMapping(value="/saveOperation",method=RequestMethod.POST)
	public String saveOperation(Model model, String typeOperation, String codeCompte, double montant, String codeCompte2){
		try {
			if(typeOperation.equals("versement")){
				operationMetier.verser(codeCompte, montant);
			}
			else if(typeOperation.equals("retrait")){
				operationMetier.retirer(codeCompte, montant);
			}
			else if(typeOperation.equals("virement")){
				operationMetier.virment(codeCompte, codeCompte2, montant);
			}
		} catch (Exception e) {
			model.addAttribute("errorTransaction", e.getMessage());
			return "redirect:/getCompte?codeCompte="+codeCompte+"&errorTransaction="+e.getMessage();
		}

		return "redirect:/getCompte?codeCompte="+codeCompte;
	}
	
	

	
}
