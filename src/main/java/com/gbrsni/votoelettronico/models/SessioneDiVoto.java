package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SessioneDiVoto {
	private final int id;
	private String nome;
	private ModalitąDiVoto modalitąDiVoto;
	private ModalitąDiVittoria modalitąDiVittoria;
	
	public SessioneDiVoto(int id, String nome, ModalitąDiVoto modalitąDiVoto, ModalitąDiVittoria modalitąDiVittoria) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modalitąDiVoto);
		Objects.requireNonNull(modalitąDiVittoria);
		
		this.id = id;
		this.nome = nome;
		this.modalitąDiVoto = modalitąDiVoto;
		this.modalitąDiVittoria = modalitąDiVittoria;
	}

	public SessioneDiVoto(int id, String nome, String modalitąDiVoto, String modalitąDiVittoria) throws IllegalArgumentException {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modalitąDiVoto);
		Objects.requireNonNull(modalitąDiVittoria);
		
		this.id = id;
		this.nome = nome;
		
		if (modalitąDiVoto.equals("ORDINALE")) {
			this.modalitąDiVoto = ModalitąDiVoto.ORDINALE;
		} else if (modalitąDiVoto.equals("CATEGORICO")) {
			this.modalitąDiVoto = ModalitąDiVoto.CATEGORICO;
		} else if (modalitąDiVoto.equals("CATEGORICO_CON_PREFERENZE")) {
			this.modalitąDiVoto = ModalitąDiVoto.CATEGORICO_CON_PREFERENZE;
		} else if (modalitąDiVoto.equals("REFERENDUM")) {
			this.modalitąDiVoto = ModalitąDiVoto.REFERENDUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalitą di voto " + modalitąDiVoto + " non valida");
		}
		
		if (modalitąDiVoto.equals("MAGGIORANZA")) {
			this.modalitąDiVittoria = ModalitąDiVittoria.MAGGIORANZA;
		} else if (modalitąDiVittoria.equals("MAGGIORANZA_ASSOLUTA")) {
			this.modalitąDiVittoria = ModalitąDiVittoria.MAGGIORANZA_ASSOLUTA;
		} else if (modalitąDiVittoria.equals("REFERENDUM_SENZA_QUORUM")) {
			this.modalitąDiVittoria = ModalitąDiVittoria.REFERENDUM_SENZA_QUORUM;
		} else if (modalitąDiVittoria.equals("REFERENDUM_CON_QUORUM")) {
			this.modalitąDiVittoria = ModalitąDiVittoria.REFERENDUM_CON_QUORUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalitą di vittoria " + modalitąDiVoto + " non valida");
		}
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	// TODO: toString()
}
