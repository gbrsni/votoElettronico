package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;


public abstract class SessioneDiVoto {
	
	private int id;
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
	
	public <V> Map<V,Integer> calcolaVincitore(Map<V,Integer> vincitori) {
		int max = Collections.max(vincitori.values());
		Map<V,Integer> keys = new HashMap<>();
		for (Entry<V, Integer> entry : vincitori.entrySet()) {
		    if (entry.getValue()==max) {
		        keys.put(entry.getKey(),entry.getValue());
		    }
		}
		return keys;
	}
	
	public int setId(int id) {
		return this.id = id;
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
		Logging.infoMessage(this.getClass(), "Aggiornato stato della sessione con id " + this.getId() + " a " + statoSessione);
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