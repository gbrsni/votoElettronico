package com.gbrsni.votoelettronico.data_access;

import java.util.Map;

import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiPartitiDAO {
	
	public Map<Partito,Integer> getVotiPartitiBySessione(SessioneDiVoto sessione);
	public void addVotiPartitoBySessione(SessioneDiVoto sessione,  Partito partito);
	public void deleteVotiPartitiBySessione(SessioneDiVoto sessione);
	public void setVotiPartitiFromVotazioniBySessione(SessioneDiVoto sessione, Partito partito, int valore);
}
