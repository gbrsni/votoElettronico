package com.gbrsni.votoelettronico.data_access;

import java.util.Map;

import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiPartitiDAO {
	
	public Map<Partito,Integer> getVotiPartitiBySessione(SessioneDiVoto sessione);
	public void addVotiPartitoBySessione(SessioneDiVoto sessione,  Partito partito);
	public void deleteVotiPartitiBySessione(SessioneDiVoto sessione);
	public void setVotiPartitiBySessione(SessioneDiVoto sessione, Map<Partito, Integer> conteggioPartiti);
	public void updateVotiPartitiBySessione(SessioneDiVoto sessione, Partito partito, int valore);
	public void increaseVotiPartitiBySessione(SessioneDiVoto sessione,Partito partito, int valore);
}
