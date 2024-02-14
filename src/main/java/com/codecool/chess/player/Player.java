package com.codecool.chess.player;

import java.time.LocalDate;

public class Player {
    private int id;
    private String name;
    private int fide_id;
    private LocalDate born;
    private String born_place;
    private int earn_gm;
    private String nationality;

    public Player() {
    }

    public Player(String name, int fide_id, LocalDate born, String born_place, int earn_gm, String nationality) {
        this.name = name;
        this.fide_id = fide_id;
        this.born = born;
        this.born_place = born_place;
        this.earn_gm = earn_gm;
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFide_id() {
        return fide_id;
    }

    public void setFide_id(int fide_id) {
        this.fide_id = fide_id;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public String getBorn_place() {
        return born_place;
    }

    public void setBorn_place(String born_place) {
        this.born_place = born_place;
    }

    public int getEarn_gm() {
        return earn_gm;
    }

    public void setEarn_gm(int earn_gm) {
        this.earn_gm = earn_gm;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fide_id=" + fide_id +
                ", born=" + born +
                ", born_place='" + born_place + '\'' +
                ", earn_gm=" + earn_gm +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
