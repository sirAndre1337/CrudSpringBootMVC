package com.example.curso.model;

public enum Cargo {

	JUNIOR ("Júnior (Comeco de carreira)"),
	PLENO ("Pleno (Alguns anos de XP)"),
	SENIOR("Senio (Pika das Galaxias)");
	
	private String nome;
	
	private Cargo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
