/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.util.List;
import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * class to create a Movie object, also used to create a table in the database
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class Movie {
    
    @Id
    private int id;
    
    private String naam;
    public String genre;
    private String beschrijving;
    private String releaseDatum;
    private String regisseur;
    public String path;


    public Movie(int id, String naam, String genre, String beschrijving, String releaseDatum, String regisseur, String path) {
        this.id = id;
        this.naam = naam;
        this.genre = genre;
        this.beschrijving = beschrijving;
        this.releaseDatum = releaseDatum;
        this.regisseur = regisseur;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getGenre() {
        return genre;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getReleaseDatum() {
        return releaseDatum;
    }

    public String getRegisseur() {
        return regisseur;
    }

    public String getPath() {
        return path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setReleaseDatum(String releaseDatum) {
        this.releaseDatum = releaseDatum;
    }

    public void setRegisseur(String regisseur) {
        this.regisseur = regisseur;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Movie: {" + this.id + ", " 
                          + this.naam + ", " 
                          + this.beschrijving + ", "
                          + this.genre + ", "
                          + this.regisseur + ", " 
                          + this.releaseDatum + ", " 
                          + this.path + "}";
    } 
}
