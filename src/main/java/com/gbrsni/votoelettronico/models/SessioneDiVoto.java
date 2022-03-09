package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SessioneDiVoto {
	private final int id;
	private String nome;
	private Modalit‡DiVoto modalit‡DiVoto;
	private Modalit‡DiVittoria modalit‡DiVittoria;
	
	public SessioneDiVoto(int id, String nome, Modalit‡DiVoto modalit‡DiVoto, Modalit‡DiVittoria modalit‡DiVittoria) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modalit‡DiVoto);
		Objects.requireNonNull(modalit‡DiVittoria);
		
		this.id = id;
		this.nome = nome;
		this.modalit‡DiVoto = modalit‡DiVoto;
		this.modalit‡DiVittoria = modalit‡DiVittoria;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	// TODO: toString()
}
