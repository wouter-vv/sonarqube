/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author <cedric.dhooge@student.odisee.be>
 */
public class UserTest {
    
    private User usr;
    
    public UserTest() {
        usr = new User(0, "Cedric", "Dhooge", "cedric.dhooge@student.odisee.be", "Azerty123");
    }
    
     @Test
    public void testGetters() {
        assertEquals(0, usr.getId());
        assertEquals("Cedric", usr.getVoornaam());
        assertEquals("Dhooge", usr.getAchternaam());
        assertEquals("cedric.dhooge@student.odisee.be", usr.getEmail());
        assertEquals("Azerty123", usr.getWachtwoord());
    }
    @Test
    public void testSetters() {
        usr.setId(1);
        assertEquals(1, usr.getId());
        usr.setVoornaam("Thomas");
        assertEquals("Thomas", usr.getVoornaam());
        usr.setAchternaam("VanRaemdonck");
        assertEquals("VanRaemdonck", usr.getAchternaam());
        usr.setEmail("thomas.vanraemdonck@student.odisee.be");
        assertEquals("thomas.vanraemdonck@student.odisee.be", usr.getEmail());
        usr.setWachtwoord("thvrSTD");
        assertEquals("thvrSTD", usr.getWachtwoord());
    }
    
    @Test
    public void testToString() {
        assertEquals("User: {0, Cedric Dhooge, cedric.dhooge@student.odisee.be, Azerty123}", usr.toString());
    }
}
