package src.model;

import java.util.Date;

public class ActionItem {
    private String quoi;
    private String quand;
    private Date dueDate;

    public ActionItem(String quoi, String quand, Date dueDate) {
        this.quoi = quoi;
        this.quand = quand;
        this.dueDate = dueDate;
    }

    public String getQuoi() {
        return quoi;
    }

    public String getQuand() {
        return quand;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setQuoi(String quoi) {
        this.quoi = quoi;
    }

    public void setQuand(String quand) {
        this.quand = quand;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}