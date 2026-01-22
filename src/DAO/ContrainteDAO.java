package src.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate; // Plus moderne que Date
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import src.model.Contrainte;
import src.Enum.EnumContrainte;


public class ContrainteDAO {
    // on initialise le fichier et le format de date
    private String fichier = "src/csv/contraintes.csv";
    // On définit le format de date attendu dans le CSV (ex: 2023-10-25)
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // méthode pour parser le csv en liste d'objets Contrainte
    public List<Contrainte> csvToArrayList() {
        List<Contrainte> liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] data = ligne.split(",");
                if (data.length < 8) continue; // Sécurité
    
                Contrainte c = new Contrainte();
                c.setId(Integer.parseInt(data[0]));
                c.setLibelle(data[1]);
                c.setEnumContrainte(EnumContrainte.valueOf(data[2]));
                
                // Gestion des dates (Index 3 et 4)
                c.setDateCreation(parseDate(data[3]));
                c.setDateVerification(parseDate(data[4]));
                
                c.setResponsable(data[5]); // Index 5
                c.setEstVerifie(Boolean.parseBoolean(data[6])); // Index 6
                c.setRaisonAnnulation(data[7].equals("null") ? null : data[7]); // Index 7
    
                liste.add(c);
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture : " + e.getMessage());
        }
        return liste;
    }

    // méthode pour enregistrer un objet Contrainte dans le csv
    public void save(Contrainte c) {
        // Mode "true" pour ajouter à la fin du fichier
        try (FileWriter fw = new FileWriter(fichier, true)) {
            // Il faut exactement 8 colonnes pour correspondre au parsing
            String ligne = String.format("%d,%s,%s,%s,%s,%s,%b,%s%n",
                    c.getId(),
                    c.getLibelle(),
                    c.getEnumContrainte(),
                    c.getDateCreation(),
                    c.getDateVerification(),
                    c.getResponsable(),
                    c.isEstVerifie(),
                    c.getRaisonAnnulation());
            fw.write(ligne);
        } catch (IOException e) {
            System.err.println("Erreur d'écriture : " + e.getMessage());
        }
    }

    public boolean delete(int id) {
        List<Contrainte> liste = csvToArrayList();
        // Utilise removeIf pour supprimer l'élément par ID
        boolean removed = liste.removeIf(b -> b.getId() == id);

        if (removed) {
            // Méthode qui réécrit tout le fichier à partir de la nouvelle liste
            saveAllContrainte(liste);
        }
        return removed;
    }

    // méthode pour enregistrer une liste d'objets Contrainte dans le csv
    public void saveAllContrainte(List<Contrainte> liste) {

        // Le false ici est important : il dit à Java d'effacer le fichier
        // avant d'écrire la nouvelle liste.
        try (FileWriter fw = new FileWriter(fichier, false)) {
            for (Contrainte c : liste) {
                String ligne = String.format("%d,%s,%s,%s,%s,%s,%b,%s%n", ///////// 8 colonnes
                        c.getId(),
                        c.getLibelle(),
                        c.getEnumContrainte(),
                        c.getDateCreation(),
                        c.getDateVerification(),
                        c.getResponsable(),
                        c.isEstVerifie(),
                        c.getRaisonAnnulation());
                fw.write(ligne);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la réécriture du fichier : " + e.getMessage());
        }
    }

    public int getNextId() {

        List<Contrainte> liste = csvToArrayList(); // On récupère la liste actuelle

        // Si la liste est vide, on commence à 1
        if (liste.isEmpty()) {
            return 1;
        }
        int maxId = 0;
        for (Contrainte c : liste) {
            if (c.getId() > maxId) {
                maxId = c.getId();
            }
        }
        return maxId + 1;
    }

    public boolean updateStatus(int id, EnumContrainte enumContrainte) {
        List<Contrainte> liste = csvToArrayList();
        boolean trouve = false;
        for (Contrainte contrainte : liste) {
            if (contrainte.getId() == id) {
                contrainte.setEnumContrainte(enumContrainte);
                trouve = true;
                break;
            }
        }
        if (trouve) {
            saveAllContrainte(liste);
        }
        return trouve;
    }

    // Méthode utilitaire pour éviter le crash sur "null" ou vide
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.equalsIgnoreCase("null") || dateStr.isEmpty()) {
            return null; // Retourne un vrai null Java, pas le texte "null"
        }
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            return null; // Si format date invalide, on met null au lieu de crasher
        }
    }

    // Méthode utilitaire pour éviter le crash sur les nombres
    private int parseInteger(String intStr) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            return 0; // Retourne 0 par défaut si ce n'est pas un nombre
        }
    }


}
