package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

import javafx.util.Pair;


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

	public SessioneDiVoto(int id, String nome, String descrizione, LocalDate data,String modVittoria, String statoSessione, int nvoti) throws IllegalArgumentException {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(data);
		Objects.requireNonNull(modVittoria);
		Objects.requireNonNull(nvoti);
		
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.data = data;
		this.nvoti = nvoti; 
		
		
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
		
		if (statoSessione.equals("CHIUSA")) {
			this.statoSessione = StatoSessione.CHIUSA;
		} else if (statoSessione.equals("IN_CORSO")) {
			this.statoSessione = StatoSessione.IN_CORSO;
		} else if (statoSessione.equals("CONCLUSA")) {
			this.statoSessione = StatoSessione.CONCLUSA;
		} else if (statoSessione.equals("SCRUTINATA")) {
			this.statoSessione = StatoSessione.SCRUTINATA;
		}
	}
	
	protected static <O> Map<O, Integer> getClassifica(List<Pair<O, Integer>> voti) {
		Map<O, Integer> res = new TreeMap<>();
		
		for (Pair<O, Integer> voto : voti) {
			O opzione = voto.getKey();
			Integer valore = voto.getValue();
			
			if (res.containsKey(opzione)) {
				valore = valore + res.get(opzione);
			}
			
			res.put(opzione, valore);
		}
		
		return res;
	}
	
	protected static List<Candidato> getClassificaCandidati(Map<Candidato, Integer> classifica) {
		List<Entry<Candidato, Integer>> l = new ArrayList<>(classifica.entrySet());
		l.sort(Entry.comparingByValue());

		List<Candidato> res = new ArrayList<>(classifica.keySet());
		
		for (Entry<Candidato, Integer> e : l) {
			res.add(e.getKey());
		}
		
		Collections.reverse(res);
		
		return res;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
}