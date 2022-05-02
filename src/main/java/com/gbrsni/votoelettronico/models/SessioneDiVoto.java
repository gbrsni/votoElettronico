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

import com.gbrsni.votoelettronico.data_access.VotazioniCandidatiDAO;
import com.gbrsni.votoelettronico.data_access.VotazioniCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAO;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAO;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAO;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAOImpl;
import com.gbrsni.votoelettronico.exceptions.SessionModVotoException;
import com.gbrsni.votoelettronico.exceptions.SessionStateException;

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

	public int setId(int id) {
		return this.id = id;
	}
	
	public void contaVoti() {
		if (this.statoSessione == StatoSessione.CHIUSA) {			
			VotazioniCandidatiDAO votazioniCandidatiDAO = new VotazioniCandidatiDAOImpl();
			List<Pair<Candidato, Integer>> votazioniCandidati = votazioniCandidatiDAO.getVotazioniCandidatiBySessione(this);
			Map<Candidato, Integer> conteggioCandidati = SessioneDiVoto.getConteggioVoti(votazioniCandidati);
			
			VotiCandidatiDAO votiCandidatiDAO = new VotiCandidatiDAOImpl();
			for (Candidato c : conteggioCandidati.keySet()) {
				votiCandidatiDAO.addVotiCandidatoBySessione(this, c, conteggioCandidati.get(c));
			}
			
			this.setStatoSessione(StatoSessione.SCRUTINATA);
		} else {
			throw new SessionStateException(StatoSessione.CHIUSA, this.statoSessione, this.id);
		}
	}
	
	public static <O extends OpzioneDiVoto> Map<O, Integer> getConteggioVoti(List<Pair<O, Integer>> voti) {
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
	
	// Prende in input l'output di getVotiPartitiBySessione
	public Partito getPartitoVincitore(Map<Partito,Integer> voti) {
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
			throw new SessionModVotoException(this.getModVoto(), this.getId());
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