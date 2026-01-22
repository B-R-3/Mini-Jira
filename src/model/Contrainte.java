package src.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Paths;
import src.Enum.EnumContrainte;
import src.DAO.ContrainteDAO;
import java.util.List;
import java.util.Scanner;

public class Contrainte {
    private int id;
    private String libelle;
    private EnumContrainte enumContrainte;
    private LocalDate dateCreation;
    private String raisonAnnulation;
    private LocalDate dateVerification;
    private String responsable;
    private boolean estVerifie;

    public Contrainte() {
        this.id = id;
        this.libelle = libelle;
        this.dateCreation = LocalDate.now();
        this.enumContrainte = EnumContrainte.A_PRENDRE_EN_COMPTE;
        this.raisonAnnulation = null;
        this.dateVerification = null;
        this.responsable = null;
        this.estVerifie = false;
    }

    public void afficherToutesLesContraintes() {
        System.out.println("\n=== LISTE DES CONTRAINTES ===");
        ContrainteDAO dao = new ContrainteDAO();
        List<Contrainte> liste = dao.csvToArrayList();

        if (liste.isEmpty()) {
            System.out.println("Aucune contrainte trouvée.");
        } else {
            // En-tête (Note : la largeur totale a été recalculée pour correspondre au
            // printf)
            String ligneSeparation = "├──────┼────────────────────────────┼────────────────┼──────────────┼──────────────┼──────────────┼─────────────┼───────────────────┤";

            System.out.println(
                    "┌──────┬────────────────────────────┬────────────────┬──────────────┬──────────────┬──────────────┬─────────────┬───────────────────┐");
            System.out.println(
                    "│  ID  │          Libellé           │     Statut     │   Création   │ Vérification │ Responsable  │   Vérifié   │ Raison Annulation │");
            System.out.println(ligneSeparation);

            for (int i = 0; i < liste.size(); i++) {
                Contrainte c = liste.get(i);

                System.out.printf(
                        "│ %-4d │ %-26s │ %-14s │ %-12s │ %-12s │ %-12s │ %-11s │ %-17s │%n",
                        c.getId(),
                        c.getLibelle(),
                        c.getEnumContrainte(),
                        c.getDateCreation() != null ? c.getDateCreation().toString() : "N/A",
                        c.getDateVerification() != null ? c.getDateVerification().toString() : "N/A",
                        c.getResponsable() != null ? c.getResponsable() : "N/A",
                        c.isEstVerifie() ? "Oui" : "Non",
                        c.getRaisonAnnulation() != null ? c.getRaisonAnnulation() : "N/A");

                // Ligne de séparation entre les données
                if (i < liste.size() - 1) {
                    System.out.println(ligneSeparation);
                }
            }

            // Pied du tableau
            System.out.println(
                    "└──────┴────────────────────────────┴────────────────┴──────────────┴──────────────┴──────────────┴─────────────┴───────────────────┘");
        }
    }

    public void ajouterContrainteInteractif(String libelle) {
        // Créer un nouvel objet Contrainte
        Contrainte nouveau = new Contrainte();
        // on crée un nouvel objet ContrainteDAO
        ContrainteDAO dao = new ContrainteDAO();
        // on récupère l'id suivant
        nouveau.setId(dao.getNextId());
        // on set les attributs de l'objet Contrainte
        nouveau.setLibelle(libelle);    
        nouveau.setEnumContrainte(EnumContrainte.A_PRENDRE_EN_COMPTE);
        nouveau.setDateCreation(LocalDate.now());
        // Demander au DAO de l'enregistrer
        dao.save(nouveau);

        System.out.println("Contrainte ajoutée avec succès !");
    }

    public void supprimerContrainte(Scanner scanner) { // Passe le scanner en paramètre au lieu de le créer
        System.out.print("Entrez l'ID de la contrainte à supprimer : ");

        // Sécurité si l'utilisateur ne tape pas un nombre
        if (!scanner.hasNextInt()) {
            System.out.println("Erreur : Veuillez entrer un ID valide.");
            scanner.next(); // Consomme l'entrée (la touche ) invalide
            return;
        }
        // on récupère l'id de la contrainte à supprimer
        int id = scanner.nextInt();
        scanner.nextLine(); // Nettoie le buffer après un nextInt()

        // nouvel objet ContrainteDAO
        ContrainteDAO dao = new ContrainteDAO();
        // on supprime la contrainte (on réécrit le fichier mis a jour )
        dao.delete(id);
        System.out.println("Contrainte " + id + " supprimée avec succès !");

    }

    public void modifierStatutContrainte(Scanner scanner) {
        ContrainteDAO dao = new ContrainteDAO();
        // On charge la liste actuelle des besoins
        List<Contrainte> listeContraintes = dao.csvToArrayList();

        System.out.print("Entrez l'ID de la contrainte à modifier : ");
        // ... (ton code de vérification du nombre)
        int id = scanner.nextInt();
        scanner.nextLine();

        // On cherche la contrainte correspondant dans la liste
        Contrainte contrainteAModifier = null;
        for (Contrainte c : listeContraintes) {
            if (c.getId() == id) {
                contrainteAModifier = c;
                break;
            }
        }

        if (contrainteAModifier == null) {
            System.out.println("Erreur : Aucune contrainte trouvée avec l'ID " + id);
            return;
        }

        System.out.print("Entrez le nouveau statut (A_PRENDRE_EN_COMPTE, PRISE_EN_COMPTE_A_VERIFIER, VERIFIEE, ANNULEE) : ");
        String saisie = scanner.nextLine().toUpperCase().trim();

        switch (saisie) {
            case "PRISE_EN_COMPTE_A_VERIFIER":
                contrainteAModifier.setEnumContrainte(EnumContrainte.PRISE_EN_COMPTE_A_VERIFIER);
                break;

            case "VERIFIEE":
                System.out.print("Date de vérification (AAAA-MM-JJ) : ");
                contrainteAModifier.setDateVerification(LocalDate.parse(scanner.nextLine()));
                System.out.print("Responsable : ");
                contrainteAModifier.setResponsable(scanner.nextLine());
                contrainteAModifier.setEstVerifie(true);
                contrainteAModifier.setEnumContrainte(EnumContrainte.VERIFIEE);
                break;

            case "ANNULEE":
                System.out.print("Raison de l'annulation : ");
                contrainteAModifier.setRaisonAnnulation(scanner.nextLine());
                contrainteAModifier.setEnumContrainte(EnumContrainte.ANNULEE);
                break;
                

        }
        // on enregistre la contrainte modifiée
            dao.saveAllContrainte(listeContraintes);
        System.out.println("Contrainte mis à jour avec succès !");
    }
    // getters et setters
    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public EnumContrainte getEnumContrainte() {
        return enumContrainte;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public String getRaisonAnnulation() {
        return raisonAnnulation;
    }

    public LocalDate getDateVerification() {
        return dateVerification;
    }

    public String getResponsable() {
        return responsable;
    }

    public boolean isEstVerifie() {
        return estVerifie;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setEnumContrainte(EnumContrainte enumContrainte) {
        this.enumContrainte = enumContrainte;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setRaisonAnnulation(String raisonAnnulation) {
        this.raisonAnnulation = raisonAnnulation;
    }

    public void setDateVerification(LocalDate dateVerification) {
        this.dateVerification = dateVerification;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public void setEstVerifie(boolean estVerifie) {
        this.estVerifie = estVerifie;
    }

}
