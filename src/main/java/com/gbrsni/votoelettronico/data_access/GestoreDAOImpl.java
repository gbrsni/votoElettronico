package com.gbrsni.votoelettronico.data_access;

import java.sql.*;
import java.util.List;

import com.gbrsni.votoelettronico.models.Gestore;

public class GestoreDAOImpl implements GestoreDAO {
	private Connection connection;

	public GestoreDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Gestore> getAllGestore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGestore(Gestore e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteGestore(Gestore e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addGestore(Gestore e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addGestore(String username, String nome) {
		// TODO Auto-generated method stub

	}

}
