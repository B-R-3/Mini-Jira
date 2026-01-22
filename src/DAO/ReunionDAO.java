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


public class ReunionDAO {
    private final String fichierCsv = "reunions.csv";
    private final String dossierTextes = "comptes_rendus/";

    public ReunionDAO() {
        // Créer le dossier s'il n'existe pas
        new File(dossierTextes).mkdirs();
    }


    // public List<Reunion> csvToArrayList() {
    //     List<Reunion> liste = new ArrayList<>();
        
    //     try (BufferedReader br = new BufferedReader(new FileReader(fichierCsv))) {
    //         String ligne;
    //         while ((ligne = br.readLine()) != null) {
    //             String[] data = ligne.split(";");
    //             if (data.length < 5) continue;
    
    //             int id = Integer.parseInt(data[0]);
    //             String auteur = data[1];
    //             String participants = data[2];
    //             LocalDate date = LocalDate.parse(data[3]);
                
    //             // On transforme la chaîne de caractères en List<ActionItem>
    //             List<ActionItem> items = serialiserActionItems(data[4]);
    
    //             Reunion r = new Reunion();
    //             r.setId(id);
    //             r.setNomAuteur(auteur);
    //             r.setParticipants(participants);
    //             r.setDateReunion(date);
    //             r.setActionItems(items);
    //             liste.add(r);
    //         }
    //     } catch (IOException e) {
    //         System.err.println("Erreur de lecture : " + e.getMessage());
    //     }
    //     return liste;
    // }

    // // SAUVEGARDE 1 : Les données dans le CSV
    // public void save(Reunion r) {
    //     try (FileWriter fw = new FileWriter(fichierCsv, true)) {
    //         // On transforme la liste d'ActionItems en une seule chaîne pour le CSV (séparée par des |)
    //         String itemsSerialises = serialiserActionItems(r.getActionItems());
            
    //         String ligne = String.format("%d;%s;%s;%s;%s%n",
    //                 r.getId(),
    //                 r.getNomAuteur(),
    //                 r.getParticipants(),
    //                 r.getDateReunion(),
    //                 itemsSerialises);
    //         fw.write(ligne);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

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


}