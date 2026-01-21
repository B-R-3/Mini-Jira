package src.DAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate; // Plus moderne que Date
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
                if (line.trim().isEmpty()) continue; // Ignore les lignes vides

                String[] data = line.split(",");
                
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
}