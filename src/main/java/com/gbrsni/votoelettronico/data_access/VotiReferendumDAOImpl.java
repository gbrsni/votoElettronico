package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento dei voti della sessione " + sessioneDiVoto);
			e.printStackTrace();
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
			System.out.println("Aggiunto record per la sessione Referendum " + sessioneDiVoto);
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiunta del record per la sessione Referendum" + sessioneDiVoto);
			e.printStackTrace();
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
			System.out.println("Incrementato il numero dei voti per la sessione" + sessioneDiVoto);
		} catch (SQLException e) {
			System.out.println("Errore durante l'incremento dei voti per la sessione " + sessioneDiVoto);
			e.printStackTrace();
		}
		finally {  DbUtils.closeStatement(ps); }

	}

}
