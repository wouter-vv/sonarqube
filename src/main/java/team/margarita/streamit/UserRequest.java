/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.margarita.streamit;

import entities.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Communication as the client with the server with DataSockets
 * 
 * @author Cédric D'hooge
 */
public class UserRequest {
    
    private static Socket socket;
    private static final int PORT = 2500;
    private static final String HOST = "10.129.35.6";
    
    /**
     * Delete rating (used for test)
     * @param email
     * @param path
     * @return 
     */
    public static boolean deleteRating(String email,String path) {
        try
        {
            InetAddress address = InetAddress.getByName(HOST);
            socket = new Socket(address, PORT);
 
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("deleteRating\n");
            bw.write(email + "\n" + path + "\n");
            bw.flush();
 
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String authenticated = br.readLine();
            
            System.out.println("deleteRating = " + authenticated);
            
            if (authenticated.equals("false")) return false;
            else return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false; 
    }
    
    /**
     * Delete user (used for test)
     * @param email
     * @return 
     */
    public static boolean deleteUser(String email) {
        try
        {
            InetAddress address = InetAddress.getByName(HOST);
            socket = new Socket(address, PORT);
 
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("deleteUser\n");
            bw.write(email + "\n");
            bw.flush();
 
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String authenticated = br.readLine();
            
            System.out.println("deleteUser = " + authenticated);
            
            if (authenticated.equals("false")) return false;
            else return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false; 
    }
    
    /**
     * Return average rating of playing movie
     * @param film
     * @return 
     */
    public static String getAverageRating(String film) {
       try
        {
            InetAddress address = InetAddress.getByName(HOST);
            socket = new Socket(address, PORT);
 
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("ratingAverage\n");
            bw.write(film + "\n");
            bw.flush();
 
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String ratingAv = br.readLine();
            
            System.out.println("ratingAv = " + ratingAv);
            
            return ratingAv;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return ""; 
    }
    
    /**
     * @author <cedric.dhooge@student.odisee.be> <thomas.vanraemdonck@student.odisee.be>
     * @param rating
     * @param email
     * @param film
     * @return 
     */
    public static boolean rating(int rating, String email,String film) {
        try
        {
            InetAddress address = InetAddress.getByName(HOST);
            socket = new Socket(address, PORT);
 
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("rating\n");
            bw.write(email + "\n" + rating+"\n" + film + "\n");
            bw.flush();
 
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String authenticated = br.readLine();
            
            System.out.println("rating = " + authenticated);
            
            if (authenticated.equals("false")) return false;
            else return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * @author Cedric Dhooge & Stef De Brabander
     * @param surname
     * @param name
     * @param email
     * @param pwd
     * @return true/false
     * Sends the registration data to the server wich will send a true or false back 
     */
    public static boolean registerUser(String surname, String name, String email, String pwd) {
        try
        {
            BufferedWriter bw = openConnection();
            
            bw.write("registration\n");
            bw.write(surname + "\n" + name + "\n" + email + "\n" + PasswordEncryption.GetSaltedHash(pwd, email) + "\n");
            bw.flush();
 
            return server_response("register");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Change password of logged user
     * @param email
     * @param password
     * @param newPassword
     * @return 
     */
    public static boolean changePass(String email, String password, String newPassword) {
        try
        {
            BufferedWriter bw = openConnection();
            
            bw.write("modify_password\n");
            bw.write(email + "\n" + PasswordEncryption.GetSaltedHash(password, email) + "\n" + PasswordEncryption.GetSaltedHash(newPassword, email)+"\n");
            bw.flush();
 
            return server_response("modify password");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    
    /**
     * @author De Brabander Stef
     * @param email
     * @param password
     * @return true/false
     * Sends the login data to the server wich will send back a true or false if the user
     * was able to login.
     */
    public static boolean login(String email, String password) {
        try
        {
            BufferedWriter bw = openConnection();
            
            bw.write("login\n");
            bw.write(email + "\n" + PasswordEncryption.GetSaltedHash(password, email) + "\n");
            bw.flush();
 
            return server_response("login");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * @author Stef De Brabander
     * @param genre
     * @return 
     */
    public static String get_movie_path(String genre) {
        try
        {
            BufferedWriter bw = openConnection();
            
            bw.write("get_movie_path\n");
            bw.write(genre + "\n");
            bw.flush();
 
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String path = br.readLine();
            
            return path;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }
     
    /**
     * @author <cedric.dhooge@student.odisee.be> <thomas.vanraemdonck@student.odisee.be>
     * @param genre
     * @return 
     */
    public static String[] get_movie_details(String genre) {
        try
        {
            BufferedWriter bw = openConnection();
            
            bw.write("get_movie_details\n" + genre + "\n");
            bw.flush();
 
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String[] movieInfoArray = new String[5];
            
            for(int i = 0; i < movieInfoArray.length; i++) {
                movieInfoArray[i] = br.readLine();
            }
            
            return movieInfoArray;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    /**
     * Returns a bufferedwriter to communicate with the server
     * @return
     * @throws Exception 
     */
    public static BufferedWriter openConnection() throws Exception {
        InetAddress address = InetAddress.getByName(HOST);
        socket = new Socket(address, PORT);

        OutputStream os = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        return new BufferedWriter(osw);
    }
    
    /**
     * Retrieve an answer from the server
     * @param mode
     * @return
     * @throws Exception 
     */
    public static boolean server_response(String mode) throws Exception {
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String state = br.readLine();

        System.out.println(mode + " = " + state);

        if (state.equals("false")) return false;
        else return true;
    }
}
