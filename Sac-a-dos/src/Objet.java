
public class Objet {
	private float valeur, poids, rapport;
	private String nom;
	private boolean pris;
	
	public Objet(String nom, float poids, float valeur) {
		this.valeur = valeur; 
		this.poids = poids;
		this.rapport = valeur/poids;
		this.nom = nom;
		this.pris = false;
	}
	
	public Objet(Objet o) {
		this.valeur = o.getValeur();
		this.nom = o.getNom();
		this.poids = o.getPoids();
	}
	
	//Getteurs pour les attributs private des objets 
	
	public float getValeur() {
		return this.valeur;
	}
	
	public float getPoids() {
		return this.poids;
	}
	
	public float getRapport() {
		return this.rapport;
	}

	public String getNom() {
		return this.nom;
	}
	
	public boolean getPris() {
		return this.pris;
	}
	
	public void setPris() {
		this.pris = true;
	}
	//transformer les float en int 
	public void TransfoInt() {
		this.poids = this.poids * 100;
		this.valeur = this.valeur * 100;
	}
	//transformation des int en float
	public void DeTransfo() {
		this.poids = this.poids / 100;
		this.valeur = this.valeur / 100;
		
	}
}