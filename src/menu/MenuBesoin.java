package src.menu;

import src.model.Besoin;
import java.util.Scanner;
import src.Enum.EnumBesoin;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
            System.out.println("2. Afficher tous les besoins");
            System.out.println("3. Supprimer un besoin");
            System.out.println("4. Modifier le statut d'un besoin");
            System.out.println("5. Retour au menu principal");
            System.out.println("6. Quitter");
            System.out.println("--------------------------------");
            System.out.println("Entrez votre choix : ");
            System.out.println("--------------------------------");
            choix = scanner.nextInt(); // ← Lire APRÈS avoir affiché le menu
            // scanner.nextLine(); // Consommer le \n
            switch (choix) {
                case 1:
                    scanner.nextLine(); 
                    System.out.println("(Ajouter un besoin) Entrez le libelle du besoin : ");
                    String libelle = scanner.nextLine(); 
                    
                    LocalDate datePrevueAnalyse = null;
                    boolean dateValide = false;
                    
                    while (!dateValide) {
                        System.out.println("(Ajouter un besoin) Entrez la date de prévision (AAAA-MM-JJ) : ");
                        try {
                            datePrevueAnalyse = LocalDate.parse(scanner.nextLine());
                            dateValide = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Format invalide ! Veuillez respecter le format AAAA-MM-JJ.");
                        }
                    }
                
                    // On passe maintenant les DEUX informations
                    new Besoin().ajouterBesoinInteractif(libelle, datePrevueAnalyse);
                    break;
                case 2:
                    System.out.println("Afficher tous les besoins");
                    new Besoin().afficherTousLesBesoins();
                    // continuer = false;
                    scanner.nextLine(); // Consomme le retour à la ligne restant
                    break;
                   
                case 3:
                    System.out.println("Supprimer un besoin");
                    new Besoin().supprimerBesoin(scanner);
                    // continuer = false;
                    break;
                case 4:
                    System.out.println("Modifier le statut d'un besoin");
                    new Besoin().modifierStatutBesoin(scanner);
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