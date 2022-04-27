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

import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAO;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAO;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAO;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAOImpl;

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
	
	public abstract void votaCandidato(Candidato candidato);
	public abstract void votaPartito(Partito partito);
	
	// Per votazioni con preferenze, il candidato in posizione 0 nella list ha preferenza maggiore. Idem per i partiti
	public abstract void votaCandidati(List<Candidato> candidati);
	public abstract void votaPartiti(List<Partito> partiti);
	
	protected boolean haVotato(Elettore elettore) {
		VotiEspressiDAO votiEspressiDAO = new VotiEspressiDAOImpl();
		return votiEspressiDAO.existsVotoEspresso(this, elettore);
	}
	
	protected static <O extends OpzioneDiVoto> Map<O, Integer> getConteggioVoti(List<Pair<O, Integer>> voti) {
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
	
	protected static <O extends OpzioneDiVoto> List<O> getClassifica(Map<O, Integer> voti) {
		List<Entry<O, Integer>> l = new ArrayList<>(voti.entrySet());
		l.sort(Entry.comparingByValue());

		List<O> res = new ArrayList<>(voti.keySet());
		
		for (Entry<O, Integer> e : l) {
			res.add(e.getKey());
		}
		
		Collections.reverse(res);
		
		return res;
	}
	
	public Candidato getCandidatoVincitore() {
		switch (this.modVittoria) {
		case MAGGIORANZA:
			return getCandidatoVincitoreMaggioranza();
		case MAGGIORANZA_ASSOLUTA:
			return getCandidatoVincitoreMaggioranzaAssoluta();
		default:
			throw new RuntimeException("Modalità di voto non valida");
		}
	}
	
	private Candidato getCandidatoVincitoreMaggioranza() {
		VotiCandidatiDAO votiCandidatiDAO = new VotiCandidatiDAOImpl();
		Map<Candidato, Integer> voti = votiCandidatiDAO.getVotiCandidatiBySessione(this);
		List<Candidato> classifica = SessioneDiVoto.getClassifica(voti);
		return classifica.get(0);
	}
	
	private Candidato getCandidatoVincitoreMaggioranzaAssoluta() {
		VotiCandidatiDAO votiCandidatiDAO = new VotiCandidatiDAOImpl();
		Map<Candidato, Integer> voti = votiCandidatiDAO.getVotiCandidatiBySessione(this);
		List<Candidato> classifica = SessioneDiVoto.getClassifica(voti);
		
		Candidato vincitore = classifica.get(0);
		
		int minVoti = (this.nvoti / 2) + 1;
		
		if (voti.get(vincitore) >= minVoti) {
			return vincitore;
		} else {
			return null;
		}
	}
	
	public Partito getPartitoVincitore() {
		VotiPartitiDAO votiPartitiDAO = new VotiPartitiDAOImpl();
		Map<Partito,Integer> voti = votiPartitiDAO.getVotiPartitiBySessione(this);
		List<Partito> classifica = SessioneDiVoto.getClassifica(voti);
		
		Partito vincitore = classifica.get(0);
		
		switch (this.modVittoria) {
		case MAGGIORANZA:
			return vincitore;
		case MAGGIORANZA_ASSOLUTA:			
			int minVoti = (this.nvoti / 2) + 1;
			
			if (voti.get(vincitore) >= minVoti) {
				return vincitore;
			} else {
				return null;
			}
		default:
			throw new RuntimeException("Modalità di voto non valida");
		}
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