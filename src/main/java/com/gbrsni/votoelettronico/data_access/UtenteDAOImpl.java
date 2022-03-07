package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gbrsni.votoelettronico.models.SessioneDiVoto;
import com.gbrsni.votoelettronico.models.Utente;

public class UtenteDAOImpl implements UtenteDAO {
	private Connection connection;
	
	public UtenteDAOImpl(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public List<Utente> getAllUtente() {
		List<Utente> res = new ArrayList<>();

		ElettoreDAO elettoreDAO = new ElettoreDAOImpl(connection);
		GestoreDAO gestoreDAO = new GestoreDAOImpl(connection);
		
		res.addAll(elettoreDAO.getAllElettore());
		res.addAll(gestoreDAO.getAllGestore());
		
		return res;
	}

	@Override
	public void updateUtente(Utente u) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE utenti SET nome = ?, cognome = ? WHERE username = ?");
			ps.setString(1, u.getNome());
			ps.setString(2, u.getCognome());
			ps.setString(3, u.getUsername());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento dell'utente " + u.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Aggiornato utente " + u.toString() + " nel database");
	}

	@Override
	public void deleteUtente(Utente u) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM utenti  WHERE username = ?");
			ps.setString(1, u.getUsername());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione dell'utente " + u.toString());
			e.printStackTrace();
			return;
		}
		System.out.println("Utente " + u.toString() + " rimosso dal database");
	}

}
