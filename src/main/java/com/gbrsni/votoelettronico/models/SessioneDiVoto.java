package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;
import java.util.Objects;

public abstract class SessioneDiVoto {
	
	private final int id;
	private String nome;
	private String descrizione;
	private LocalDate data;
	private ModVittoria modVittoria;
	private StatoSessione statoSessione;
	private int nvoti;
	
	public SessioneDiVoto(int id, String nome, String descrizione, LocalDate data, ModVittoria modVittoria, StatoSessione statoSessione, int nvoti) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(data);
		Objects.requireNonNull(modVittoria);
		Objects.requireNonNull(nvoti);
		
		this.id = id;
		this.nome = nome;
		this.modVittoria = modVittoria;
		this.descrizione = descrizione;
		this.data = data;
		this.nvoti = nvoti; 
		
		if (statoSessione == null) {
			this.statoSessione = StatoSessione.CHIUSA;
		} else {
			this.statoSessione = statoSessione;
		}
	}



	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public ModVittoria getModVittoria() {
		return modVittoria;
	}

	public void setModVittoria(ModVittoria modVittoria) {
		this.modVittoria = modVittoria;
	}

	public StatoSessione getStatoSessione() {
		return statoSessione;
	}

	public void setStatoSessione(StatoSessione statoSessione) {
		this.statoSessione = statoSessione;
	}

	public int getNvoti() {
		return nvoti;
	}

	public void setNvoti(int nvoti) {
		this.nvoti = nvoti;
	}

	public abstract ModVoto getModVoto();
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override 
	public int hashCode() {
		return this.id;
	}
	@Override
	public String toString() {
		return this.nome;
	}
}