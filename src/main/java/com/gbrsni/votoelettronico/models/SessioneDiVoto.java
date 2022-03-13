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

	public SessioneDiVoto(int id, String nome, String modalit‡DiVoto, String modalit‡DiVittoria) throws IllegalArgumentException {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modalit‡DiVoto);
		Objects.requireNonNull(modalit‡DiVittoria);
		
		this.id = id;
		this.nome = nome;
		
		if (modalit‡DiVoto.equals("ORDINALE")) {
			this.modalit‡DiVoto = Modalit‡DiVoto.ORDINALE;
		} else if (modalit‡DiVoto.equals("CATEGORICO")) {
			this.modalit‡DiVoto = Modalit‡DiVoto.CATEGORICO;
		} else if (modalit‡DiVoto.equals("CATEGORICO_CON_PREFERENZE")) {
			this.modalit‡DiVoto = Modalit‡DiVoto.CATEGORICO_CON_PREFERENZE;
		} else if (modalit‡DiVoto.equals("REFERENDUM")) {
			this.modalit‡DiVoto = Modalit‡DiVoto.REFERENDUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalit‡ di voto " + modalit‡DiVoto + " non valida");
		}
		
		if (modalit‡DiVoto.equals("MAGGIORANZA")) {
			this.modalit‡DiVittoria = Modalit‡DiVittoria.MAGGIORANZA;
		} else if (modalit‡DiVittoria.equals("MAGGIORANZA_ASSOLUTA")) {
			this.modalit‡DiVittoria = Modalit‡DiVittoria.MAGGIORANZA_ASSOLUTA;
		} else if (modalit‡DiVittoria.equals("REFERENDUM_SENZA_QUORUM")) {
			this.modalit‡DiVittoria = Modalit‡DiVittoria.REFERENDUM_SENZA_QUORUM;
		} else if (modalit‡DiVittoria.equals("REFERENDUM_CON_QUORUM")) {
			this.modalit‡DiVittoria = Modalit‡DiVittoria.REFERENDUM_CON_QUORUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalit‡ di vittoria " + modalit‡DiVoto + " non valida");
		}
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Modalit‡DiVoto getModalit‡DiVoto() {
		return modalit‡DiVoto;
	}

	public void setModalit‡DiVoto(Modalit‡DiVoto modalit‡DiVoto) {
		this.modalit‡DiVoto = modalit‡DiVoto;
	}

	public Modalit‡DiVittoria getModalit‡DiVittoria() {
		return modalit‡DiVittoria;
	}

	public void setModalit‡DiVittoria(Modalit‡DiVittoria modalit‡DiVittoria) {
		this.modalit‡DiVittoria = modalit‡DiVittoria;
	}
	
	// TODO: toString()
}
