package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VincitoriDAOImpl implements VincitoriDAO {
	
	private Connection connection = DBConnection.getConnection();

	@Override
	public void addVincitori(Candidato candidato, SessioneDiVoto sessioneDiVoto) {
		Objects.requireNonNull(sessioneDiVoto);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO vincitori (sessioni, candidati) VALUES (?, ?)");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setInt(2, candidato.getId());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Inserito vincitore per la sessione con id " + sessioneDiVoto.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'inserimento del vincitore della sessione " + sessioneDiVoto.toString() + "\n" + e.toString());
		}
		finally {  DbUtils.closeStatement(ps); }
	}

}
