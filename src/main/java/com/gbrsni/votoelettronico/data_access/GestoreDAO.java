package com.gbrsni.votoelettronico.data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.Gestore;
import com.gbrsni.votoelettronico.models.SaltedPassword;

public interface GestoreDAO {
	public List<Gestore> getAllGestore();
	public void updateGestore(Gestore e);
	public void deleteGestore(Gestore e);
	public void addGestore(Gestore e);
	public void addGestore(String username, String nome, String cognome);
	public Gestore getGestoreByUsername(String username);
	public SaltedPassword getPasswordGestoreByUsername(String username);
	public void setPasswordGestoreByUsername(String username, SaltedPassword sp);
}
