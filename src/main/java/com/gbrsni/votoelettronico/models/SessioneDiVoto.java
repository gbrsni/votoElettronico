package com.gbrsni.votoelettronico.models;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAO;
import com.gbrsni.votoelettronico.data_access.VotiCandidatiDAOImpl;

public class SessioneDiVoto {
	private int id;
	private String nome;
	private String descrizione;
	private LocalDate data;
	private ModVoto modVoto;
	private ModVittoria modVittoria;
	private StatoSessione statoSessione;
	private int nvoti;
	
	
	public SessioneDiVoto(int id, String nome, String descrizione, LocalDate data, ModVoto modVoto, ModVittoria modVittoria, StatoSessione statoSessione, int nvoti) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(data);
		Objects.requireNonNull(modVoto);
		Objects.requireNonNull(modVittoria);
		Objects.requireNonNull(nvoti);
		
		this.id = id;
		this.nome = nome;
		this.modVoto = modVoto;
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

	
	public SessioneDiVoto(int id, String nome, String descrizione, LocalDate data,String modVoto, String modVittoria, String statoSessione, int nvoti) throws IllegalArgumentException {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(data);
		Objects.requireNonNull(modVoto);
		Objects.requireNonNull(modVittoria);
		Objects.requireNonNull(nvoti);
		
		this.id = id;
		this.nome = nome;
		this.descrizione = descrizione;
		this.data = data;
		this.nvoti = nvoti; 
		
		if (modVoto.equals("ORDINALE")) {
			this.modVoto = ModVoto.ORDINALE;
		} else if (modVoto.equals("CATEGORICO")) {
			this.modVoto = ModVoto.CATEGORICO;
		} else if (modVoto.equals("CATEGORICO_CON_PREFERENZE")) {
			this.modVoto = ModVoto.CATEGORICO_CON_PREFERENZE;
		} else if (modVoto.equals("REFERENDUM")) {
			this.modVoto = ModVoto.REFERENDUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalità di voto " + modVoto + " non valida");
		}
		
		if (modVittoria.equals("MAGGIORANZA")) {
			this.modVittoria = ModVittoria.MAGGIORANZA;
		} else if (modVittoria.equals("MAGGIORANZA_ASSOLUTA")) {
			this.modVittoria = ModVittoria.MAGGIORANZA_ASSOLUTA;
		} else if (modVittoria.equals("REFERENDUM_SENZA_QUORUM")) {
			this.modVittoria = ModVittoria.REFERENDUM_SENZA_QUORUM;
		} else if (modVittoria.equals("REFERENDUM_CON_QUORUM")) {
			this.modVittoria = ModVittoria.REFERENDUM_CON_QUORUM;
		} else {
			throw new IllegalArgumentException("Impossibile istanziare sessione di voto: modalità di vittoria " + modVittoria + " non valida");
		}
		
		if (statoSessione.equals("CHIUSA")) {
			this.statoSessione = StatoSessione.CHIUSA;
		} else if (statoSessione.equals("IN_CORSO")) {
			this.statoSessione = StatoSessione.IN_CORSO;
		} else if (statoSessione.equals("CONCLUSA")) {
			this.statoSessione = StatoSessione.CONCLUSA;
		} else if (statoSessione.equals("SCRUTINATA")) {
			this.statoSessione = StatoSessione.SCRUTINATA;
		}
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	
	public LocalDate getData() {
		return data;
		
	}
	public ModVoto getModVoto() {
		return modVoto;
	}

	public void setModVoto(ModVoto modVoto) {
		Objects.requireNonNull(modVoto);
		this.modVoto = modVoto;
	}

	public ModVittoria getModVittoria() {
		return modVittoria;
	}

	public void setModVittoria(ModVittoria modVittoria) {
		Objects.requireNonNull(modVittoria);
		this.modVittoria = modVittoria;
	}

	public StatoSessione getStatoSessione() {
		return statoSessione;
	}
	
	public int getNvoti() {
		return nvoti;
	}
	public void setStatoSessione(StatoSessione statoSessione) {
		Objects.requireNonNull(statoSessione);
		this.statoSessione = statoSessione;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<Candidato, Integer> getVotiCandidati() {
		VotiCandidatiDAO votiCandidatiDAO = new VotiCandidatiDAOImpl();
		return votiCandidatiDAO.getVotiCandidatiBySessione(this);
	}
	
	public void setVotiCandidato(Candidato candidato, int voti) {
		Objects.requireNonNull(candidato);
		
		VotiCandidatiDAO votiCandidatiDAO = new VotiCandidatiDAOImpl();
		votiCandidatiDAO.setVotiCandidatiBySessione(this, candidato, voti);
	}
	
	// TODO: toString()
}