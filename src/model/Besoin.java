package src.model;


import src.Enum.EnumBesoin;
import java.util.List;
import src.DAO.BesoinDAO;
import java.time.LocalDate;

public class Besoin {
    private int id;
    private String libelle;
    private EnumBesoin enumBesoin;
    private LocalDate dateCreation;
    private String responsable;
    private int progression;
    private boolean estTermine;

    // Constructeur vide (pour le DAO)
    public Besoin() {
        this.dateCreation = LocalDate.now();
        this.enumBesoin = EnumBesoin.A_ANALYSER;
    }

    public void afficherTousLesBesoins() {
        System.out.println("=== LISTE DES BESOINS ===");
        
        // 1. On appelle le DAO pour obtenir la liste d'objets déjà parsés
        BesoinDAO dao = new BesoinDAO();
        List<Besoin> liste = dao.csvToArrayList();
    
        // 2. On vérifie si la liste est vide
        if (liste.isEmpty()) {
            System.out.println("Aucun besoin trouvé dans le fichier.");
        } else {
            // 3. On boucle sur les objets (très propre)
            for (Besoin b : liste) {
                System.out.printf("ID: %d | Libellé: %s | Statut: %s | Responsable: %s | Progression: %d%%%n", 
                    b.getId(), 
                    b.getLibelle(), 
                    b.getEnumBesoin(), 
                    b.getResponsable(), 
                    b.getProgression());
            }
        }
    }
    
    // Methode pour le menu interactif
    public void ajouterBesoinInteractif(String libelle, EnumBesoin enumBesoin) {
        // 1. Créer un nouvel objet Besoin
        Besoin nouveau = new Besoin();
        nouveau.setId(new BesoinDAO().getNextId()); 
        nouveau.setLibelle(libelle);
        nouveau.setEnumBesoin(enumBesoin);
        nouveau.setDateCreation(LocalDate.now());
        // 2. Demander au DAO de l'enregistrer
        BesoinDAO dao = new BesoinDAO();
        dao.save(nouveau);
        
        System.out.println("Besoin ajouté avec succès !");
    }

    // public void supprimerBesoin() {
    // System.out.println("Supprimer un besoin");
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

    public LocalDate getDateCreation() {
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

    public void setDateCreation(LocalDate dateCreation) {
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
