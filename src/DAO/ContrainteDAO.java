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

public class ContrainteDAO  {
    //on initialise le fichier et le format de date
    private String fichier = "src/csv/contraintes.csv";
    // On définit le format de date attendu dans le CSV (ex: 2023-10-25)
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    // méthode pour parser le csv en liste d'objets Contrainte
    public List<Contrainte> csvToArrayList() {
        List<Contrainte> listeContraintes = new ArrayList<>();

        // Utilisation du try-with-resources pour la fermeture automatique
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String line;

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
                Contrainte contrainte = new Contrainte();
                contrainte.setId(Integer.parseInt(data[0].trim()));
                contrainte.setLibelle(data[1].trim());
                contrainte.setEnumContrainte(EnumContrainte.valueOf(data[2].trim().toUpperCase()));
                contrainte.setDateCreation(LocalDate.parse(data[3].trim(), formatter));
                contrainte.setRaisonAnnulation(data[4].trim());
                contrainte.setDateVerification(LocalDate.parse(data[5].trim(), formatter));
                contrainte.setResponsable(data[6].trim());
                contrainte.setEstVerifie(Boolean.parseBoolean(data[7].trim()));
                listeContraintes.add(contrainte);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erreur de lecture ou de format : " + e.getMessage());
        }
        return listeContraintes;
    }

}
