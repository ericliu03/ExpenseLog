package edu.brandeis.yangliu.expenselog;

import java.io.Serializable;
import java.util.Date;

public class ExpenseLogEntryData implements Serializable {
    private Date date;
    private String description;
    private String note;

    public ExpenseLogEntryData(String description, String note) {
        this.description = description;
        this.note = note;
        this.date = new Date();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String des) {
        this.description = des;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return this.date;
    }

    public void setTime(Date date) {
        this.date = date;
    }
}
