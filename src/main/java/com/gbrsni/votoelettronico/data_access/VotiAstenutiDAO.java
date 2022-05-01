package com.gbrsni.votoelettronico.data_access;

import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiAstenutiDAO {
	public int getVotiAstenutiBySessione(SessioneDiVoto sessione);
	public void addVotiAstenutiBySessione(SessioneDiVoto sessione);
	public void increaseVotiAstenutiBySessione(SessioneDiVoto sessione);
}
