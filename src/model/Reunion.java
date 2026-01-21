package src.model;

import java.util.Date;
import java.util.List;

public class Reunion {

    private String NomAuteur;
    private String Participants;
    private List<ActionItem> actionItems;



    public Reunion(String NomAuteur, String Participants, List<ActionItem> actionItems) {
        this.NomAuteur = NomAuteur;
        this.Participants = Participants;
        this.actionItems = actionItems;
    }

    public String getNomAuteur() {
        return NomAuteur;
    }
    public String getParticipants() {
        return Participants;
    }
    public List<ActionItem> getActionItems() {
        return actionItems;
    }
    public void setNomAuteur(String NomAuteur) {
        this.NomAuteur = NomAuteur;
    }
    public void setParticipants(String Participants) {
        this.Participants = Participants;
    }
    public void setActionItems(List<ActionItem> actionItems) {
        this.actionItems = actionItems;
    }

}

    

