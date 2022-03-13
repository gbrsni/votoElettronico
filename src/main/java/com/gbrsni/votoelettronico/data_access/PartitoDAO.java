package com.gbrsni.votoelettronico.data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.Partito;

public interface PartitoDAO {
	public List<Partito> getAllPartito();
	public void updatePartito(Partito p);
	public void deletePartito(Partito p);
	public void addPartito(Partito p);
	public void addPartito(int id, String nome);
}
