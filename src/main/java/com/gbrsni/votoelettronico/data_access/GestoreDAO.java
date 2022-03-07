package com.gbrsni.votoelettronico.data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.Gestore;

public interface GestoreDAO {
	public List<Gestore> getAllGestore();
	public void updateGestore(Gestore e);
	public void deleteGestore(Gestore e);
	public void addGestore(Gestore e);
	public void addGestore(String username, String nome);
}
