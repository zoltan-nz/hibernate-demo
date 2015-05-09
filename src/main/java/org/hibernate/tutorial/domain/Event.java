package org.hibernate.tutorial.domain;

import java.util.Date;

public class Event {
    private Long   id;
    
    private String title;
    private Date   date;
    
    public Event() {
    }
    
    public Date getDate() {
        return date;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
