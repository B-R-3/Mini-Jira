package src.model;

import java.util.List;
import java.time.LocalDate;
import java.util.Scanner;
import src.DAO.ReunionDAO;
import java.util.ArrayList;

public class Reunion {

    private int id;
    private String NomAuteur;
    private String Participants;
    private List<ActionItem> actionItems;
    private LocalDate dateReunion;
    private String cheminFichierTxt;

    public Reunion() {
        this.id = id;
        this.NomAuteur = NomAuteur;
        this.Participants = Participants;
        this.actionItems = actionItems;
        this.dateReunion = null;
        this.cheminFichierTxt = cheminFichierTxt;
    }

    public void afficherToutesLesReunions() {
        System.out.println("=== LISTE DES REUNIONS ===");
        ReunionDAO dao = new ReunionDAO();
        List<Reunion> liste = dao.csvToArrayList();

        for (int i = 0; i < liste.size(); i++) {
            Reunion r = liste.get(i);

            System.out.printf(
                    "│ %-4d │ %-26s │ %-14s │ %-12s │ %-12s │ %-12s │%n",
                    r.getId(),
                    r.getNomAuteur(),
                    r.getParticipants(),
                    r.getDateReunion() != null ? r.getDateReunion().toString() : "N/A",
                    r.getActionItems().toString());

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

    public void creerReunionInteractif(Scanner scanner) {
        ReunionDAO dao = new ReunionDAO();
        this.id = dao.getNextId();
        // Initialisation de la liste pour éviter les erreurs de pointeur null
        this.actionItems = new ArrayList<>();
        System.out.print("Nom de l'auteur : ");
        this.NomAuteur = scanner.nextLine();

        System.out.print("Participants : ");
        this.Participants = scanner.nextLine();

        // Date automatique au jour J
        this.dateReunion = LocalDate.now();
        boolean success = false;
        String reponse;
        do {
            System.out.println("\n--- Ajout d'une action (Tableau Quoi/Qui/Quand) ---");
            System.out.print("Quoi : ");
            String quoi = scanner.nextLine();
            System.out.print("Qui : ");
            String qui = scanner.nextLine();
            System.out.print("Quand : ");
            LocalDate quand = LocalDate.parse(scanner.nextLine());

            // CORRECTION : On crée un nouvel objet ActionItem avant de l'ajouter
            this.actionItems.add(new ActionItem(quoi, qui, quand));

            System.out.print("Ajouter une autre action ? (O/N) : ");
            reponse = scanner.nextLine().toUpperCase().trim();
        } while (reponse.equals("O"));

        // ÉTAPE FINALE : On génère le texte et on l'enregistre via le DAO
        String compteRendu = preparerTexteRapport();
        dao.saveCompteRendu(this.id, compteRendu);
        success = true;

        System.out.println("\nFélicitations ! Le fichier 'reunion_" + this.id + ".txt' a été généré.");
        if(success) {
            dao.save(this);
        }
        else {
            System.out.println("Erreur lors de l'enregistrement de la réunion.");
        }
    }

    // Cette méthode crée la mise en page du fichier .txt
    private String preparerTexteRapport() {
        StringBuilder sb = new StringBuilder();
        sb.append("RAPPORT DE REUNION #").append(this.id).append("\n");
        sb.append("---------------------------\n");
        sb.append("AUTEUR : ").append(this.NomAuteur).append("\n");
        sb.append("DATE   : ").append(this.dateReunion).append("\n");
        sb.append("PARTICIPANTS : ").append(this.Participants).append("\n\n");
        sb.append("TABLEAU DES ACTIONS :\n");
        sb.append(String.format("%-20s | %-15s | %-15s\n", "QUOI", "QUI", "QUAND"));
        sb.append("-------------------------------------------------------\n");

        for (ActionItem item : actionItems) {
            sb.append(String.format("%-20s | %-15s | %-15s\n",
                    item.getQuoi(), item.getQui(), item.getQuand()));
        }

        return sb.toString();
    }

    // getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCheminFichierTxt() {
        return cheminFichierTxt;
    }

    public void setCheminFichierTxt(String cheminFichierTxt) {
        this.cheminFichierTxt = cheminFichierTxt;
    }

    public String getNomAuteur() {
        return NomAuteur;
    }

    public String getParticipants() {
        return Participants;
    }

    public List<ActionItem> getActionItems() {
        return actionItems;
    }

    public LocalDate getDateReunion() {
        return dateReunion;
    }

    public void setDateReunion(LocalDate dateReunion) {
        this.dateReunion = dateReunion;
    }

    public void setNomAuteur(String NomAuteur) {
        this.NomAuteur = NomAuteur;
    }

    public void setParticipants(String Participants) {
        this.Participants = Participants;
    }

    public void setActionItems(List<ActionItem> actionItems) {
        this.actionItems = actionItems;
    }

}
