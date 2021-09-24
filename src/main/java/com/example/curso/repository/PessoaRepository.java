package com.example.curso.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.curso.model.Pessoa;
@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	@Query("select p from Pessoa p where upper(p.nome) like %?1%")
	List<Pessoa> pesquisaPorNome(String nome);
	
	@Query("select p from Pessoa p where p.sexo = ?1")
	List<Pessoa> pesquisaPorSexo(String sexo);
	
	@Query("select p from Pessoa p where upper(p.nome) like %?1% and p.sexo = ?2")
	List<Pessoa> pesquisaPorSexoENome(String nome , String sexo);
	
	default Page<Pessoa> pesquisaPessoaPorNomePaginada(String nome , Pageable pageable) {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		
		/*Configurando a pesquisa para consulta por partes do nome no banco de dados, igual like do SQL*/
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		/*Une o objeto com o valor e a configuracao para pesquisar*/
		Example<Pessoa> example = Example.of(pessoa , exampleMatcher);
		
		Page<Pessoa> pessoas = findAll(example, pageable);
		
		return pessoas;
	}
}
