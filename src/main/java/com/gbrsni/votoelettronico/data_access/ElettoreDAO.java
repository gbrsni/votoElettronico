package com.gbrsni.votoelettronico.data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SaltedPassword;

public interface ElettoreDAO {
	public List<Elettore> getAllElettore();
	public void updateElettore(Elettore e);
	public void deleteElettore(Elettore e);
	public void addElettore(Elettore e);
	public void addElettore(String username, String nome, String cognome, String tesseraElettorale, String codiceFiscale);
	public Elettore getElettoreByUsername(String username);
	public Elettore getElettoreByTesseraElettorale(String tesseraElettorale);
	public SaltedPassword getPasswordElettoreByUsername(String username);
	public void setPasswordElettoreByUsername(String username, SaltedPassword sp);
	public int getNumberElettori();
}
