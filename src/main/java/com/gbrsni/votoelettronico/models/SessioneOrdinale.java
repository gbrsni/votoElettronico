package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.data_access.VotazioniCandidatiDAO;
import com.gbrsni.votoelettronico.data_access.VotazioniCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotazioniPartitiDAO;
import com.gbrsni.votoelettronico.data_access.VotazioniPartitiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiAstenutiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAO;
import com.gbrsni.votoelettronico.data_access.VotiEspressiDAOImpl;
import com.gbrsni.votoelettronico.data_access.VotiPartitiDAOImpl;

public class SessioneOrdinale extends SessioneDiVoto{
	
	public SessioneOrdinale(int id, String nome, String descrizione, LocalDate data, ModVittoria modVittoria,
			StatoSessione statoSessione, int nvoti) {
		super(id, nome, descrizione, data, modVittoria, statoSessione, nvoti);
	}
	
	
	
	public ModVoto getModVoto() {
		return ModVoto.ORDINALE;
	}
	
}
