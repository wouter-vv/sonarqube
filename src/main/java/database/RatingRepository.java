/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import entities.Movie;
import entities.Rating;
import entities.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository for the Rating class
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public interface RatingRepository extends MongoRepository<Rating, Integer> {
    
    public Rating findByUserAndMovie(User usr, Movie mov);
    public void removeByUserAndMovie(User usr, Movie mov);
    public List<Rating> findAllByMovie(Movie mov);
   /* public Rating findByWachtwoordAndEmail(String wachtwoord, String email);
    public Rating findByEmail(String email);*/
    
}
