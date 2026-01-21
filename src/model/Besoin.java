package src.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import src.Enum.EnumBesoin;
import java.util.List;


public class Besoin {
    private int id;
    private String libelle;
    private EnumBesoin enumBesoin; // statut du besoin
    private Date dateCreation;
    private String responsable;
    private int progression;
    private boolean estTermine;
    

    public Besoin(int id, String libelle, EnumBesoin enumBesoin) {
        this.id = id;
        this.libelle = libelle;
        this.enumBesoin = enumBesoin;
    }



    //parsing du csv

    // Méthode essentieles pour le menu interactif
    public void ajouterBesoin() {
        String fichier = "src/csv/besoins.csv";
        try {
            FileWriter writer = new FileWriter(fichier, true);
            writer.write(id + "," + libelle + "," + enumBesoin + "," + dateCreation + ";" + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ajout du besoin");
        }
        System.out.println("Besoin ajouté avec succès");
    }

    // public void supprimerBesoin() {
    // System.out.println("Supprimer un besoin");
    // }

    public void afficherTousLesBesoins() {
    System.out.println("Afficher tous les besoins");
    String fichier = "src/csv/besoins.csv";
    try {
        String contenu = new String(Files.readAllBytes(Paths.get("src/csv/besoins.csv")));
        String[] lignes = contenu.split(";"); // Séparer par ;
        
        for (String ligne : lignes) {
            if (!ligne.trim().isEmpty()) {
                String[] champs = ligne.split(","); // Séparer par , uniquement
                int id = Integer.parseInt(champs[0]);
                String libelle = champs[1];
                EnumBesoin enumBesoin = EnumBesoin.valueOf(champs[2]);
                String dateCreation = champs[3];
                String responsable = champs[4];
                int progression = Integer.parseInt(champs[5]);
                boolean estTermine = Boolean.parseBoolean(champs[6]);
                System.out.println(id + " " + libelle + " " + enumBesoin + " " + dateCreation + " " + responsable + " " + progression + " " + estTermine);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    System.out.println("Besoins affichés avec succès");
    }

    public int getLastId() {
        String fichier = "src/csv/besoins.csv";
        try {
            // Vérifier si le fichier existe
            if (!Files.exists(Paths.get(fichier))) {
                return 0; // Fichier n'existe pas, commencer à 0
            }
            String contenu = new String(Files.readAllBytes(Paths.get(fichier)));
            // Vérifier si le fichier est vide
            if (contenu.trim().isEmpty()) {
                return 0;
            }
            String[] lignes = contenu.split(";");
            
            // Trouver la dernière ligne non vide
            for (int i = lignes.length - 1; i >= 0; i--) {
                if (!lignes[i].trim().isEmpty()) {
                    int lastId = Integer.parseInt(lignes[i].split(",")[0].trim());
                    return lastId;
                }
            }
            return 0; // Aucune ligne valide trouvée
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erreur lors de la lecture du fichier");
            return 0;
        }
    }
    

    // public ArrayList<Besoin> csvToArrayList() {
    //     String fichier = "src/csv/besoins.csv";
    //     ArrayList<Besoin> besoins = new ArrayList<>();
    //     try {
    //         FileReader reader = new FileReader(fichier);
    //         BufferedReader bufferedReader = new BufferedReader(reader);
    //         String line;
    //         while ((line = bufferedReader.readLine()) != null) {
    //             String[] data = line.split(",");
    //             Besoin besoin = new Besoin(data[0], data[1], new Date(data[2]));
    //             besoins.add(besoin);
    //         }
    //         bufferedReader.close();
    //     } catch (IOException e) {
    //         System.out.println("Erreur lors de la lecture du fichier");
    //     }
    //     return besoins;
    // }

    // public void arrayListToCsv() {
    //     String fichier = "src/csv/besoins.csv";
    //     ArrayList<Besoin> besoins = new ArrayList<>();
    //     try {
    //         FileWriter writer = new FileWriter(fichier);
    //         for (Besoin besoin : besoins) {
    //             writer.write(besoin.getLibelle() + "," + besoin.getType() + "," + besoin.getDateCreation() + "\n");
    //         }
        
    //     } catch (IOException e) {
    //         System.out.println("Erreur lors de l'écriture du fichier");
    //     }
    //     System.out.println("Besoins écrits dans le fichier avec succès");
    // }

    // Méthodes getters et setters7
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

    public Date getDateCreation() {
        return dateCreation;
    }
    public String getResponsable() {
        return responsable;
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

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    public void setProgression(int progression) {
        this.progression = progression;
    }
    public void setEstTermine(boolean estTermine) {
        this.estTermine = estTermine;
    }
}
