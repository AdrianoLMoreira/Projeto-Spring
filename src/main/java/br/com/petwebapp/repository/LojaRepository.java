package br.com.petwebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.petwebapp.domain.Loja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Integer>{

}
