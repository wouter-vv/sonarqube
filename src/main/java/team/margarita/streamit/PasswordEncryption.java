/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.margarita.streamit;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encrypt a password
 * 
 * @author Cedric
 */
public class PasswordEncryption {
    
    /**
     * Returns a salted hash with the SHA-512 algorithm
     * @param password
     * @param username
     * @return 
     */
    public static String GetSaltedHash(String password, String username) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            StringBuilder sb = new StringBuilder();
            md.update(username.getBytes("UTF-8"));
            byte[] bytes = md.digest(password.getBytes("UTF-8"));

            for(int i=0; i< bytes.length ;i++){
               sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (Exception ex) {
            return null;
        }
    }
}
