package com.gbrsni.votoelettronico.data_access;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VincitoriDAO {
	public void addVincitori(Candidato candidato, SessioneDiVoto sessioneDiVoto);
	public Candidato getVincitori(SessioneDiVoto sessioneDiVoto);
}
