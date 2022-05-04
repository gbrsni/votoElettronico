package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.gbrsni.votoelettronico.logging.Logging;
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
			Logging.infoMessage(this.getClass(), "Ottenuti voti partiti per la sessione con id " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'ottenimento voti dei candidati della sessione di voto" + sessione.toString() + "\n" + e.toString());
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
			Logging.infoMessage(this.getClass(), "Relazione partiti - sessione di voto " + sessione.getId() + " aggiunta nel database");;
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'inserimento del partito  + " + partito.getId() + " per la sessione " + sessione.getId() + "\n" + e.toString());
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
			Logging.infoMessage(this.getClass(), "Partiti per la sessione " + sessione.toString() + " rimossi dal database");
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante la rimozione dei partiti per la sessione di voto " + sessione.toString() + "\n" + e.toString());
		}
		finally { DbUtils.closeStatement(ps); }
	}
	
	public void setVotiPartitiBySessione(SessioneDiVoto sessione, Map<Partito, Integer> conteggioPartiti) {
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(conteggioPartiti);
			for (Partito c : conteggioPartiti.keySet()) {
				updateVotiPartitiBySessione(sessione, c, conteggioPartiti.get(c));
			}
	}
	
	public void updateVotiPartitiBySessione(SessioneDiVoto sessione, Partito partito, int valore) {
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(partito);
		try {
			
			PreparedStatement ps = connection.prepareStatement("UPDATE votipartiti SET nvoti = ? WHERE sessioni = ? AND partiti = ?");
			ps.setInt(1, valore);
			ps.setInt(2, sessione.getId());
			ps.setInt(3, partito.getId());
			ps.executeUpdate();
			ps.close();
			Logging.infoMessage(this.getClass(), "Update voti partiti per il partito " + partito.toString() + " nella sessione con id " + sessione.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'update voti partiti per il partito " + partito.toString() + " nella sessione con id " + sessione.getId() + "\n" + e.toString());
			return;
		}
	}
	
	public void increaseVotiPartitiBySessione(SessioneDiVoto sessione,Partito partito, int valore) {
		Objects.requireNonNull(sessione);
		Objects.requireNonNull(partito);
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE votipartiti SET nvoti = nvoti + ? WHERE sessioni = ? AND partiti = ?");
			ps.setInt(1, valore);
			ps.setInt(2, sessione.getId());
			ps.setInt(3, partito.getId());
			ps.executeUpdate();
			Logging.infoMessage(this.getClass(), "Incrementato voti ricevuti per il partito " + partito.getId());
		} catch (SQLException e) {
			Logging.warnMessage(this.getClass(), "Errore durante l'incremento del numero di voti ricevuti per il partito con id" + partito.getId() + "\n" + e.toString());
		}
		finally {DbUtils.closeStatement(ps); }
	}
}
