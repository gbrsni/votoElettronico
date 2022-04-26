package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class Candidato extends OpzioneDiVoto {
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Partito getPartito() {
		return partito;
	}


	public void setPartito(Partito partito) {
		this.partito = partito;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return this.nome + " " + this.cognome;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (o == this) {
			return true;
		}
		
		if (getClass() != o.getClass()) {
			return false;
		}
		
		Candidato c = (Candidato) o;
		return this.id == c.id;
	}
}
