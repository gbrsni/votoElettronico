package votoElettronico;

/**Classe Concreta Elettore */
public class Elettore extends Utente{
		
	private String tesseraElettorale;

	public Elettore(String codiceFiscale, String nome, String cognome, String email, String tesseraElettorale){
		super(codiceFiscale, nome, cognome, email);
		this.tesseraElettorale = tesseraElettorale;
	}

	public gettessetaElettorale(){
		return this.tesseraElettorale;
	}

}
