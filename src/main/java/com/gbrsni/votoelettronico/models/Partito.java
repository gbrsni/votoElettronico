package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class Partito {
	public final int id;
	private String nome;
	
	public Partito(int id, String nome) {
		Objects.requireNonNull(nome);
		
		this.id = id;
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
}
