package com.codecool.chess.tournament;

import java.sql.Date;

public class Tournament {
    private String name;
    private String site;
    private Date start;
    private Date end;
    private int numberOfPlayers;

    public Tournament() {
    }

    public Tournament(String name, String site, Date start, Date end, int numberOfPlayers) {
        this.name = name;
        this.site = site;
        this.start = start;
        this.end = end;
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", numberOfPlayers=" + numberOfPlayers +
                '}';
    }
}
