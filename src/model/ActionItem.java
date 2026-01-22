package src.model;

import java.time.LocalDate;

public class ActionItem {
    private String quoi;
    private LocalDate quand;
    private String qui;

// Ajoute ce constructeur EXACTEMENT comme ceci :
    public ActionItem(String quoi, String qui, LocalDate quand) {
        this.quoi = quoi;
        this.qui = qui;
        this.quand = quand;
    }

    public String getQuoi() {
        return quoi;
    }

    public LocalDate getQuand() {
        return quand;
    }

    public String getQui() {
        return qui;
    }

    public void setQuoi(String quoi) {
        this.quoi = quoi;
    }

    public void setQuand(LocalDate quand) {
        this.quand = quand;
    }

    public void setQui(String qui) {
        this.qui = qui;
    }
}