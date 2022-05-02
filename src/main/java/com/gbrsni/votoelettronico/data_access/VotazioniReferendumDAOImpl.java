package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotazioniReferendumDAOImpl implements VotazioniReferendumDAO {
	
	private Connection connection = DBConnection.getConnection();
	
	/**ottiene tutti voti per la sessione di voto indicata*/
	@Override
	public List<OpzioneReferendum> getAllVotazioniReferendumBySessione(SessioneDiVoto sessioneDiVoto) {
		Objects.requireNonNull(sessioneDiVoto);
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OpzioneReferendum> res = new ArrayList<>();
		try {
			ps = connection.prepareStatement("SELECT * FROM votazionireferendum WHERE sessioni = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				res.add(OpzioneReferendum.valueOf(rs.getString("opzione")));
			}
			Logging.infoMessage(this.getClass(), "Ottenute votazioni referendum per la sessione con id " + sessioneDiVoto.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento votazioni referendum della sessione di voto" + sessioneDiVoto.toString() + "\n" + e.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**aggiunge votazione per la sessione di voto indicata*/
	@Override
	public void addVotazioniReferendumBySessione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(opzione);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO votazionireferendum (sessioni, opzione) VALUES (?, ?)");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setString(2, opzione.toString());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Inserita votazione per sessione " + sessioneDiVoto.getId() + " nel database");
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento di una votazione per la sessione " + sessioneDiVoto.getId());
			Logging.warnMessage(this.getClass(), "Errore durante l'inserimento di una votazione per la sessione " + sessioneDiVoto.getId() + "\n" + e.toString());
		}
		finally { DbUtils.closeStatement(ps); }

	}
}
