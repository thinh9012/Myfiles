package com.example.model;

public class People {
    private String avartar;
    private String name;
    private Integer old;
    private int color;

    public People(String avartar, String name, int old, int color) {
        this.avartar = avartar;
        this.color = color;
        this.name = name;
        this.old = old;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOld() {
        return old;
    }

    public void setOld(Integer old) {
        this.old = old;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
