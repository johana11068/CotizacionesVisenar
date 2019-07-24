package com.example.cotizacionesvisenar.Model;

public class Movie {

    private String name, description, image, costoSS;

    public Movie() {
    }

    public Movie(String name, String description, String image, String costoSS) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.costoSS = costoSS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCostoSS() {
        return costoSS;
    }

    public void setCostoSS(String costoSS) {
        this.costoSS = costoSS;
    }
}
