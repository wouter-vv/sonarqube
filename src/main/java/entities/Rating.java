/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * class to create a Rating object, also used to create a table in the database
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class Rating {
    
    private int rating;
    @DBRef
    private Movie movie;
    @DBRef
    private User user;

    public Rating(int rating, Movie movie, User user) {
        this.rating = rating;
        this.movie = movie;
        this.user = user;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public int getRating() {
        return this.rating;
    }
}
