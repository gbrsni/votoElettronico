package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.OpzioneReferendum;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotiReferendumDAOImpl implements VotiReferendumDAO {
	
	private Connection connection = DBConnection.getConnection();
	
	/**ottiene il numero di voti per l'opzione di voto indicata*/
	@Override
	public int getNVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(opzione);		
		PreparedStatement ps = null;
		ResultSet rs = null;
		int nvoti = 0;
		String optString = "";
		try {
			ps = connection.prepareStatement("SELECT ? FROM votireferendum where sessioni = ?");		
			optString = opzione.toString();
			ps.setString(1, optString);
			ps.setInt(2, sessioneDiVoto.getId());
			rs = ps.executeQuery();	
			nvoti = rs.getInt(1);
			Logging.infoMessage(this.getClass(), "Ottenuto numero voti per l'opzione " + opzione + " nella sessione con id " + sessioneDiVoto.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento dei voti della sessione " + sessioneDiVoto + "\n" + e.toString());
			nvoti = -1;
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return nvoti;
	}
	
	/**Aggiunge un nuovo record per la sessione di voto*/
	@Override
	public void addNewVotiSessioneReferendum(SessioneDiVoto sessioneDiVoto) {
		Objects.requireNonNull(sessioneDiVoto);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO votireferendum (sessioni, nvoti1,nvoti2,nastenuti,vincitore) VALUES (?,?,?,?,?)");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setInt(2, 0);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.setString(5, null);			
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Aggiunto record per la sessione Referendum " + sessioneDiVoto);
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'aggiunta del record per la sessione Referendum" + sessioneDiVoto + "\n" + e.toString());
		}
		finally {  DbUtils.closeStatement(ps); }
	}
	
	/**aggiorna il numero di voti per l'opzione indicata*/
	@Override
	public void setVotiBySessioneOpzione(SessioneDiVoto sessioneDiVoto, OpzioneReferendum opzione) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(opzione);
		PreparedStatement ps = null;
		int nvoti = getNVotiBySessioneOpzione(sessioneDiVoto, opzione);
		String optString = "";
		try {
			ps = connection.prepareStatement("UPDATE votireferendum SET ? = ? WHERE sessioni = ?");
			optString = opzione.toString();
			ps.setString(1, optString);
			ps.setInt(2, nvoti);
			ps.setInt(3, sessioneDiVoto.getId());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Incrementato il numero dei voti per la sessione" + sessioneDiVoto);
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'incremento dei voti per la sessione " + sessioneDiVoto+ "\n" + e.toString());
		}
		finally {  DbUtils.closeStatement(ps); }

	}

}
