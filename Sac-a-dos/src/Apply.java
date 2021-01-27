import java.util.Scanner;

public class Apply {

	public static void main(String[] args) {
		System.out.println("Ligne de commande :");
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		String[] mot;
		mot = str.split(" ");
		SacADos s = new SacADos(mot[1], Float.parseFloat(mot[2].replaceAll("\\s", "")));
		s.resoudre(mot[3]);
		System.out.println(s.toString());
			
		
	}	


}
