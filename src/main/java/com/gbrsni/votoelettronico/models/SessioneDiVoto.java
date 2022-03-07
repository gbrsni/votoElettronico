package com.gbrsni.votoelettronico.models;

public class SessioneDiVoto {
	private final int id;
	private String nome;
	
	public SessioneDiVoto(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
