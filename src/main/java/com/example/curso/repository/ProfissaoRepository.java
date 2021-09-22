package com.example.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.curso.model.Profissao;

@Repository
@Transactional
public interface ProfissaoRepository extends JpaRepository<Profissao, Long>{

}
