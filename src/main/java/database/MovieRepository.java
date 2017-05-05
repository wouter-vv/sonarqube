/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import entities.Movie;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository for the Movie class
 * 
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public interface MovieRepository extends MongoRepository<Movie, Integer>  {
    public Movie findByNaam(String naam);
    public Movie findByPath(String path);
    public List<Movie> findAllByGenre(String genre);
}
