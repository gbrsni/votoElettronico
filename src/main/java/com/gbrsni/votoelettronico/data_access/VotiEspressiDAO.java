package com.gbrsni.votoelettronico.data_access;

import com.gbrsni.votoelettronico.models.Elettore;
import com.gbrsni.votoelettronico.models.SessioneDiVoto;

public interface VotiEspressiDAO {
	public boolean existsVotoEspresso(SessioneDiVoto sessioneDiVoto, Elettore elettore);
}
