package com.example.curso.repository;

import java.util.List;

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
}
