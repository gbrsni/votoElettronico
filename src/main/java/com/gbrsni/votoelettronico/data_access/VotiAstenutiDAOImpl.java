package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotiAstenutiDAOImpl implements VotiAstenutiDAO{
	
	private Connection connection = DBConnection.getConnection();

	@Override
	public int getVotiAstenutiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer res = 0; 
		try {
			ps = connection.prepareStatement("SELECT * FROM votiastenuti WHERE sessioni = ?");
			ps.setInt(1, sessione.getId());
			rs = ps.executeQuery();
			if (rs.next())
				res = rs.getInt("nvoti");
			Logging.infoMessage(this.getClass(), "Ottenuto numero voti astenuti della sessione di voto " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento numero astenuti della sessione di voto" + sessione.getId() + "\n" + e.toString());
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
		
	}

	@Override
	public void addVotiAstenutiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO votiastenuti (sessioni, nvoti) VALUES (?, ?)");
			ps.setInt(1, sessione.getId());
			ps.setInt(2, 0);
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Ottenuti voti astenuti per sessione con id " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'inserimento voti astenuti della sessione di voto" + sessione.toString() + "\n" + e.toString());
		}
		finally {  DbUtils.closeStatement(ps); }
	}
	

	public void increaseVotiAstenutiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE votiastenuti SET nvoti = nvoti + 1");
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Incrementato numero astenuti per la sessione con id " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'incremento del numero astenuti per la sessione con id" + sessione.getId() + "\n" + e.toString());
		}
		finally {DbUtils.closeStatement(ps); }
	}
}	
