package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class SessioneDiVotoDAOImpl implements SessioneDiVotoDAO {
	private Connection connection;
	
	public SessioneDiVotoDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<SessioneDiVoto> getAllSessioneDiVoto() {
		List<SessioneDiVoto> res = new ArrayList<SessioneDiVoto>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM sessioniDiVoto");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				res.add(new SessioneDiVoto(rs.getInt(1), rs.getString(2)));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento di tutte le sessioni di voto");
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public void updateSessioneDiVoto(SessioneDiVoto s) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE sessioniDiVoto SET nome = ? WHERE id = ?");
			ps.setString(1, s.getNome());
			ps.setInt(2, s.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento della sessione di voto " + s.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Aggiornata sessione di voto " + s.toString() + " nel database");
	}

	@Override
	public void deleteSessioneDiVoto(SessioneDiVoto s) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM sessioniDiVoto  WHERE id = ?");
			ps.setInt(1, s.getId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione della sessione di voto " + s.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Sessione di voto " + s.toString() + " rimossa dal database");
	}

	@Override
	public void addSessioneDiVoto(int id, String nome) {
		SessioneDiVoto s = new SessioneDiVoto(id, nome);
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO sessioniDiVoto (id, nome) VALUES (?, ?)");
			ps.setInt(1, s.getId());
			ps.setString(2, s.getNome());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento della sessione di voto " + s.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Sessione di voto " + s.toString() + " inserita nel database");
	}

}
