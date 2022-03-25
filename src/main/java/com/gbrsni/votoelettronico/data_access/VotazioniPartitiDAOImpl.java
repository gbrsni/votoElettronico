package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotazioniPartitiDAOImpl implements VotazioniPartitiDAO {
	private Connection connection = DBConnection.getConnection();

	@Override
	public Map<Partito, Integer> getVotazioniPartitiBySessione(SessioneDiVoto sessioneDiVoto) {
		Objects.requireNonNull(sessioneDiVoto);
		
		Map<Partito, Integer> res = new HashMap<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM partiti INNER JOIN votipartiti ON partiti.id = votipartiti.partiti WHERE votipartiti.sessioni = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				PartitoDAO partitoDb = new PartitoDAOImpl();
				Partito partito = partitoDb.getPartitoById(Integer.valueOf(rs.getString("partiti")));
				res.put(partito, rs.getInt("valore"));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento votazioni dei partiti della sessione di voto" + sessioneDiVoto.toString());
			e.printStackTrace();
		}
		
		return res;
	}
	
	private boolean existsVotiPartiti(SessioneDiVoto sessioneDiVoto, Partito partito) throws SQLException {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(partito);

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM votazionipartiti WHERE sessioni = ?, partiti = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setInt(2, partito.getId());
			ResultSet rs = ps.executeQuery();
			
			ps.close();

			return rs.next();
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public void setVotazioniPartitiBySessione(SessioneDiVoto sessioneDiVoto, Partito partito, int voti) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(partito);
		
		try {
			PreparedStatement ps = null;
			if (existsVotiPartiti(sessioneDiVoto, partito)) {
				ps = connection.prepareStatement("UPDATE votazionipartiti SET nvoti = ? WHERE (sessioni = ?, partiti = ?)");
				ps.setInt(1, voti);
				ps.setInt(2, sessioneDiVoto.getId());
				ps.setInt(3, partito.getId());
			} else {
				ps = connection.prepareStatement("INSERT INTO votazionipartiti (sessioni, partiti, valore) VALUES (?, ?, ?)");
				ps.setInt(1, sessioneDiVoto.getId());
				ps.setInt(2, partito.getId());
				ps.setInt(3, voti);
			}
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento votazioni del partito " + partito.toString() + " per la sessione di voto " + sessioneDiVoto.toString());
			e.printStackTrace();
			return;
		}
		
		System.out.println("Aggiornate votazioni del partito " + partito.toString() + " per la sessione di voto " + sessioneDiVoto.toString());
	}
}
