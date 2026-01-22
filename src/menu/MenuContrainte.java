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
            System.out.println("2. Afficher toutes les contraintes");
            System.out.println("3. Modifier le statut d'une contrainte");
            System.out.println("4. Supprimer une contrainte");
            System.out.println("5. Retour au menu principal");
            System.out.println("6. Quitter");
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
                    new Contrainte().ajouterContrainteInteractif(libelle);
                    break;
                case 2:
                    System.out.println("Afficher toutes les contraintes");
                    new Contrainte().afficherToutesLesContraintes();
                    scanner.nextLine(); // Consomme le retour à la ligne restant
                    break;
                case 3:
                    System.out.println("Modifier le statut d'une contrainte");
                    new Contrainte().modifierStatutContrainte(scanner);
                    break;
                case 4:
                    System.out.println("Supprimer une contrainte");
                    new Contrainte().supprimerContrainte(scanner);
                    break;
                case 5:
                    System.out.println("Retour au menu principal");
                    continuer = false;
                    break;
                case 6:
                    System.out.println("Quitter");
                    scanner.close();               
                    continuer = false;
                    break;
            }
        } while (continuer);
    }
}