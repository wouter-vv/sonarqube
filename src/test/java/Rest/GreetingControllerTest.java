/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import entities.Movie;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Wouter Vande Velde <wouter.vandevelde@student.odisee.be>
 */
public class GreetingControllerTest {
    
    /**
     * Test of movies method, of class GreetingController.
     */
    @Test
    public void testMovies() {
        System.out.println("movies");
        String genre = "randomStringThatIsNotAGenre";
        GreetingController instance = new GreetingController();
        List<Movie> result = instance.movies(genre);
        assertNotNull(result);
    }
    
}
