package com.gbrsni.votoelettronico.models;

import java.util.Objects;

public class SessioneDiVoto {
	private final int id;
	private String nome;
	private ModVoto modVoto;
	private ModVittoria modVittoria;
	private StatoSessione statoSessione;
	
	public SessioneDiVoto(int id, String nome, ModVoto modVoto, ModVittoria modVittoria, StatoSessione statoSessione) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modVoto);
		Objects.requireNonNull(modVittoria);
		
		this.id = id;
		this.nome = nome;
		this.modVoto = modVoto;
		this.modVittoria = modVittoria;
		
		if (statoSessione == null) {
			this.statoSessione = StatoSessione.CREATA;
		} else {
			this.statoSessione = statoSessione;
		}
	}

	public SessioneDiVoto(int id, String nome, String modVoto, String modVittoria, String statoSessione) throws IllegalArgumentException {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(modVoto);
		Objects.requireNonNull(modVittoria);
		
		this.id = id;
		this.nome = nome;
		
		if (modVoto.equals("ORDINALE")) {
			this.modVoto = ModVoto.ORDINALE;
		} else if (modVoto.equals("CATEGORICO")) {
			this.modVoto = ModVoto.CATEGORICO;
		} else if (modVoto.equals("CATEGORICO_CON_PREFERENZE")) {
			this.modVoto = ModVoto.CATEGORICO_CON_PREFERENZE;
		} else if (modVoto.equals("REFERENDUM")) {
			this.modVoto = ModVoto.REFERENDUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalità di voto " + modVoto + " non valida");
		}
		
		if (modVittoria.equals("MAGGIORANZA")) {
			this.modVittoria = ModVittoria.MAGGIORANZA;
		} else if (modVittoria.equals("MAGGIORANZA_ASSOLUTA")) {
			this.modVittoria = ModVittoria.MAGGIORANZA_ASSOLUTA;
		} else if (modVittoria.equals("REFERENDUM_SENZA_QUORUM")) {
			this.modVittoria = ModVittoria.REFERENDUM_SENZA_QUORUM;
		} else if (modVittoria.equals("REFERENDUM_CON_QUORUM")) {
			this.modVittoria = ModVittoria.REFERENDUM_CON_QUORUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalità di vittoria " + modVittoria + " non valida");
		}
		
		if (statoSessione == null || statoSessione.equals("CREATA")) {
			this.statoSessione = StatoSessione.CREATA;
		} else if (statoSessione.equals("CONFIGUIRATA")) {
			this.statoSessione = StatoSessione.CONFIGURATA;
		} else if (statoSessione.equals("IN_CORSO")) {
			this.statoSessione = StatoSessione.IN_CORSO;
		} else if (statoSessione.equals("CONCLUSA")) {
			this.statoSessione = StatoSessione.CONCLUSA;
		} else if (statoSessione.equals("SCRUTINATA")) {
			this.statoSessione = StatoSessione.SCRUTINATA;
		}
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public ModVoto getModVoto() {
		return modVoto;
	}

	public void setModVoto(ModVoto modVoto) {
		Objects.requireNonNull(modVoto);
		this.modVoto = modVoto;
	}

	public ModVittoria getModVittoria() {
		return modVittoria;
	}

	public void setModVittoria(ModVittoria modVittoria) {
		Objects.requireNonNull(modVittoria);
		this.modVittoria = modVittoria;
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
