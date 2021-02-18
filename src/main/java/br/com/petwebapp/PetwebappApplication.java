package br.com.petwebapp;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.petwebapp.repository.ProdutoRepository;

@SpringBootApplication
public class PetwebappApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PetwebappApplication.class, args);
	}

	@Autowired
	public EntityManager entity;
	
	@Autowired
	public ProdutoRepository prodRepo;

	@Override
	public void run(String... args) throws Exception {

		System.out.println("INICIOU!!!");
		
//		javax.persistence.Query query = entity.createQuery("Select p from Produto p where p.nome like '%:nome%'").setParameter("nome", "Mouse");
//		lista = query.getResultList();
	

	}
}
