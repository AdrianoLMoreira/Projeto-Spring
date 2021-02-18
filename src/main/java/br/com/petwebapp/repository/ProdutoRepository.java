package br.com.petwebapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.petwebapp.domain.Categoria;
import br.com.petwebapp.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	
//	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
//	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
	
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContaining(String nome, Pageable pageRequest);
	
	@Query("Select p from Produto p where p.nome like %:nome%")
	Page<Produto> findProduto(@Param("nome") String nome, Pageable pageRequest);
	
	
	@Query("Select p from Produto p where p.nome like %:nome%")
	List<Produto> findProduto(@Param("nome") String nome);
	
	@Transactional(readOnly=true)
	List<Produto> findByNomeContainingIgnoreCase(String nome);
}
