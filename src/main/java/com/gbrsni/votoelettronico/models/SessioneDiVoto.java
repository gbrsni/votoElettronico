package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SessioneDiVoto {
	private final int id;
	private String nome;
	
	public SessioneDiVoto(int id, String nome) {
		Objects.requireNonNull(nome);
		
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	// TODO: toString()
}
