package team.margarita.streamit;

import database.DataAccessImplementation;
import database.UserRepository;
import entities.Movie;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Datasockets with data are recieved from the client
 * 
 * @author Admin ( created on the remote server)
 */
public class ServerRequestHandler extends Thread {
    
    private Socket socket;
    private final int PORT = 2500;
    private DataAccessImplementation db;
    
    /**
     * CONSTRUCTORzez
     * @param test 
     */
    public ServerRequestHandler(DataAccessImplementation test) {
        this.db = test;
    }
    
    /**
     * Request handler method
     */
    @Override
    public void run() {
        try
        {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server Started and listening to the port 2500");
 
            while(true)
            {
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                String type = br.readLine();
                
                if (type.equals("login")) {
                    login_request(br, bw);
                } else if (type.equals("registration")) {
                    registration_request(br, bw);
                } else if (type.equals("modify_password")) {
                    modify_password_request(br,bw);
                } else if (type.equals("rating")) {
                    rating_request(br,bw);
                } else if (type.equals("get_movie_details")) {
                    movie_detail_request(br, bw);
                } else if (type.equals("get_movie_name")) {
                    movie_name_request(br, bw);
                } else if(type.equals("ratingAverage")) {
                    movie_rating_request(br,bw);
                } else if(type.equals("deleteUser")) {
                    delete_user(br,bw);
                }else if(type.equals("deleteRating")) {
                    delete_rating(br,bw);
                } else if(type.equals("get_movie_path")) {
                    get_movie_path(br,bw);
                }
        
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
    
    public void get_movie_path(BufferedReader br, BufferedWriter bw) throws Exception {
        System.out.println("Recieved movie path request from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));
        String genre= br.readLine();
        String path = db.getPath(genre);
        bw.write(path + "\n");
        bw.flush();
    }
    /**
     * Delete rating (used for tests)
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void delete_rating(BufferedReader br, BufferedWriter bw) throws Exception {
        System.out.println("Recieved delete rating request from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));
        String email = br.readLine();
        String path = br.readLine();
        boolean ok = db.deleteRating(email, path);
        bw.write(ok + "\n");
        bw.flush();
    }
    
    /**
     * Delete user (used for test)
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void delete_user(BufferedReader br, BufferedWriter bw) throws Exception {
        System.out.println("Recieved delete user request from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));
        String email = br.readLine();
        boolean ok = db.deleteUser(email);
        bw.write(ok + "\n");
        bw.flush();
    }
    
    /**
     * Send average rating to incoming IP-address
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void movie_rating_request(BufferedReader br, BufferedWriter bw) throws Exception {
        String film = br.readLine();
        int avRating = db.getAverRating(film);
        bw.write(avRating+ "\n");
        bw.flush();
    }
    
    /**
     * @author Stef De Brabander
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void movie_name_request(BufferedReader br, BufferedWriter bw) throws Exception {
        System.out.println("Recieved movie name request from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));
        String genre= br.readLine();
        Movie m = db.getMovieByPath(genre);
        bw.write(m.getNaam()+ "\n");
        bw.flush();
    }
        
    /**
     * Send the details of a move from the playing servers
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void movie_detail_request(BufferedReader br, BufferedWriter bw) throws Exception {
        System.out.println("Recieved movie detail request from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));
        String genre = br.readLine();
        Movie m = db.getMovieByPath(genre);
        bw.write(m.getNaam()+ "\n" +m.getBeschrijving()+ "\n" + m.getGenre()+"\n"+m.getRegisseur()+"\n"+m.getReleaseDatum()+"\n");
        bw.flush();
    }
    
    /**
     * Verify the given login values and return true or false
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void login_request(BufferedReader br, BufferedWriter bw) throws Exception {
        String email = br.readLine();
        String password = br.readLine();             

        System.out.println("Recieved login request from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));

        if (db.login(email, password)) {
            bw.write("true\n");
        } else {
            bw.write("false\n");
        }
        
        bw.flush();
    }
    
    /**
     * Request to add a rating to a movie
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void rating_request(BufferedReader br, BufferedWriter bw) throws Exception {
        String email = br.readLine();
        int rating = Integer.parseInt(br.readLine());
        String film = br.readLine();
        System.out.println("Recieved rating request from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));
        
        if (db.rating(email, rating, film)) {
            bw.write("true\n");
        } else {
            bw.write("false\n");
        }
        
        bw.flush();
    }
    
    /**
     * Registration request
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void registration_request(BufferedReader br, BufferedWriter bw) throws Exception {
        String voornaam = br.readLine();
        String achternaam = br.readLine();
        String email = br.readLine();
        String wachtwoord = br.readLine();
        
        System.out.println("Recieved registration request from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));
        DataAccessImplementation da = new DataAccessImplementation();
        if (da.getUser(email) != null) {
            bw.write("This email is already used\n");
        }
        else if (db.registration(voornaam, achternaam, email, wachtwoord)) bw.write("true\n");
        else bw.write("false\n");
        
        bw.flush();
    }
    
    /**
     * Request to modify a password of a given user
     * @param br
     * @param bw
     * @throws Exception 
     */
    public void modify_password_request(BufferedReader br, BufferedWriter bw) throws Exception {
        String email = br.readLine();
        String password = br.readLine();   
        String newPass = br.readLine();

        System.out.println("Recieved passChange from: " + socket.getRemoteSocketAddress().toString().replace('/', ' '));

        if(db.changePassword(email,password,newPass)) {
            bw.write("true\n");
        }
        else {
            bw.write("false\n");
        }
        
        bw.flush();
    }
}
