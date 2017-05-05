/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import entities.Movie;
import entities.User;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import team.margarita.streamit.UserRequest;

/**
 *
 * @author Timon
 */
public class DataAccessImplementationTest {
    
   public DataAccessImplementationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    /**
     * Test Rating data
     */
    @Test
    public void testRating() {
        UserRequest.deleteUser("test@test.com");
        UserRequest.registerUser("testVoornaam", "testNaam", "test@test.com", "Azerty123");
        
        System.out.println("details movie length");
        String[] details = UserRequest.get_movie_details("action");
        assertEquals(details.length, 5);
        
        System.out.println("movie path");
        String path = UserRequest.get_movie_path("action");
        System.out.println(path);
        assertEquals(path.contains("C:\\"), true);
        
        System.out.println("rating");
        boolean r = UserRequest.rating(5, "test@test.com", details[0]);
        assertEquals(r, true);
        
        System.out.println("Average rating");
        String avRating = UserRequest.getAverageRating(path);
        assertNotNull(avRating);
        
        UserRequest.deleteUser("test@test.com");
        UserRequest.deleteRating("test@test.com", path);
        
        
    }

    /**
     * Test all user data
     */
    @Test
    public void testUserData() {
        
        System.out.println("delete");
        boolean r = UserRequest.deleteUser("test@test.com");
        
        System.out.println("register");
        r = UserRequest.registerUser("testVoornaam", "testNaam", "test@test.com", "Azerty123");
        assertEquals(true, r);
        
        System.out.println("login");
        r = UserRequest.login("test@test.com", "Azerty123");
        assertEquals(true, r);
        
        System.out.println("change pass");
        r = UserRequest.changePass("test@test.com", "Azerty123", "Azerty");
        assertEquals(true, r);
        
        System.out.println("login");
        r = UserRequest.login("test@test.com", "Azerty");
        assertEquals(true, r);
        
        System.out.println("delete");
        r = UserRequest.deleteUser("test@test.com");
        assertEquals(false, r);
    }
}
