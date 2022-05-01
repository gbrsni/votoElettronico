package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

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
			System.out.println("Ottenuto numero voti astenuti della sessione di voto " + sessione.getId());
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento numero astenuti della sessione di voto" + sessione.getId());
			e.printStackTrace();
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
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento ");
			e.printStackTrace();
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
			System.out.println("Incrementato numero astenuti per la sessione con id " + sessione.getId());
		} catch (SQLException e) {
			System.out.println("Errore durante l'incremento del numero astenuti per la sessione con id" + sessione.getId());
			e.printStackTrace();
		}
	}
}	
