package com.example.curso.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@SequenceGenerator(name = "seq_usuario" , sequenceName = "seq_usuario" , allocationSize = 1)
public class Usuario implements Serializable , UserDetails{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_usuario" , strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String login;
	
	private String senha;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public void setLogin(String login) {
		this.login = login;
	}



	public void setSenha(String senha) {
		this.senha = senha;
	}



	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
