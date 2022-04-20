package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotazioniReferendumDAOImpl implements VotazioniReferendumDAO {
	
	private Connection connection = DBConnection.getConnection();

	@Override
	public List<OpzioneReferendum> getAllVotazioniReferendumBySessione(SessioneDiVoto sessioneDiVoto) {
		Objects.requireNonNull(sessioneDiVoto);
		
		List<OpzioneReferendum> res = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM votazionireferendum WHERE sessioni = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				res.add(OpzioneReferendum.valueOf(rs.getString("opzione")));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento votazioni referendum della sessione " + sessioneDiVoto.toString());
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void addVotazioniReferendumBySessione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(opzione);

		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO votazionireferendum (sessioni, opzione) VALUES (?, ?)");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setString(2, opzione.toString());
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento di una votazione per la sessione " + sessioneDiVoto.getId());
			e.printStackTrace();
			return;
		}
		System.out.println( "Inserita votazione per sessione " + sessioneDiVoto.getId() + " nel database");
	}
}
