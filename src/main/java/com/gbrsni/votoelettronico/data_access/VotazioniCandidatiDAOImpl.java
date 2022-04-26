package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotazioniCandidatiDAOImpl implements VotazioniCandidatiDAO {
	private Connection connection = DBConnection.getConnection();

	@Override
	public Map<Candidato, Integer> getVotazioniCandidatiBySessione(SessioneDiVoto sessioneDiVoto) {
		Objects.requireNonNull(sessioneDiVoto);
		
		Map<Candidato, Integer> res = new HashMap<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM votazionicandidati WHERE sessioni = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				PartitoDAO partitoDb = new PartitoDAOImpl();
				Partito partito = partitoDb.getPartitoById(Integer.valueOf(rs.getString("partiti")));
				res.put(new Candidato(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), partito), rs.getInt("valore"));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento votazioni dei candidati della sessione di voto" + sessioneDiVoto.toString());
			e.printStackTrace();
		}
		
		return res;
	}
	
	@Override
	public void addVotazioniCandidatiBySessione(SessioneDiVoto sessioneDiVoto, Candidato candidato, int valore) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(candidato);
		PreparedStatement ps = null;
		
		try {	
			ps = connection.prepareStatement("INSERT INTO votazionicandidati (sessioni, candidati, valore) VALUES (?, ?, ?)");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setInt(2, candidato.getId());
			ps.setInt(3, valore);
		
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento votazioni del candidato " + candidato.toString() + " per la sessione di voto " + sessioneDiVoto.toString());
			e.printStackTrace();
			return;
		}
		
		System.out.println("Aggiornate votazioni del candidato " + candidato.toString() + " per la sessione di voto " + sessioneDiVoto.toString());
	}
}
