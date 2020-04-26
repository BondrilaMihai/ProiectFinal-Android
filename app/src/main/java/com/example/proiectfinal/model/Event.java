package com.example.proiectfinal.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {
    private String eventTitle;
    private String eventDescription;
    private LocalDate eventDate;
    private long eventPrice;

    public Event() {
    }

    public Event(String eventTitle, String eventDescription) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public long getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(long eventPrice) {
        this.eventPrice = eventPrice;
    }


    private static int lastContactId = 0;

    public static ArrayList<Event> createContactsList(int numEvents) {
        ArrayList<Event> events = new ArrayList<Event>();

        for (int i = 1; i <= numEvents; i++) {
            events.add(new Event("Event " + ++lastContactId, "Description " + ++lastContactId));
        }

        return events;
    }
}
