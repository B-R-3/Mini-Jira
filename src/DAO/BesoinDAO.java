package src.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate; // Plus moderne que Date
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import src.model.Besoin;
import src.Enum.EnumBesoin;

public class BesoinDAO {
    private String fichier = "src/csv/besoins.csv";
    // On définit le format de date attendu dans le CSV (ex: 2023-10-25)
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Besoin> csvToArrayList() {
        List<Besoin> listeBesoins = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;

                String[] data = line.split(",");

                // --- SÉCURITÉ : Vérification de l'ID ---
                if (!data[0].trim().matches("\\d+"))
                    continue;

                Besoin besoin = new Besoin();

                try {
                    besoin.setId(Integer.parseInt(data[0].trim()));
                    besoin.setLibelle(data[1].trim());

                    // Enum sécurisé
                    try {
                        besoin.setEnumBesoin(EnumBesoin.valueOf(data[2].trim().toUpperCase()));
                    } catch (Exception e) {
                        besoin.setEnumBesoin(EnumBesoin.A_ANALYSER);
                    }

                    // Dates sécurisées (grâce à ta méthode parseDate)
                    besoin.setDateCreation(parseDate(data[3].trim()));
                    besoin.setDatePrevueAnalyse(parseDate(data[4].trim()));
                    besoin.setDateDebut(parseDate(data[5].trim()));
                    besoin.setDateFin(parseDate(data[6].trim()));

                    // Nombres sécurisés (grâce à ta méthode parseInteger)
                    besoin.setCharge(parseInteger(data[7].trim()));
                    besoin.setProgression(parseInteger(data[8].trim()));
                    besoin.setEstTermine(Boolean.parseBoolean(data[9].trim()));
                    besoin.setRaisonAnnulation(data[10].trim());

                    listeBesoins.add(besoin);

                } catch (Exception e) {
                    System.err.println("Erreur de parsing sur la ligne : " + line + " -> " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier CSV : " + e.getMessage());
        }
        return listeBesoins;
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

    public void save(Besoin b) {
        // Mode "true" pour ajouter à la fin du fichier
        try (FileWriter fw = new FileWriter(fichier, true)) {
            // Il faut exactement 11 colonnes pour correspondre au parsing
            String ligne = String.format("%d,%s,%s,%s,%s,%s,%s,%d,%d,%b,%s%n",
                    b.getId(),
                    b.getLibelle(),
                    b.getEnumBesoin(),
                    b.getDateCreation(),
                    b.getDatePrevueAnalyse(),
                    b.getDateDebut(),
                    b.getDateFin(),
                    b.getCharge(),
                    b.getProgression(),
                    b.isEstTermine(),
                    b.getRaisonAnnulation());
            fw.write(ligne);
        } catch (IOException e) {
            System.err.println("Erreur d'écriture : " + e.getMessage());
        }
    }

    public boolean delete(int id) {
        List<Besoin> liste = csvToArrayList();
        // Utilise removeIf pour supprimer l'élément par ID
        boolean removed = liste.removeIf(b -> b.getId() == id);

        if (removed) {
            // Méthode qui réécrit tout le fichier à partir de la nouvelle liste
            saveAllBesoin(liste);
        }
        return removed;
    }

    public void saveAllBesoin(List<Besoin> liste) {
        // Le false ici est important : il dit à Java d'effacer le fichier
        // avant d'écrire la nouvelle liste.
        try (FileWriter fw = new FileWriter(fichier, false)) {
            for (Besoin b : liste) {
                String ligne = String.format("%d,%s,%s,%s,%s,%s,%s,%d,%d,%b,%s%n",
                        b.getId(),
                        b.getLibelle(),
                        b.getEnumBesoin(),
                        b.getDateCreation(),
                        b.getDatePrevueAnalyse(),
                        b.getDateDebut(),
                        b.getDateFin(),
                        b.getCharge(),
                        b.getProgression(),
                        b.isEstTermine(),
                        b.getRaisonAnnulation());
                fw.write(ligne);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la réécriture du fichier : " + e.getMessage());
        }
    }

    public int getNextId() {
        List<Besoin> liste = csvToArrayList(); // On récupère la liste actuelle

        // Si la liste est vide, on commence à 1
        if (liste.isEmpty()) {
            return 1;
        }
        int maxId = 0;
        for (Besoin b : liste) {
            if (b.getId() > maxId) {
                maxId = b.getId();
            }
        }
        return maxId + 1;
    }

    public boolean updateStatus(int id, EnumBesoin enumBesoin) {
        List<Besoin> liste = csvToArrayList();
        boolean trouve = false;
        for (Besoin besoin : liste) {
            if (besoin.getId() == id) {
                besoin.setEnumBesoin(enumBesoin);
                trouve = true;
                break;
            }
        }
        if (trouve) {
            saveAllBesoin(liste);
        }
        return trouve;
    }
}