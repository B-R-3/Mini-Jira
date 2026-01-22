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
                    "┌──────┬────────────────────────────┬────────────────┬──────────────┬─────────────┬──────────────┬──────────────┬──────────────┬────────┬──────────┐");
            System.out.println(
                    "│  ID  │          Libellé           │     Statut     │   Création   │ Progression │ Prévue Anal. │ Date Début   │  Date Fin    │ Charge │  Terminé │");
            System.out.println(
                    "├──────┼────────────────────────────┼────────────────┼──────────────┼─────────────┼──────────────┼──────────────┼──────────────┼────────┼──────────┤");

            for (Besoin b : liste) {
                System.out.printf(
                        "│ %-4d │ %-26s │ %-14s │ %-12s │    %3d%%     │ %-12s │ %-12s │ %-12s │ %6d │ %-8s │%n",
                        b.getId(),
                        b.getLibelle(),
                        b.getEnumBesoin(),
                        b.getDateCreation() != null ? b.getDateCreation().toString() : "N/A",
                        b.getProgression(),
                        b.getDatePrevueAnalyse() != null ? b.getDatePrevueAnalyse().toString() : "N/A",
                        b.getDateDebut() != null ? b.getDateDebut().toString() : "N/A",
                        b.getDateFin() != null ? b.getDateFin().toString() : "N/A",
                        b.getCharge(),
                        b.isEstTermine() ? "Oui" : "Non");
            }
            // Pied du tableau (déplacé APRES la boucle)
            System.out.println(
                    "└──────┴────────────────────────────┴────────────────┴──────────────┴─────────────┴──────────────┴──────────────┴──────────────┴────────┴──────────┘");
        }
    }

    // Methode pour le menu interactif
    public void ajouterBesoinInteractif(String libelle) {
        // 1. Créer un nouvel objet Besoin
        Besoin nouveau = new Besoin();
        nouveau.setId(new BesoinDAO().getNextId());
        nouveau.setLibelle(libelle);
        nouveau.setEnumBesoin(EnumBesoin.A_ANALYSER);
        nouveau.setDateCreation(LocalDate.now());
        // 2. Demander au DAO de l'enregistrer
        BesoinDAO dao = new BesoinDAO();
        dao.save(nouveau);

        System.out.println("Besoin ajouté avec succès !");
    }

    public void supprimerBesoin(Scanner scanner) { // Passe le scanner en paramètre au lieu de le créer
        System.out.print("Entrez l'ID du besoin à supprimer : ");

        // Sécurité si l'utilisateur ne tape pas un nombre
        if (!scanner.hasNextInt()) {
            System.out.println("Erreur : Veuillez entrer un ID valide.");
            scanner.next(); // Consomme l'entrée invalide
            return;
        }

        int id = scanner.nextInt();
        scanner.nextLine(); // Nettoie le buffer après un nextInt()

        BesoinDAO dao = new BesoinDAO();

        // Idéalement, delete devrait retourner un boolean pour savoir si l'ID existait
        dao.delete(id);
        System.out.println("Besoin " + id + " supprimé avec succès !");

    }

    // Méthode pour modifier le statut d'un besoin
    public void modifierStatutBesoin(Scanner scanner) {
        System.out.print("Entrez l'ID du besoin à modifier : ");

        // Sécurité si l'utilisateur ne tape pas un nombre
        if (!scanner.hasNextInt()) {
            System.out.println("Erreur : Veuillez entrer un ID valide.");
            scanner.next(); // Consomme l'entrée invalide
            return;
        }
        System.out.print("Entrez le nouveau statut du besoin : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Nettoie le buffer après un nextInt()

        BesoinDAO dao = new BesoinDAO();

        // Idéalement, delete devrait retourner un boolean pour savoir si l'ID existait
        dao.updateStatus(id, EnumBesoin.valueOf(scanner.nextLine().toUpperCase()));
        System.out.println("Statut du besoin " + id + " modifié avec succès !");
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
}
