package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;

public class CandidatoDAOImpl implements CandidatoDAO {
	private Connection connection = DBConnection.getConnection();

	public CandidatoDAOImpl() {}
	
	/**restituisce i candidati presenti nel database.*/
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
			
		} catch (SQLException c) {
			System.out.println("Errore durante l'ottenimento di tutti gli candidati dal database");
			c.printStackTrace();
		}	
		
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**Restituisce candidato con specifico id se presente, null altrimenti */
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
			
		} catch (SQLException c) {
			System.out.println("Errore durante l'ottenimento del candidato con id " + id);
			c.printStackTrace();
		}
		
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**restituisce tutti i candidati di un partito. */
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
			
		} catch (SQLException c) {
			System.out.println("Errore durante l'ottenimento dei candidati del partito " + partito.getId());
			c.printStackTrace();
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
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento del candidato " + c.toString());
			e.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}

	@Override
	public void deleteCandidato(Candidato c) {
		Objects.requireNonNull(c);
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement("DELETE FROM candidati WHERE id = ?");
			ps.setInt(1, c.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione del candidato " + c.toString());
			e.printStackTrace();
		}
		
		finally {  DbUtils.closeStatement(ps); }
	}

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
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del candidato " + c.toString());
			e.printStackTrace();
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
