package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.List;

import com.gbrsni.votoelettronico.models.Elettore;

public class ElettoreDAOImpl implements ElettoreDAO {
	private Connection connection;

	public ElettoreDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Elettore> getAllElettore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateElettore(Elettore e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElettore(Elettore e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addElettore(Elettore e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addElettore(String username, String nome, String cognome, String codiceFiscale) {
		// TODO Auto-generated method stub

	}

}
