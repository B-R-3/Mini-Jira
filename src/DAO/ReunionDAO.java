package src.DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.FileWriter;
import src.model.Reunion;
import src.model.ActionItem;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReunionDAO {
    private final String fichierCsv = "src/csv/reunions.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String dossierTextes = "comptes_rendus/";

    public ReunionDAO() {
        // Créer le dossier s'il n'existe pas
        new File(dossierTextes).mkdirs();
    }

    // Parsage -----------------------------
    public List<Reunion> csvToArrayList() {
        List<Reunion> listeReunions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichierCsv))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] data = ligne.split(";");

                int id = Integer.parseInt(data[0]);
                String auteur = data[1];
                String participants = data[2];
                LocalDate date = LocalDate.parse(data[3]);

                // On transforme la chaîne de caractères en List<ActionItem>
                List<ActionItem> items = parseActionItems(data[4]);

                Reunion r = new Reunion();
                r.setId(id);
                r.setNomAuteur(auteur);
                r.setParticipants(participants);
                r.setDateReunion(date);
                r.setActionItems(items);
                listeReunions.add(r);
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture : " + e.getMessage());
        }
        return listeReunions;
    }

    private List<ActionItem> parseActionItems(String data) {
        List<ActionItem> items = new ArrayList<>();
        if (data == null || data.isEmpty() || data.equals("NONE"))
            return items;

        // Utilisation de \\| car le pipe est un métacaractère
        String[] itemsData = data.split("\\|");
        for (String itemData : itemsData) {
            if (itemData.isEmpty())
                continue;

            // On utilise le même séparateur que dans serializeActionItems (le
            // point-virgule)
            String[] parts = itemData.split(";");
            if (parts.length >= 3) {
                // Attention : assure-toi que ActionItem accepte String ou LocalDate ici
                items.add(new ActionItem(parts[0], parts[1], LocalDate.parse(parts[2])));
            }
        }
        return items;
    }
    // fin de parsage --------------------------------------

    // SAUVEGARDE 1 : Les données dans le CSV
    public void save(Reunion r) {
        // Ne pas recréer de DAO ici, utilise 'this' ou calcule l'ID avant
        try (FileWriter fw = new FileWriter(fichierCsv, true)) {
            String ligne = String.format("%d;%s;%s;%s;%s%n",
                    r.getId(), // Utilise l'ID déjà défini dans l'objet
                    r.getNomAuteur(),
                    r.getParticipants(),
                    r.getDateReunion(),
                    serializeActionItems(r.getActionItems()));
            fw.write(ligne);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serializeActionItems(List<ActionItem> items) {
        if (items == null || items.isEmpty())
            return "NONE";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            ActionItem item = items.get(i);
            sb.append(item.getQuoi()).append(";")
                    .append(item.getQui()).append(";")
                    .append(item.getQuand());

            if (i < items.size() - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    // SAUVEGARDE 2 : Le compte-rendu dans un .txt
    public void saveCompteRendu(int id, String contenu) {
        // On s'assure que le dossier existe
        new File(dossierTextes).mkdirs();

        File file = new File(dossierTextes + "reunion_" + id + ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(contenu);
        } catch (IOException e) {
            System.out.println("Erreur : Impossible de créer le fichier .txt");
        }
    }

    // méthode pour récupérer l'id suivant
    public int getNextId() {
        List<Reunion> liste = csvToArrayList(); // On récupère la liste actuelle

        // Si la liste est vide, on commence à 1
        if (liste.isEmpty()) {
            return 1;
        }
        int maxId = 0;
        for (Reunion r : liste) {
            if (r.getId() > maxId) {
                maxId = r.getId();
            }
        }
        return maxId + 1;
    }

}