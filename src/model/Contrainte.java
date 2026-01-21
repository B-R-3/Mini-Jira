package src.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import src.Enum.EnumContrainte;


public class Contrainte {

    private String libelle;
    private EnumContrainte enumContrainte;
    private Date dateCreation;



    public Contrainte(String libelle, EnumContrainte enumContrainte, Date dateCreation) {
        this.libelle = libelle;
        this.enumContrainte = enumContrainte;
        this.dateCreation = dateCreation;
    }

    public void ajouterContrainte() {
        String fichier = "src/csv/contraintes.csv";
        try {
            FileWriter writer = new FileWriter(fichier, true);
            writer.write(libelle + "," + enumContrainte + "," + dateCreation + ";" + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ajout de la contrainte");
        }
    }
    public void afficherTousLesContraintes() {
        String fichier = "src/csv/contraintes.csv";
        try {
            String contenu = new String(Files.readAllBytes(Paths.get("src/csv/contraintes.csv")));
            String[] lignes = contenu.split(";");
            for (String ligne : lignes) {
                System.out.println(ligne);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de l'affichage des contraintes");
        }
    }
    public void supprimerContrainte() {

    }

    //getters et setters
    public String getLibelle() {
        return libelle;
    }
    public EnumContrainte getEnumContrainte() {
        return enumContrainte;
    }
    public Date getDateCreation() {
        return dateCreation;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public void setEnumContrainte(EnumContrainte enumContrainte) {
        this.enumContrainte = enumContrainte;
    }
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
 

}
