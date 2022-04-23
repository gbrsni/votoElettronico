package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;
import java.util.Objects;

public class GetSessioneFactory {
	
	public SessioneDiVoto getSessione(ModVoto modVoto, int id, String nome, String descrizione, LocalDate data, ModVittoria modVittoria, StatoSessione statoSessione, int nvoti) throws Exception{
		Objects.requireNonNull(modVoto);
		
		switch(modVoto) {
		case ORDINALE:
			return new SessioneOrdinale(id,  nome, descrizione,  data,  modVittoria,  statoSessione,  nvoti); 
		case CATEGORICO:
			return new SessioneCategorico(id,  nome, descrizione,  data,  modVittoria,  statoSessione,  nvoti); 
		case CATEGORICO_CON_PREFERENZE:
			return new SessioneCategoricoPreferenze(id,  nome, descrizione,  data,  modVittoria,  statoSessione,  nvoti); 
		case REFERENDUM:
			break;
		default:
			throw new Exception("Modalità di voto non riconosciuta");		
		}
		return null;
	}
}
