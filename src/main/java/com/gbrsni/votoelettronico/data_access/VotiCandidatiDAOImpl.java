package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotiCandidatiDAOImpl implements VotiCandidatiDAO {
	private Connection connection = DBConnection.getConnection();

	@Override
	public Map<Candidato, Integer> getVotiCandidatiBySessione(SessioneDiVoto sessioneDiVoto) {
		Map<Candidato, Integer> res = new HashMap<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM voticandidati WHERE sessioni = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				PartitoDAO partitoDb = new PartitoDAOImpl();
				Partito partito = partitoDb.getPartitoById(Integer.valueOf(rs.getString("partiti")));
				res.put(new Candidato(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), partito), rs.getInt("nvoti"));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento dei voti dei candidati della sessione di voto" + sessioneDiVoto.toString());
			e.printStackTrace();
		}
		
		return res;
	}

}
