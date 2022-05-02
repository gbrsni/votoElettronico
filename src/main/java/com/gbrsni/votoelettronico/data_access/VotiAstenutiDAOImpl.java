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
		
		Integer res = 0; 

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM votiastenuti WHERE sessioni = ?");
			ps.setInt(1, sessione.getId());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				res = rs.getInt("nvoti");
			ps.close();
			Logging.infoMessage(this.getClass(), "Ottenuto numero voti astenuti della sessione di voto " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento numero astenuti della sessione di voto" + sessione.getId() + "\n" + e.toString());
		}
		
		return res;
		
	}

	@Override
	public void addVotiAstenutiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		try {
			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO votiastenuti (sessioni, nvoti) VALUES (?, ?)");
			ps.setInt(1, sessione.getId());
			ps.setInt(2, 0);
			ps.executeUpdate();
			ps.close();
			Logging.infoMessage(this.getClass(), "Ottenuti voti astenuti per sessione con id " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'inserimento votireferendum della sessione di voto" + sessione.toString() + "\n" + e.toString());
			return;
		}
		System.out.println("Inserimento nel db effettuato");

	}
	

	public void increaseVotiAstenutiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE votiastenuti SET nvoti = nvoti + 1");
			ps.executeUpdate();
			ps.close();
			Logging.infoMessage(this.getClass(), "Incrementato numero astenuti per la sessione con id " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'incremento del numero astenuti per la sessione con id" + sessione.getId() + "\n" + e.toString());
		}
	}
}	
