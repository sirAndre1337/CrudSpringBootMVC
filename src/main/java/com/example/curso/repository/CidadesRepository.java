package com.example.curso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.curso.model.Cidades;

public interface CidadesRepository extends JpaRepository<Cidades, Long>{

	@Query("select c from Cidades c where c.estados.id = ?1" )
	public List<Cidades> buscaCidadePorEstado(Long estadoId);
	
}
