package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
import com.gbrsni.votoelettronico.models.Utente;

public class UtenteDAOImpl implements UtenteDAO {
	private Connection connection = DBConnection.getConnection();
	
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
}
