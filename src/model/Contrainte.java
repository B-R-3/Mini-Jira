package src.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Paths;
import src.Enum.EnumContrainte;


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

    public void ajouterContrainte() {

    }
    public void afficherTousLesContraintes() {
       
    }
    public void supprimerContrainte() {

    }













    //getters et setters
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
