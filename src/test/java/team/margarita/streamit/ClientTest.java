/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.margarita.streamit;

import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import team.margarita.streamit.Client;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;


/**
 * Methods that contain lines related to the UI can not be tested on the 
 * jenkins buildserver because the buildserver is located on a Linux operating 
 * system
 * 
 * @author thomas van raemdonck
 */
public class ClientTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void test() {
        Client c = new Client();
        assertEquals(c, c);      
    }
    
    /**
     * @author Stef De Brabander
     */
    /*@Test
    public void testClient() {
        Client c= new Client("test@123.test", "actie");
        assertNotNull(c);
        assertNotNull(c.Keuze);
        assertNotNull(c.email);
        assertNotNull(c.film);
        assertNotNull(c.frame);
        assertNotNull(c.genre);
        assertNotNull(c.mediaPlayer);
        assertNotNull(c.movieInfo);
        assertNotNull(c.vlcArgs);
    }*/

    /**
     * Test of setKeuze method, of class Client.
     */
    @Test
    public void testSetKeuze() {
        System.out.println("setKeuze");
        String keuze = "action";
        Client instance = new Client();
        instance.setKeuze(keuze);
        assertEquals(keuze, instance.Keuze);
    }
    
    /**
     * Test of showTime method, of class Client.
     */
    @Test
    public void testShowTime() {
        System.out.println("showTime");
        int keuze = 1000;
        Client instance = new Client();
        String test = instance.formatTime(keuze);
        assertEquals("00:00:01", test);
    }
}
