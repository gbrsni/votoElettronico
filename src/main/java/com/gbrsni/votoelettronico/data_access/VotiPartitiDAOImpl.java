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
	
	/**ottiene tutti i partiti per la sessione di voto*/
	@Override
	public Map<Partito, Integer> getVotiPartitiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		Map<Partito, Integer> res = new HashMap<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM partiti INNER JOIN votipartiti ON partiti.id = votipartiti.partiti WHERE votipartiti.sessioni = ?");
			ps.setInt(1, sessione.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				res.put(new Partito(rs.getInt("id"), rs.getString("nome")), rs.getInt("nvoti"));
			}
		} catch (SQLException e) {
			System.out.println("Errore durante l'ottenimento voti dei candidati della sessione di voto" + sessione.toString());
			e.printStackTrace();
		}
		finally { DbUtils.closeResultSet(rs); DbUtils.closeStatement(ps); }
		return res;
	}
	
	/**aggiunge il partito per la sessione di voto*/
	public void addVotiPartitoBySessione(SessioneDiVoto sessione,  Partito partito) {
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(partito);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO votipartiti (sessioni, partiti, nvoti) VALUES (?, ?, ?)");
			ps.setInt(1, sessione.getId());
			ps.setInt(2, partito.getId());
			ps.setInt(3, 0);
			ps.executeUpdate();
			System.out.println( "Relazione partiti - sessione di voto " + sessione.getId() + " aggiunta nel database");
		} catch (SQLException e) {
			System.out.println("Errore durante l'inserimento del partito  + " + partito.getId() + " per la sessione " + sessione.getId());
			e.printStackTrace();
		}
		finally {  DbUtils.closeStatement(ps); }
	}
	
	/**elimina tutti i partiti della sessione di voto*/
	@Override
	public void deleteVotiPartitiBySessione(SessioneDiVoto sessione) {
		Objects.requireNonNull(sessione);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM votipartiti  WHERE sessioni = ?");
			ps.setInt(1, sessione.getId());
			ps.executeUpdate();
			System.out.println("Partiti per la sessione " + sessione.toString() + " rimossi dal database");
		} catch (SQLException e) {
			System.out.println("Errore durante la rimozione dei partiti per la sessione di voto " + sessione.toString());
			e.printStackTrace();
		}
		finally { DbUtils.closeStatement(ps); }
	}
}
