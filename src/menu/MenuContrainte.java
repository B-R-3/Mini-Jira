package src.menu;

import src.model.Contrainte;
import java.util.Scanner;
import src.Enum.EnumContrainte;

public class MenuContrainte {

    public void afficherMenuContrainte() {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        int choix = 0;
        do {
            System.out.println("--------------------------------");
            System.out.println("Bienvenue dans le menu des contraintes");
            System.out.println("--------------------------------");
            System.out.println("1. Ajouter une contrainte");
            System.out.println("2. Supprimer une contrainte");
            System.out.println("3. Afficher toutes les contraintes");
            System.out.println("4. Retour au menu principal");
            System.out.println("5. Quitter");
            System.out.println("--------------------------------");
            System.out.println("Entrez votre choix : ");
            System.out.println("--------------------------------");
            choix = scanner.nextInt(); // ← Lire APRÈS avoir affiché le menu
            // scanner.nextLine(); // Consommer le \n
            switch (choix) {
                case 1:
                    scanner.nextLine(); // Consomme le retour à la ligne restant
                    System.out.println("(Ajouter une contrainte) Entrez le libelle de la contrainte : ");
                    String libelle = scanner.nextLine(); 
                    System.out.println("(Ajouter une contrainte) Entrez le statut de la contrainte : ");
                    break;
                case 2:
                    System.out.println("Supprimer une contrainte");
                    // supprimerContrainte();

                    break;
                case 3:
                    System.out.println("Afficher toutes les contraintes");
                
                    scanner.nextLine(); // Consomme le retour à la ligne restant
                    break;
                case 4:
                    System.out.println("Retour au menu principal");
                    continuer = false;
                    break;
                case 5:
                    System.out.println("Quitter");
                    scanner.close();               
                    continuer = false;
                    break;
            }
        } while (continuer);
    }
}