package com.gbrsni.votoelettronico.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public class VotiEspressiDAOImpl implements VotiEspressiDAO {
	private Connection connection = DBConnection.getConnection();

	// Attenzione! Restituisce false anche se viene sollevata un'eccezione durante l'esecuzione del metodo
	@Override
	public boolean existsVotoEspresso(SessioneDiVoto sessioneDiVoto, Elettore elettore) {
		Objects.requireNonNull(sessioneDiVoto);
		Objects.requireNonNull(elettore);

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM votiespressi WHERE sessioni = ?, elettori = ?");
			ps.setInt(1, sessioneDiVoto.getId());
			ps.setString(2, elettore.getUsername());
			ResultSet rs = ps.executeQuery();
			
			ps.close();

			return rs.next();
		} catch (SQLException e) {
			System.out.println("Errore durante la ricerca del voto espresso da " + elettore.toString() + " per la sessione di voto " + sessioneDiVoto.toString());
			e.printStackTrace();
			return false;
		}
	}

}
