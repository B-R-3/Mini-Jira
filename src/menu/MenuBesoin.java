package src.menu;

import src.model.Besoin;
import java.util.Scanner;
import java.util.Date;
import src.Enum.EnumBesoin;

public class MenuBesoin {

    public void afficherMenuBesoin() {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        int choix = 0;
        do {
            System.out.println("--------------------------------");
            System.out.println("Bienvenue dans le menu des besoins");
            System.out.println("--------------------------------");
            System.out.println("1. Ajouter un besoin");
            System.out.println("2. Supprimer un besoin");
            System.out.println("3. Afficher tous les besoins");
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
                    System.out.println("(Ajouter un besoin) Entrez le libelle du besoin : ");
                    String libelle = scanner.nextLine(); 
                    System.out.println("(Ajouter un besoin) Entrez le statut du besoin : ");
                    EnumBesoin enumBesoin = EnumBesoin.valueOf(scanner.nextLine()); 
                    Date dateCreation = new Date();
                    Besoin besoin = new Besoin(0, libelle, enumBesoin, dateCreation, "", 0, false);
                    besoin.ajouterBesoin();
                    // continuer = false;
                    break;
                case 2:
                    System.out.println("Supprimer un besoin");
                    // supprimerBesoin();
                    continuer = false;
                    break;
                case 3:
                    System.out.println("Afficher tous les besoins");
                    Besoin besoin2 = new Besoin(0, "", EnumBesoin.A_ANALYSER, new Date(), "", 0, false);
                    besoin2.afficherTousLesBesoins();
                    // continuer = false;
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