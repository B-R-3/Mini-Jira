package src.menu;

import java.util.Scanner;

public class Menu {

    public void afficherMenuPrincipal() {
        // scanner, outils pour entrer des données
        Scanner scanner = new Scanner(System.in);
        int choix = 0;
        boolean continuer = true;
        do {
            System.out.println("Menu interactif (Choisisez une option entre 1 et 4) ");
            System.out.println("1. Besoin");
            System.out.println("2. Rapport de réunion");
            System.out.println("3. Contraintes");
            System.out.println("4. Quitter");
            System.out.println("--------------------------------");
            System.out.println("Entrez votre choix : ");
            System.out.println("--------------------------------");


            choix = scanner.nextInt();
            // scanner.nextLine(); // Consomme le retour à la ligne restant
            switch (choix) {
                case 1:
                    scanner.nextLine(); // Consomme le retour à la ligne restant
                    MenuBesoin menuBesoin = new MenuBesoin();
                    menuBesoin.afficherMenuBesoin();
                    System.out.println("Besoin");
                    // continuer = false;
                    break;
                case 2:
                    System.out.println("Rapport de réunion");
                    // MenuRapportDeReunion menuRapportDeReunion = new MenuRapportDeReunion();
                    // menuRapportDeReunion.afficherMenuRapportDeReunion();
                    // continuer = false;
                    break;
                case 3:
                    System.out.println("Contraintes");
                    break;
                case 4:
                    System.out.println("Quitter");
                    scanner.close();
                    continuer = false;
                    break;

            }
        } while (continuer);
    }
}