package com.gbrsni.votoelettronico.data_access;

import java.util.List;
import java.util.Map;

import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiPartitiDAO {
	
	public Map<Partito,Integer> getVotiPartitiBySessione(SessioneDiVoto sessione);
	public void addVotiPartitiBySessione(SessioneDiVoto sessione,  Partito partito);
}