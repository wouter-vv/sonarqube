/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import database.DataAccessImplementation;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    public static DataAccessImplementation DAI;

    /**
     * get movies from all or a certain genre
     * http://10.129.35.6:8080/movies?genre=comedy
     * 
     * @param genre the genre of the movie
     * @return List of movies
     */
    @RequestMapping("/movies")
    public List<Movie> movies(@RequestParam(value="genre", defaultValue="all") String genre) {
        List<Movie> movies;
        try {
            movies = DAI.getMoviesByGenre(genre);
        } catch (Exception e) {
            movies = new ArrayList<>();
        }
        return movies;
    }
}
