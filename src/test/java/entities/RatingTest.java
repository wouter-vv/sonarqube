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
public class RatingTest {
    
    private Rating rt;
    
    public RatingTest() {
        rt = new Rating(4, null, null);
    }
    
     @Test
    public void testGetters() {
        assertEquals(4, rt.getRating());
    }
    @Test
    public void testSetters() {
        rt.setRating(5);
        assertEquals(5, rt.getRating());
    }
}
