import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SacADos {
	private float poidsMax, poidsActu = 0;
	private ArrayList<Objet> ListeObjet = new ArrayList<Objet>();
	private ArrayList<Objet> Danslesac = new ArrayList<Objet>();
	private String chemin; 
	
	public SacADos() {
		
	}
	
	//Création d'un sac à dos à partir d'un fichier texte
	
	public SacADos(String chemin, float poidsMax) {
		this.poidsMax = poidsMax;
		this.chemin = chemin;
		try {
			File f = new File(chemin);
	        BufferedReader b = new BufferedReader(new FileReader(f));
	        String ligne = "";
	        while ((ligne = b.readLine()) != null) {  							//lit le fichier texte ligne par ligne 
	        	String[] elem;
	        	elem = ligne.split(";");										//séparation de la chaîne de caractère à chaque fois qu'elle rencontre un ";"
	        	Objet o = new Objet(elem[0].substring(0, elem[0].length() - 1),	//Création d'un objet à partir des éléments du fichier texte
	        			Float.parseFloat(elem[1].replaceAll("\\s", "")),
	        			Float.parseFloat(elem[2].replaceAll("\\s", "")));
	        	this.ListeObjet.add(o);											//Ajour de l'objet à la liste d'objet
	        }
	        b.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
	    }
	}
	
	//Affichage des différents objets présents dans le sac
	
	public String toString() {
		StringBuilder s = new StringBuilder("");
		float val = 0, poids = 0; 
		for (Objet o : Danslesac) {
			s.append(o.getNom() + " ; " + o.getPoids() + " ; " + o.getValeur());
			s.append("\n");
			val += o.getValeur();
			poids += o.getPoids();
		}
		s.append("\n");
		s.append("Valeur : ");
		s.append(String.valueOf(val)); //affichage de la valeur du sac à dos 
		s.append(" Poids : ");
		s.append(String.valueOf(poids)); //affichage du poids du sac à dos 
		
		return s.toString();
	}
	
	//////////////////////////////////  GLOUTON  /////////////////////////////////////
	
	public void Glouton() {
		float rap[] = new float [ListeObjet.size()];	//tableau des rapports valeur/poids des objets 
		int i = 0;
		for(Objet o : ListeObjet) {
			rap[i] = o.getRapport();
			i++;
		}
		triFusion(rap);
		i = 0;
		for(float f : reverse(rap)) {
			for(Objet o : ListeObjet) {
				if (f == o.getRapport() && ((this.poidsActu + o.getPoids()) <= this.poidsMax) && o.getPris() == false) {
					o.setPris();
					setPoidsActu(o);
					Danslesac.add(o); //ajout dans le sac des objets réunissant les conditions
				}
			}
		}
		
	}
	
	//Retourne un tableau inversé 
	
	public float[] reverse(float tab[]) {
		float reverse[] = new float[tab.length];
		for(int i = 0; i < tab.length; i++) {
			reverse[i] = tab[tab.length - 1 - i];
		}
		return reverse;
	}
	
	//Permet de retourner un tableau trié dans l'ordre croissant
	
	public void triFusion(float tab[]){
		if (tab.length > 0) {
			triFusion(tab, 0, tab.length-1);
        }
    }

	private void triFusion(float tab[],int debut,int fin) {
		if (debut != fin) {
			int milieu = (fin + debut)/2;
			triFusion(tab, debut, milieu);
			triFusion(tab, milieu + 1, fin);
			fusion(tab, debut, milieu, fin);
         }
     }

	private void fusion(float tableau[], int debut1, int fin1, int fin2) {
		int debut2 = fin1 + 1;

     
		float table1[] = new float[fin1 - debut1 + 1];
		for(int i = debut1; i <= fin1; i++) {
			table1[i - debut1] = tableau[i];
        }
     
		int compteur1 = debut1;
		int compteur2 = debut2;
     
		for(int i = debut1; i <= fin2; i++) {        
			if (compteur1 == debut2)
				break; 
			
			else if (compteur2 == (fin2 + 1)) {
				tableau[i] = table1[compteur1 - debut1]; 
				compteur1++;
			}
			
			else if (table1[compteur1 - debut1] < tableau[compteur2]) {
				tableau[i] = table1[compteur1 - debut1]; 
				compteur1++;
			}
			
			else {
				tableau[i] = tableau[compteur2]; 
				compteur2++;
			}
		}
     }
	
	//Mise à jour du poids actuel du sac à dos
	
	private void setPoidsActu(Objet o) {
		this.poidsActu += o.getPoids();
	}
	
	//////////////////////////////////  DYNAMIQUE  /////////////////////////////////////
	
	public void Dynamique() {
		
		for (Objet o : ListeObjet) 
			o.TransfoInt();
		
		float M[][] = new float [ListeObjet.size()][(int) (this.poidsMax * 100 + 1)];
		
		for (int j = 0; j <= this.poidsMax * 100 ; j++) {
			if (ListeObjet.get(0).getPoids() > j)
				M[0][j] = 0;
			else
				M[0][j] = ListeObjet.get(0).getValeur();
		}
		
		for (int i = 1; i < ListeObjet.size(); i++) {
			for (int j = 0; j <= this.poidsMax * 100; j++) {
				if(ListeObjet.get(i).getPoids() > j)
					M[i][j] = M [i-1][j];
				else 
					M[i][j] = Math.max(M[i-1][j], M[i-1][(int) (j - ListeObjet.get(i).getPoids())] + ListeObjet.get(i).getValeur());
			}
		}
		
		int i = ListeObjet.size() - 1;
		int j = (int) (this.poidsMax * 100); 
		
		while(M[i][j] == (M[i][j-1]))
			--j;
		
		while(j > 0) {
			while(i > 0 && M[i][j] == M[i - 1][j])
				--i;
			j -= ListeObjet.get(i).getPoids();
			if(j >= 0)
				Danslesac.add(ListeObjet.get(i));
			--i;
		}
		
		for(Objet o : ListeObjet)
			o.DeTransfo();
	}
	
	//////////////////////////////////  PSE  /////////////////////////////////////
	
	public void Pse() {
		Arbre A = new Arbre(ListeObjet,poidsMax);
		for(Objet o : A.getMeilleur()) {
			Danslesac.add(o);
		}
	}
	
	
	////////////////////////////////// RESOUDRE /////////////////////////////////////
	
	public void resoudre(String mot) {
		if(mot.toLowerCase().equals("pse")) {
			Pse();
		}
			
		else if(mot.toLowerCase().equals("glouton")) {
			Glouton();
		}
			
		else if(mot.toLowerCase().equals("dynamique")) {
			Dynamique();
		}
			
		else {
			System.out.println("Erreur syntaxe : Méthode");
		}
	}
}
