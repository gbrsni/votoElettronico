package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class Candidato {
	public final int id;
	private String nome;
	private String cognome;
	private Partito partito;
	
	public Candidato(int id, String nome, String cognome, Partito partito) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);
		
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.partito = partito;
	}

	public Partito getPartito() {
		return partito;
	}

	public void setPartito(Partito partito) {
		this.partito = partito;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}
}
