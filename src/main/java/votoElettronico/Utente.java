package votoElettronico;

public abstract class Utente {
	private int id;
	
	public Utente(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
