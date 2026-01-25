package src.menu;

import java.util.Scanner;
import src.model.Reunion;
import src.DAO.ReunionDAO;
import java.time.LocalDate;

public class MenuReunion {

    public void afficherMenuReunion() {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        int choix = 0;
        do {
            System.out.println("--------------------------------");
            System.out.println("Bienvenue dans le menu des reunions");
            System.out.println("--------------------------------");
            System.out.println("1. Créer une reunion");
            System.out.println("2. Afficher toutes les reunions");
            System.out.println("3. Supprimer une reunion");
            System.out.println("4. Retour au menu principal");
            System.out.println("5. Quitter");
            System.out.println("--------------------------------");
            System.out.println("Entrez votre choix : ");
            System.out.println("--------------------------------");
            choix = scanner.nextInt(); // ← Lire APRÈS avoir affiché le menu
            // scanner.nextLine(); // Consommer le \n
            switch (choix) {
                case 1:
                        scanner.nextLine(); 
                        Reunion nouvelleReunion = new Reunion();
                        nouvelleReunion.creerReunionInteractif(scanner);
                        break;
                case 2:
                    Reunion reunion = new Reunion();
                    reunion.afficherToutesLesReunions();
                    break;
                case 3:

                    break;
                case 4:

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
        }while(continuer);
}}