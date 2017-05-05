/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.Movie;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author <cedric.dhooge@student.odisee.be
 */
public class MovieTest {
    Movie p;
    
    public MovieTest() {
        p = new Movie(0,"asterix","Komedie","Film", "test", "Alain", "C:\\movies");
    }   
    
    @Test
    public void testGetters() {
        assertEquals(0, p.getId());
        assertEquals("asterix", p.getNaam());
        assertEquals("Komedie", p.getGenre());
        assertEquals("Film", p.getBeschrijving());
        assertEquals("test", p.getReleaseDatum());
        assertEquals("Alain", p.getRegisseur());
        assertEquals("C:\\movies", p.getPath());
    }
    
    @Test
    public void testSetters() {
        p.setId(1);
        assertEquals(1, p.getId());
        p.setNaam("Asterix en Obelix");
        assertEquals("Asterix en Obelix", p.getNaam());
        p.setGenre("Cartoon");
        assertEquals("Cartoon", p.getGenre());
        p.setBeschrijving("Leuke film met asterix en obelix");
        assertEquals("Leuke film met asterix en obelix", p.getBeschrijving());
        p.setReleaseDatum("15/12/1992");
        assertEquals("15/12/1992", p.getReleaseDatum());
        p.setRegisseur("Alain de ketel");
        assertEquals("Alain de ketel", p.getRegisseur());
        p.setPath("C:\\movies\\comedy\\asterix.mp4");
        assertEquals("C:\\movies\\comedy\\asterix.mp4", p.getPath());
    }
    
    @Test
    public void testToString() {
        assertEquals("Movie: {0, asterix, Film, Komedie, Alain, test, C:\\movies}", p.toString());
    }
}
