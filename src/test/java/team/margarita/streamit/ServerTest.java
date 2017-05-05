/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.margarita.streamit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.caprica.vlcj.medialist.MediaList;
import java.util.ArrayList;
import java.util.List;
import entities.Movie;

/**
 *
 * @author Timon
 */
public class ServerTest {
    
    public ServerTest() {
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

    /*
     * Test of getGenre method, of class Server.
     */
    @Test
    public void testGetGenre() {
        List<Movie> movies = new ArrayList<Movie>();
        System.out.println("getGenre");
        Server instance = new Server("comedy",movies);
        String expResult = "comedy";
        String result = instance.getGenre();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMediaDetails method, of class Server.
     */
    @Test
    public void testGetMediaDetails() {
        List<Movie> movies = new ArrayList<Movie>();
        System.out.println("getMediaDetails");
        Server instance = new Server("comedy",movies);
        String expResult = null;
        String result = instance.getMediaDetails();
        assertEquals(expResult, result);
    }
    /**
     * Test of run method, of class Server.
     */
    @Test
    public void testRun() {
        List<Movie> movies = new ArrayList<Movie>();
        System.out.println("run");
        Server instance = new Server("",movies);
        instance.run();
    }

    /**
     * Test of formatRtspStream method, of class Server.
     */
    @Test
    public void testFormatRtspStream() {
        List<Movie> movies = new ArrayList<Movie>();
        System.out.println("formatRtspStream");
        String genre = "comedy";
        Server instance = new Server("comedy",movies);
        String expResult = ":sout=#rtp{sdp=rtsp://@10.129.35.6:8554/"+genre+"}";
        String result = instance.formatRtspStream(genre);
        assertEquals(expResult, result);
    }
}
