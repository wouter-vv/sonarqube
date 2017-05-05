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

/**
 *
 * @author Cedric
 */
public class PasswordEncryptionTest {
    
    /**
     * test if hash is succesfully salted with username
     */
    @Test
    public void testGetSaltedHash() {
        String password = "Azerty123";
        String username1 = "username";
        String username2 = "test";
        
        String hash1 = PasswordEncryption.GetSaltedHash(password, username1);
        String hash2 = PasswordEncryption.GetSaltedHash(password, username2);
        
        assertFalse(hash1.equals(hash2));
        String hash3 = PasswordEncryption.GetSaltedHash(null, null);
        assertNull(hash3);
    }
}
