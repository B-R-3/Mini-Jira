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

        // Utilisation du try-with-resources pour la fermeture automatique
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String line;
            // On saute éventuellement l'en-tête si ton CSV en a un
            // br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;

                String[] data = line.split(",");

                // VERIFICATION DE SECURITE
                // On vérifie si le premier élément est bien un chiffre
                if (!data[0].trim().matches("\\d+")) {
                    System.out.println("Ligne ignorée (en-tête ou format invalide) : " + line);
                    continue;
                }
                // On crée l'objet
                Besoin besoin = new Besoin();
                besoin.setId(Integer.parseInt(data[0].trim()));
                besoin.setLibelle(data[1].trim());

                // Gestion sécurisée de l'Enum (attention à la casse !)
                besoin.setEnumBesoin(EnumBesoin.valueOf(data[2].trim().toUpperCase()));

                // Parsing de date moderne
                besoin.setDateCreation(LocalDate.parse(data[3].trim(), formatter));

                besoin.setResponsable(data[4].trim());
                besoin.setProgression(Integer.parseInt(data[5].trim()));
                besoin.setEstTermine(Boolean.parseBoolean(data[6].trim()));

                listeBesoins.add(besoin);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erreur de lecture ou de format : " + e.getMessage());
        }
        return listeBesoins;
    }

    public void save(Besoin b) {
        // (ajouter à la fin du fichier)
        try (FileWriter fw = new FileWriter(fichier, true)) {
            String ligne = String.format("%d,%s,%s,%s,%s,%d,%b%n",
                    b.getId(),
                    b.getLibelle(),
                    b.getEnumBesoin(),
                    b.getDateCreation(),
                    b.getResponsable(),
                    b.getProgression(),
                    b.isEstTermine());
            fw.write(ligne);
        } catch (IOException e) {
            System.err.println("Erreur d'écriture : " + e.getMessage());
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
}