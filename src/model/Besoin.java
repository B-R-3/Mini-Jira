package src.model;

import src.Enum.EnumBesoin;
import java.util.List;
import src.DAO.BesoinDAO;
import java.time.LocalDate;
import java.util.Scanner;

public class Besoin {
    // Attributs classique shit
    private int id;
    private String libelle;
    private EnumBesoin enumBesoin;
    private LocalDate dateCreation;
    private int progression;
    // Attributs pour le satut a analyser
    private LocalDate datePrevueAnalyse;
    // Attributs pour le statut analysee
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int charge;
    // Attributs pour le statut annulé
    private String raisonAnnulation;
    // Attributs pour le statut terminé
    private boolean estTermine;

    // Constructeur vide (pour le DAO)
    public Besoin() {
        this.dateCreation = LocalDate.now();
        this.enumBesoin = EnumBesoin.A_ANALYSER;
        this.datePrevueAnalyse = null;
        this.dateDebut = null;
        this.dateFin = null;
        this.charge = 0;
        this.estTermine = false;
        this.progression = 0;
        this.raisonAnnulation = null;
    }

    public void afficherTousLesBesoins() {
        System.out.println("=== LISTE DES BESOINS ===");
        BesoinDAO dao = new BesoinDAO();
        List<Besoin> liste = dao.csvToArrayList();

        if (liste.isEmpty()) {
            System.out.println("Aucun besoin trouvé.");
        } else {
            // En-tête
            System.out.println(
                    "┌──────┬────────────────────────────┬────────────────┬──────────────┬─────────────┬──────────────┬──────────────┬──────────────┬────────┬──────────┬──────────────┐");
            System.out.println(
                    "│  ID  │          Libellé           │     Statut     │   Création   │ Progression │ Prévue Anal. │ Date Début   │  Date Fin    │ Charge │  Terminé │ Raison Annulation │");
            System.out.println(
                    "├──────┼────────────────────────────┼────────────────┼──────────────┼─────────────┼──────────────┼──────────────┼──────────────┼────────┼──────────┼──────────────┤");

            for (int i = 0; i < liste.size(); i++) {
                Besoin b = liste.get(i);

                System.out.printf(
                        "│ %-4d │ %-26s │ %-14s │ %-12s │    %3d%%     │ %-12s │ %-12s │ %-12s │ %6d │ %-8s │ %-16s │%n",
                        b.getId(),
                        b.getLibelle(),
                        b.getEnumBesoin(),
                        b.getDateCreation() != null ? b.getDateCreation().toString() : "N/A",
                        b.getProgression(),
                        b.getDatePrevueAnalyse() != null ? b.getDatePrevueAnalyse().toString() : "N/A",
                        b.getDateDebut() != null ? b.getDateDebut().toString() : "N/A",
                        b.getDateFin() != null ? b.getDateFin().toString() : "N/A",
                        b.getCharge(),
                        b.isEstTermine() ? "Oui" : "Non",
                        b.getRaisonAnnulation() != null ? b.getRaisonAnnulation() : "N/A");

                // Si ce n'est pas le dernier élément, on affiche une ligne de séparation
                if (i < liste.size() - 1) {
                    System.out.println(
                            "├──────┼────────────────────────────┼────────────────┼──────────────┼─────────────┼──────────────┼──────────────┼──────────────┼────────┼──────────┼──────────────┤");
                }
            }

            // Pied du tableau
            System.out.println(
                    "└──────┴────────────────────────────┴────────────────┴──────────────┴─────────────┴──────────────┴──────────────┴──────────────┴────────┴──────────┴──────────────┘");
        }
    }

    // Methodes pour le menu interactif
    public void ajouterBesoinInteractif(String libelle, LocalDate datePrevueAnalyse) {
        // Créer un nouvel objet Besoin
        Besoin nouveau = new Besoin();
        // on crée un nouvel objet BesoinDAO
        BesoinDAO dao = new BesoinDAO();
        // on récupère l'id suivant
        nouveau.setId(dao.getNextId());
        // on set les attributs de l'objet Besoin
        nouveau.setLibelle(libelle);
        nouveau.setEnumBesoin(EnumBesoin.A_ANALYSER);
        nouveau.setDateCreation(LocalDate.now());
        nouveau.setDatePrevueAnalyse(datePrevueAnalyse);
        // Demander au DAO de l'enregistrer
        dao.save(nouveau);

        System.out.println("Besoin ajouté avec succès !");
    }

