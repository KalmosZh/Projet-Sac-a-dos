import java.util.ArrayList;

public class Arbre {
	
	private static float PoidsMax = 0;
	private static float BorneInf = 0;
	private static ArrayList<Objet> ListeObjet = new ArrayList<Objet>();
	private static ArrayList<Objet> ListeMeilleur = new ArrayList<Objet>();
	
	private ArrayList<Objet> Valeur = new ArrayList<Objet>();
	private Arbre Arbre_haut = null;
	private Arbre Arbre_bas = null;
	private float BorneSup;
	
	//création de la racine de l'arbre
	
	public Arbre(ArrayList<Objet> ListeObjet, float PoidsMax) {
		this.Valeur = null;

		Arbre.PoidsMax = PoidsMax;
		Arbre.ListeObjet = ListeObjet;
		
		ArrayList<Objet> Vide = new ArrayList<Objet>();
		ArrayList<Objet> Init = new ArrayList<Objet>();
		Init.add(ListeObjet.get(0));
		this.Arbre_haut = new Arbre(Init,1);
		this.Arbre_bas = new Arbre(Vide,1);
	}
	
	//création des différents noeuds de l'arbre
	
	public Arbre(ArrayList<Objet> ObjetNoeud, int profondeur) {
		if(profondeur <= Arbre.ListeObjet.size()) {
			if(poids(ObjetNoeud) <= Arbre.PoidsMax) {
				calculBorneInf(ObjetNoeud); 		
			}	//calcul des bornes 
			calculBorneSup(ObjetNoeud,profondeur);	
		
			this.Valeur = ObjetNoeud;
		
			
			
			if(this.BorneSup >= Arbre.BorneInf && poids(ObjetNoeud) <= Arbre.PoidsMax) {
				
				ArrayList<Objet> ObjArbHaut = new ArrayList<Objet>();
				ArrayList<Objet> ObjArbBas = new ArrayList<Objet>();
		
				for (Objet o : ObjetNoeud) {
					ObjArbHaut.add(o);	//copie du noeud dans le sous arbre bas et haut 
					ObjArbBas.add(o);
				}
				
				if (valeurNoeud(ObjetNoeud) > valeurNoeud(Arbre.ListeMeilleur)) {
					Arbre.ListeMeilleur.clear();
					for(Objet o : ObjetNoeud)
						Arbre.ListeMeilleur.add(o); //ajout dans la liste des objets correspondant à la meilleur solution 
				}
				
				if(profondeur < Arbre.ListeObjet.size()) {
					ObjArbHaut.add(ListeObjet.get(profondeur));
					this.Arbre_haut = new Arbre(ObjArbHaut,profondeur + 1);	//appel rescursif au constructeur, pour construire le reste de l'arbre
					this.Arbre_bas = new Arbre(ObjArbBas,profondeur + 1);
				}
			}
		}
	}
	
	//getteur pour la liste contenant la solution optiamale
	
	public ArrayList<Objet> getMeilleur(){
		return Arbre.ListeMeilleur;
	}
	
	//retourne le poids d'une liste d'objet

	private float poids(ArrayList<Objet> Valeur) {
		float poids = 0;
		for (Objet o : Valeur) {
			poids += o.getPoids();
		}
		return poids;
	}
	
	//retourne la valeur d'une liste d'objet
	
	private float valeurNoeud(ArrayList<Objet> Valeur) {
		if(Valeur.isEmpty())
			return 0;
		float valeur = 0;
		for (Objet o : Valeur) {
			valeur += o.getValeur();
		}
		return valeur;
	}
	
	//calcule la borne inférieure de l'arbre et l'a met à jour si besoin
	
	private void calculBorneInf(ArrayList<Objet> Valeur) {
		float val = valeurNoeud(Valeur);
		if(val > Arbre.BorneInf)
			Arbre.BorneInf = val;
	}
	
	//calcule la borne supérieure de chaque noeud
	
	private void calculBorneSup(ArrayList<Objet> Valeur, int profondeur) {
		float bornesup = 0;
		for(int i = profondeur; i < Arbre.ListeObjet.size(); i++) {
			bornesup += Arbre.ListeObjet.get(i).getValeur();
		}
		bornesup += valeurNoeud(Valeur);
		this.BorneSup = bornesup;
	}
	
}
