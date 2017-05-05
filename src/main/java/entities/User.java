/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Id;

/**
 * class to create a User object, also used to create a table in the database
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class User {
    
    @Id
    private int id;
    
    private String voornaam;
    private String achternaam;
    private String email;
    private String wachtwoord;
    

    public User(int id, String voornaam, String achternaam, String email, String wachtwoord) {
        this.id = id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.wachtwoord = wachtwoord;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
    
    public String getAchternaam() {
        return achternaam;
    }
    
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }
    
    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    @Override
    public String toString() {
        return "User: {" + id + ", " + voornaam + " " + achternaam + ", " + email + ", " + wachtwoord + '}';
    }
}