    public void supprimerBesoin(Scanner scanner) { // Passe le scanner en paramètre au lieu de le créer
        System.out.print("Entrez l'ID du besoin à supprimer : ");

        // Sécurité si l'utilisateur ne tape pas un nombre
        if (!scanner.hasNextInt()) {
            System.out.println("Erreur : Veuillez entrer un ID valide.");
            scanner.next(); // Consomme l'entrée (la touche ) invalide
            return;
        }
        // on récupère l'id du besoin à supprimer
        int id = scanner.nextInt();
        scanner.nextLine(); // Nettoie le buffer après un nextInt()

        // nouvel objet BesoinDAO
        BesoinDAO dao = new BesoinDAO();
        // on supprime le besoin (on réécrit le fichier mis a jour )
        dao.delete(id);
        System.out.println("Besoin " + id + " supprimé avec succès !");

    }

    // Méthode pour modifier le statut d'un besoin
    public void modifierStatutBesoin(Scanner scanner) {
        BesoinDAO dao = new BesoinDAO();
        // On charge la liste actuelle des besoins
        List<Besoin> listeBesoins = dao.csvToArrayList();

        System.out.print("Entrez l'ID du besoin à modifier : ");
        // ... (ton code de vérification du nombre)
        int id = scanner.nextInt();
        scanner.nextLine();

        // On cherche le besoin correspondant dans la liste
        Besoin besoinAModifier = null;
        for (Besoin b : listeBesoins) {
            if (b.getId() == id) {
                besoinAModifier = b;
                break;
            }
        }

        if (besoinAModifier == null) {
            System.out.println("Erreur : Aucun besoin trouvé avec l'ID " + id);
            return;
        }

        System.out.print("Entrez le nouveau statut (A_ANALYSER, ANALYSEE, ANNULEE, TERMINEE) : ");
        String saisie = scanner.nextLine().toUpperCase().trim();

        switch (saisie) {
            case "ANALYSEE":
                System.out.print("Date début (AAAA-MM-JJ) : ");
                besoinAModifier.setDateDebut(LocalDate.parse(scanner.nextLine()));

                System.out.print("Date fin (AAAA-MM-JJ) : ");
                besoinAModifier.setDateFin(LocalDate.parse(scanner.nextLine()));

                System.out.print("Charge (heures) : ");
                besoinAModifier.setCharge(scanner.nextInt());
                scanner.nextLine();

                besoinAModifier.setEnumBesoin(EnumBesoin.ANALYSEE);
                break;

            case "ANNULEE":
                System.out.print("Raison de l'annulation : ");
                besoinAModifier.setRaisonAnnulation(scanner.nextLine());
                besoinAModifier.setEnumBesoin(EnumBesoin.ANNULEE);
                break;

            case "TERMINEE":
                String reponse;
                do {
                    System.out.print("Votre besoin est terminé ? (O/N) : ");
                    reponse = scanner.nextLine().trim().toUpperCase(); // On lit UNE SEULE FOIS

                    if (reponse.equals("O")) {
                        besoinAModifier.setEstTermine(true);
                        besoinAModifier.setEnumBesoin(EnumBesoin.TERMINEE); // N'oublie pas de changer le statut aussi !
                    } else if (reponse.equals("N")) {
                        besoinAModifier.setEstTermine(false);
                        // Optionnel : décider si le statut reste TERMINEE ou autre
                    } else {
                        System.out.println("Saisie invalide, veuillez taper O ou N.");
                    }
                } while (!reponse.equals("O") && !reponse.equals("N"));

                if (besoinAModifier.isEstTermine() == true) {
                    System.out.println("Quelle est la progression du besoin ? (0-100)");
                    besoinAModifier.setProgression(scanner.nextInt());
                    scanner.nextLine();
                } 
                break;

        }
        // on enregistre le besoin modifié
        dao.saveAllBesoin(listeBesoins);
        System.out.println("Besoin mis à jour avec succès !");
    }

    // Méthodes getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public EnumBesoin getEnumBesoin() {
        return enumBesoin;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public LocalDate getDatePrevueAnalyse() {
        return datePrevueAnalyse;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDatePrevueAnalyse(LocalDate datePrevueAnalyse) {
        this.datePrevueAnalyse = datePrevueAnalyse;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getProgression() {
        return progression;
    }

    public boolean isEstTermine() {
        return estTermine;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setEnumBesoin(EnumBesoin enumBesoin) {
        this.enumBesoin = enumBesoin;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setProgression(int progression) {
        this.progression = progression;
    }

    public void setEstTermine(boolean estTermine) {
        this.estTermine = estTermine;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public String getRaisonAnnulation() {
        return raisonAnnulation;
    }

    public void setRaisonAnnulation(String raisonAnnulation) {
        this.raisonAnnulation = raisonAnnulation;
    }
}