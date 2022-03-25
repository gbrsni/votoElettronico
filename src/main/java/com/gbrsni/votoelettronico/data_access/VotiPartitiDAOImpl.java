package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Candidato;
import com.gbrsni.votoelettronico.models.Partito;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotiPartitiDAOImpl implements VotiPartitiDAO{
	
	private Connection connection = DBConnection.getConnection();
	
	//get partiti della sessione di voto
	@Override
	public Map<Partito, Integer> getVotiPartitiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		
		Map<Partito, Integer> res = new HashMap<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM partiti INNER JOIN votipartiti ON partiti.id = votipartiti.partiti WHERE votipartiti.sessioni = ?");
			ps.setInt(1, sessione.getId());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				res.put(new Partito(rs.getInt("id"), rs.getString("nome")), rs.getInt("nvoti"));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento voti dei candidati della sessione di voto" + sessione.toString());
			e.printStackTrace();
		}
		
		return res;
	}
	
	//aggiunge i partiti per la sessione di voto
	public void addVotiPartitiBySessione(SessioneDiVoto sessione,  Partito partito) {
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(partito);
		int i = 0;
		try {
			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO votipartiti (sessioni, partiti, nvoti) VALUES (?, ?, ?)");
			ps.setInt(1, sessione.getId());
			ps.setInt(2, partito.getId());
			ps.setInt(3, 0);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del partito  + " + partito.getId() + " per la sessione " + sessione.getId());
			e.printStackTrace();
			return;
		}
		System.out.println( "Relazione partiti - sessione di voto " + sessione.getId() + " aggiunta nel database");
		
	}
}
