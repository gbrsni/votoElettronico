package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.exceptions.SessionStateException;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.StatoSessione;

import javafx.util.Pair;

public class VotiCandidatiDAOImpl implements VotiCandidatiDAO {
	
	private Connection connection = DBConnection.getConnection();
	
	/**ottiene i candidati con il relativo numero di voti per la sessione di voto*/
	@Override
	public Map<Candidato, Integer> getVotiCandidatiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<Candidato, Integer> res = new HashMap<>();

		try {
			ps = connection.prepareStatement("SELECT * FROM candidati INNER JOIN voticandidati ON candidati.id = voticandidati.candidati WHERE voticandidati.sessioni = ?");
			ps.setInt(1, sessione.getId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				PartitoDAO partitoDb = new PartitoDAOImpl();
				Partito partito = partitoDb.getPartitoById(Integer.valueOf(rs.getString("partiti")));
				res.put(new Candidato(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), partito), rs.getInt("nvoti"));
			}	
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento voti dei candidati della sessione di voto" + sessione.getId());
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	//aggiunta candidati per una sessione
	public void addVotiCandidatoBySessione(SessioneDiVoto sessione, Candidato candidato, int valore){
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(candidato);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO voticandidati (sessioni, candidati, nvoti) VALUES (?, ?, ?)");
			ps.setInt(1, sessione.getId());
			ps.setInt(2, candidato.getId());
			ps.setInt(3, valore);
			ps.executeUpdate();
			System.out.println("Relazione candidati - sessione di voto " + sessione.getId() + " aggiunta nel database ");
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del candidato " + candidato.getId() + " per la sessione di voto " + sessione.getId());
			e.printStackTrace();
			return;
		}
		finally {  DbUtils.closeStatement(ps); }
	}
	
	/**elimina tutti i candidati per la sessione di voto indicata*/
	@Override
	public void deleteVotiCandidatiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM voticandidati  WHERE sessioni = ?");
			ps.setInt(1, sessione.getId());
			ps.executeUpdate();
			System.out.println("Candidati per la sessione " + sessione.toString() + " rimossi dal database");
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione dei candidati per la sessione di voto " + sessione.toString());
			e.printStackTrace();
		}
		finally {  DbUtils.closeStatement(ps); }
	}

	// Prende come input la mappa calcolata da SessioneDiVoto.getConteggioVoti, che a sua volta prende come input
	// 	il risultato di votazioniCandidatiDAO.getVotazioniCandidatiBySessione
	public void setVotiCandidatiFromVotazioniBySessione(SessioneDiVoto sessione, Map<Candidato, Integer> conteggioCandidati) {
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(conteggioCandidati);
		
		if (sessione.getStatoSessione() == StatoSessione.CHIUSA) {
			
			for (Candidato c : conteggioCandidati.keySet()) {
				this.addVotiCandidatoBySessione(sessione, c, conteggioCandidati.get(c));
			}
			
			sessione.setStatoSessione(StatoSessione.SCRUTINATA);
		} else {
			throw new SessionStateException(StatoSessione.CHIUSA, sessione.getStatoSessione(), sessione.getId());
		}
	}
	
	public void setVotiCandidatiFromVotazioniBySessione(SessioneDiVoto sessione, Candidato candidato, int valore) {
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(candidato);
		try {
			
			PreparedStatement ps = connection.prepareStatement("UPDATE voticandidati SET nvoti = ? WHERE sessioni = ? AND candidati = ?");
			ps.setInt(1, valore);
			ps.setInt(2, sessione.getId());
			ps.setInt(3, candidato.getId());
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			// LOG
			e.printStackTrace();
			return;
		}
		// LOG
	}
}