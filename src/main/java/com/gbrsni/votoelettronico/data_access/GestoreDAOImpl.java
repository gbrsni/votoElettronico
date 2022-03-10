package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Gestore;

public class GestoreDAOImpl implements GestoreDAO {
	private Connection connection;

	public GestoreDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Gestore> getAllGestore() {
		List<Gestore> res = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM gestori");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				res.add(new Gestore(rs.getString("username"), rs.getString("nome"), rs.getString("cognome")));
			}
			
			ps.close();
		} catch (SQLException g) {
			System.out.println("Errore durante l'ottenimento di tutti gli gestori");
			g.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void updateGestore(Gestore g) {
		Objects.requireNonNull(g);
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE gestori SET nome = ?, cognome = ? WHERE username = ?");
			ps.setString(1, g.getNome());
			ps.setString(2, g.getCognome());
			ps.setString(5, g.getUsername());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento del gestore " + g.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Aggiornato gestore " + g.toString() + " nel database");
	}

	@Override
	public void deleteGestore(Gestore g) {
		Objects.requireNonNull(g);
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM gestori WHERE username = ?");
			ps.setString(1, g.getUsername());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione del gestore " + g.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Rimosso gestore " + g.toString() + " dal database");
	}

	@Override
	public void addGestore(Gestore g) {
		Objects.requireNonNull(g);
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO gestori (username, nome, cognome) VALUES (?, ?, ?)");
			ps.setString(1, g.getUsername());
			ps.setString(2, g.getNome());
			ps.setString(3, g.getCognome());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del gestore " + g.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Inserito gestore " + g.toString() + " dal database");
	}

	@Override
	public void addGestore(String username, String nome, String cognome) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(nome);
		Objects.requireNonNull(cognome);
		Gestore g = new Gestore(username, nome, cognome);
		addGestore(g);
	}

}
