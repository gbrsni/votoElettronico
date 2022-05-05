package com.gbrsni.votoelettronico.data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiEspressiDAO {
	public boolean existsVotoEspresso(SessioneDiVoto sessioneDiVoto, Elettore elettore);
	public List<SessioneDiVoto> allExistsVotoEspressoByElettore(Elettore elettore);
	public void addVotoEspresso(SessioneDiVoto sessione, Elettore elettore);
	public int getNumberVotesBySessione(SessioneDiVoto sessione);
}
