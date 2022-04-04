package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotazioniReferendumDAOImpl implements VotazioniReferendumDAO {
	
	private Connection connection = DBConnection.getConnection();

	@Override
	public void addVotazioniReferendumBySessione(SessioneDiVoto sessione, OpzioneReferendum opzione) {
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(opzione);

		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO votazionireferendum (sessioni, opzione) VALUES (?, ?)");
			ps.setInt(1, sessione.getId());
			ps.setString(2, opzione.toString());
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento di una votazione per la sessione " + sessione.getId());
			e.printStackTrace();
			return;
		}
		System.out.println( "Inserita votazione per sessione " + sessione.getId() + " nel database");
	}

}
