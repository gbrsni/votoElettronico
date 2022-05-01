package exceptions;

import com.gbrsni.votoelettronico.models.StatoSessione;

public class SessionStateException extends RuntimeException {
	public SessionStateException(String str) {
		super(str);
	}
	
	public SessionStateException(StatoSessione expected, StatoSessione found, int sessionId) {
		super("La sessione con id " + sessionId + " era in stato " + found + " invece che in stato " + expected);
	}
}
