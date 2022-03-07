package data_access;

import java.util.List;

import com.gbrsni.votoelettronico.models.*;

public interface SessioneDiVotoDAO {
	public List<SessioneDiVoto> getAllSessioneDiVoto();
	public void updateSessioneDiVoto(SessioneDiVoto s);
	public void deleteSessioneDiVoto(SessioneDiVoto s);
	public void addSessioneDiVoto(int id, String nome);
}
