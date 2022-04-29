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
	
	/**ottiene tutti i partiti della sessione di voto*/
	@Override
	public Map<Partito, Integer> getVotazioniPartitiBySessione(SessioneDiVoto sessioneDiVoto) {
		Objects.requireNonNull(sessioneDiVoto);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<Partito, Integer> res = new HashMap<>();

		try {
			ps = connection.prepareStatement("SELECT * FROM votazionipartiti WHERE sessioni = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				PartitoDAO partitoDb = new PartitoDAOImpl();
				Partito partito = partitoDb.getPartitoById(Integer.valueOf(rs.getString("partiti")));
				res.put(partito, rs.getInt("valore"));
			}
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento votazioni dei partiti della sessione di voto" + sessioneDiVoto.toString());
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**aggiunge la votazione per il partito nella sessione di voto indicata*/
	@Override
	public void addVotazioniPartitiBySessione(SessioneDiVoto sessioneDiVoto, Partito partito, int valore) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(partito);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO votazionipartiti (sessioni, partiti, valore) VALUES (?, ?, ?)");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setInt(2, partito.getId());
			ps.setInt(3, valore);
			ps.executeUpdate();
			System.out.println("Aggiornate votazioni del partito " + partito.toString() + " per la sessione di voto " + sessioneDiVoto.toString());

		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento votazioni del partito " + partito.toString() + " per la sessione di voto " + sessioneDiVoto.toString());
			e.printStackTrace();
		}
		finally {DbUtils.closeStatement(ps); }
	}
}
