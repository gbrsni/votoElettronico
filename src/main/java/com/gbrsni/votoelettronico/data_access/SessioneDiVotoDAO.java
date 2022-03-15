package com.gbrsni.votoelettronico.data_access;

import java.time.LocalDate;
import java.util.List;

import com.gbrsni.votoelettronico.models.*;

public interface SessioneDiVotoDAO {
	public List<SessioneDiVoto> getAllSessioneDiVoto();
	public List<SessioneDiVoto> getAllSessioneDiVotoByStato(StatoSessione statoSessione);
	public void updateSessioneDiVoto(SessioneDiVoto s);
	public void deleteSessioneDiVoto(SessioneDiVoto s);
	public void addSessioneDiVoto(SessioneDiVoto s);
	public void addSessioneDiVoto(int id, String nome, String descrizione, LocalDate data, ModVoto modVoto, ModVittoria modVittoria, StatoSessione statoSessione, int nvoti);
}
