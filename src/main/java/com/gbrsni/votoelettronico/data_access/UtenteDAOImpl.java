package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Utente;

public class UtenteDAOImpl implements UtenteDAO {
	private Connection connection = DBConnection.getConnection();
	
	public UtenteDAOImpl() {}
	
	/**resituisce tutti gli utenti */
	@Override
	public List<Utente> getAllUtente() {
		List<Utente> res = new ArrayList<>();

		ElettoreDAO elettoreDAO = new ElettoreDAOImpl();
		GestoreDAO gestoreDAO = new GestoreDAOImpl();
		
		res.addAll(elettoreDAO.getAllElettore());
		res.addAll(gestoreDAO.getAllGestore());
		
		return res;
	}
	
	/**aggiorna l'utente u*/
	@Override
	public void updateUtente(Utente u) {
		Objects.requireNonNull(u);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE utenti SET nome = ?, cognome = ? WHERE username = ?");
			ps.setString(1, u.getNome());
			ps.setString(2, u.getCognome());
			ps.setString(3, u.getUsername());
			ps.executeUpdate();
			System.out.println("Aggiornato utente " + u.toString() + " nel database");
		} catch (SQLException e) {
			System.out.println("Errore durante l'aggiornamento dell'utente " + u.toString());
			e.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	/**elimina l'utente u dal database*/
	@Override
	public void deleteUtente(Utente u) {
		Objects.requireNonNull(u);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM utenti  WHERE username = ?");
			ps.setString(1, u.getUsername());
			ps.executeUpdate();
			System.out.println("Utente " + u.toString() + " rimosso dal database");
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione dell'utente " + u.toString());
			e.printStackTrace();
		}
		finally {  DbUtils.closeStatement(ps); }
	}

}
