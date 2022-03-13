package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SessioneDiVoto {
	private final int id;
	private String nome;
	private ModalitāDiVoto modalitāDiVoto;
	private ModalitāDiVittoria modalitāDiVittoria;
	private StatoSessione statoSessione;
	
	public SessioneDiVoto(int id, String nome, ModalitāDiVoto modalitāDiVoto, ModalitāDiVittoria modalitāDiVittoria, StatoSessione statoSessione) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modalitāDiVoto);
		Objects.requireNonNull(modalitāDiVittoria);
		
		this.id = id;
		this.nome = nome;
		this.modalitāDiVoto = modalitāDiVoto;
		this.modalitāDiVittoria = modalitāDiVittoria;
		
		if (statoSessione == null) {
			this.statoSessione = StatoSessione.CREATA;
		} else {
			this.statoSessione = statoSessione;
		}
	}

	public SessioneDiVoto(int id, String nome, String modalitāDiVoto, String modalitāDiVittoria) throws IllegalArgumentException {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modalitāDiVoto);
		Objects.requireNonNull(modalitāDiVittoria);
		
		this.id = id;
		this.nome = nome;
		
		if (modalitāDiVoto.equals("ORDINALE")) {
			this.modalitāDiVoto = ModalitāDiVoto.ORDINALE;
		} else if (modalitāDiVoto.equals("CATEGORICO")) {
			this.modalitāDiVoto = ModalitāDiVoto.CATEGORICO;
		} else if (modalitāDiVoto.equals("CATEGORICO_CON_PREFERENZE")) {
			this.modalitāDiVoto = ModalitāDiVoto.CATEGORICO_CON_PREFERENZE;
		} else if (modalitāDiVoto.equals("REFERENDUM")) {
			this.modalitāDiVoto = ModalitāDiVoto.REFERENDUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalitā di voto " + modalitāDiVoto + " non valida");
		}
		
		if (modalitāDiVoto.equals("MAGGIORANZA")) {
			this.modalitāDiVittoria = ModalitāDiVittoria.MAGGIORANZA;
		} else if (modalitāDiVittoria.equals("MAGGIORANZA_ASSOLUTA")) {
			this.modalitāDiVittoria = ModalitāDiVittoria.MAGGIORANZA_ASSOLUTA;
		} else if (modalitāDiVittoria.equals("REFERENDUM_SENZA_QUORUM")) {
			this.modalitāDiVittoria = ModalitāDiVittoria.REFERENDUM_SENZA_QUORUM;
		} else if (modalitāDiVittoria.equals("REFERENDUM_CON_QUORUM")) {
			this.modalitāDiVittoria = ModalitāDiVittoria.REFERENDUM_CON_QUORUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalitā di vittoria " + modalitāDiVoto + " non valida");
		}
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public ModalitāDiVoto getModalitāDiVoto() {
		return modalitāDiVoto;
	}

	public void setModalitāDiVoto(ModalitāDiVoto modalitāDiVoto) {
		Objects.requireNonNull(modalitāDiVoto);
		this.modalitāDiVoto = modalitāDiVoto;
	}

	public ModalitāDiVittoria getModalitāDiVittoria() {
		return modalitāDiVittoria;
	}

	public void setModalitāDiVittoria(ModalitāDiVittoria modalitāDiVittoria) {
		Objects.requireNonNull(modalitāDiVittoria);
		this.modalitāDiVittoria = modalitāDiVittoria;
	}

	public StatoSessione getStatoSessione() {
		return statoSessione;
	}

	public void setStatoSessione(StatoSessione statoSessione) {
		Objects.requireNonNull(statoSessione);
		this.statoSessione = statoSessione;
	}
	
	// TODO: toString()
}
