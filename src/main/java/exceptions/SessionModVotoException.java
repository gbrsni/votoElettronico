package exceptions;

import com.gbrsni.votoelettronico.models.ModVoto;

public class SessionModVotoException extends RuntimeException {
	public SessionModVotoException(String str) {
		super(str);
	}
	
	public SessionModVotoException(ModVoto found, int sessionId) {
		super("Modalità di voto " + found + " non valida per la sessione con id " + sessionId);
	}
}
