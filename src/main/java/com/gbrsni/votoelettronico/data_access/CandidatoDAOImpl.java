package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;

public class CandidatoDAOImpl implements CandidatoDAO {
	private Connection connection = DBConnection.getConnection();


	/**restituisce i candidati presenti nel database*/
	@Override
	public List<Candidato> getAllCandidato() {
		List<Candidato> res = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try {
			ps = connection.prepareStatement("SELECT * FROM candidati");
			rs = ps.executeQuery();
			while (rs.next()) {
				PartitoDAOImpl partitoDb = new PartitoDAOImpl();
				Partito partito = partitoDb.getPartitoById(Integer.valueOf(rs.getString("partiti")));
				res.add(new Candidato(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), partito));		
			}
			Logging.infoMessage(this.getClass(), "Ottenuti tutti i candidati dal database");
		} catch (SQLException c) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento di tutti gli candidati dal database\n" + c.toString());
		}	
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**restituisce candidato con id indicato*/
	@Override
	public Candidato getCandidatoById(int id) {
		Candidato res = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM candidati WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				PartitoDAOImpl partitoDb = new PartitoDAOImpl();
				Partito partito = partitoDb.getPartitoById(Integer.valueOf(rs.getString("partiti")));
				res = new Candidato(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), partito);
			}
			Logging.infoMessage(this.getClass(), "Ottenuto candidato con id " + id);
		} catch (SQLException c) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento del candidato con id " + id +  "\n" + c.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**restituisce tutti i candidati di un partito*/
	@Override
	public List<Candidato> getAllCandidatoByPartito(Partito partito){
		Objects.requireNonNull(partito);
		List<Candidato> res = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM candidati WHERE partiti = ?");
			ps.setInt(1, partito.getId());
			rs = ps.executeQuery();
			while(rs.next())
				res.add(new Candidato(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), partito));
			Logging.infoMessage(this.getClass(), "Ottenuti tutti i candidati del partito con id " + partito.getId());
		} catch (SQLException c) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento dei candidati del partito con id " + partito.getId() + "\n" + c.toString());
		} 
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}

	@Override
	/**aggiorna il candidato c*/
	public void updateCandidato(Candidato c) {
		Objects.requireNonNull(c);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE candidati SET nome = ?, cognome = ?, partiti = ? WHERE id = ?");
			ps.setString(1, c.getNome());
			ps.setString(2, c.getCognome());
			ps.setInt(3, c.getPartito().id);
			ps.setInt(4, c.getId());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Aggiornato candidato con id " + c.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento del candidato con id " + c.getId() + "\n" + e.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	/**elimina il candidato c dal database.*/
	@Override
	public void deleteCandidato(Candidato c) {
		Objects.requireNonNull(c);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM candidati WHERE id = ?");
			ps.setInt(1, c.getId());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Rimosso candidato con id " + c.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante la rimozione del candidato con id " + c.getId() + "\n" + e.toString());
		}
		finally {  DbUtils.closeStatement(ps); }
	}

	/**aggiunge il candidato c al database*/
	@Override
	public void addCandidato(Candidato c) {
		Objects.requireNonNull(c);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO candidati (id, nome, cognome, partiti) VALUES (?, ?, ?, ?)");
			ps.setInt(1, c.getId());
			ps.setString(2, c.getNome());
			ps.setString(3, c.getCognome());
			ps.setInt(4, c.getPartito().id);
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Aggiunto candidato " + c.toString());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'aggiunta del candidato" + c.toString() + "\n" + e.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}

	@Override
	public void addCandidato(int id, String nome, String cognome, Partito partito) {
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);		
		Candidato c = new Candidato(id, nome, cognome, partito);
		addCandidato(c);
	}
}
