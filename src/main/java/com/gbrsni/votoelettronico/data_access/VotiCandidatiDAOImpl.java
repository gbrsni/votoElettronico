package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotiCandidatiDAOImpl implements VotiCandidatiDAO {
	
	private Connection connection = DBConnection.getConnection();
	
	//get candidati per una sessione
	@Override
	public Map<Candidato, Integer> getVotiCandidatiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		
		Map<Candidato, Integer> res = new HashMap<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM candidati INNER JOIN voticandidati ON candidati.id = voticandidati.candidati WHERE voticandidati.sessioni = ?");
			ps.setInt(1, sessione.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				PartitoDAO partitoDb = new PartitoDAOImpl();
				Partito partito = partitoDb.getPartitoById(Integer.valueOf(rs.getString("partiti")));
				res.put(new Candidato(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), partito), rs.getInt("nvoti"));
			}	
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento voti dei candidati della sessione di voto" + sessione.getId());
			e.printStackTrace();
		}
		return res;
	}
	
	//aggiunta candidati per una sessione
	public void addVotiCandidatoBySessione(SessioneDiVoto sessione, Candidato candidato){
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(candidato);
		try {
			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO voticandidati (sessioni, candidati, nvoti) VALUES (?, ?, ?)");
			ps.setInt(1, sessione.getId());
			ps.setInt(2, candidato.getId());
			ps.setInt(3, 0);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del candidato " + candidato.getId() + " per la sessione di voto " + sessione.getId());
			e.printStackTrace();
			return;
		}
		System.out.println("Relazione candidati - sessione di voto " + sessione.getId() + " aggiunta nel database ");
	}
	
	@Override
	public void deleteVotiCandidatiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement("DELETE FROM voticandidati  WHERE sessioni = ?");
			ps.setInt(1, sessione.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione dei candidati per la sessione di voto " + sessione.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Candidati per la sessione " + sessione.toString() + " rimossi dal database");
	}

}