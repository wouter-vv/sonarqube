/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import Rest.GreetingController;
import entities.Movie;
import entities.Rating;
import entities.User;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import team.margarita.streamit.ServerRequestHandler;
import team.margarita.streamit.Server;

/**
 * class to communicate with the database
 * 
 * @author Cédric D'hooge
 * @author Wouter Vande Velde
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = GreetingController.class)
public class DataAccessImplementation implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
     
    @Autowired
    private MovieRepository movieRepository;
           
    @Autowired
    private RatingRepository ratingRepository;
    
    public Server server_Comedy; 
    public Server server_Action;
    public Server server_Horror;
   
    /**
     * main method, starts the springapplication to create a connection to the database
     * @param args 
     */
    public static void main(String[] args) {
        SpringApplication.run(DataAccessImplementation.class, args);
    }    
    /**
     * @author <cedric.dhooge@student.odisee.be> <thomas.vanraemdonck@student.odisee.be>
     * Berekent de average rating van een film door alle ratings van die film op te halen en te delen door het aantal
     * @param film
     * @return 
     */
    public int getAverRating(String film) {
        Movie movie = movieRepository.findByNaam(film);
        List<Rating> ratList = ratingRepository.findAllByMovie(movie);
        
        int avgRating = 0;
        
        for (Rating rat : ratList) {
            avgRating += rat.getRating();
        }
        
        try {
            avgRating /= ratList.size();
        } catch (Exception ex) {
            return -1;
        }
        
        return avgRating;
    }
    /**
     * @author <thomas.vanraemdonck@student.odisee.be>
     * Verwijdert een rating in de databank dmv een meegegeven email en path van een film
     * @param email
     * @param path
     * @return 
     */
    public boolean deleteRating(String email, String path) {
        try {
            User usr = userRepository.findByEmail(email);
            Movie mov = movieRepository.findByPath(path);
            ratingRepository.removeByUserAndMovie(usr, mov);
            return true;
        } catch (Exception ex) {
            return false;
        }   
    }
    /**
     * @author <cedric.dhooge@student.odisee.be> <thomas.vanraemdonck@student.odisee.be>
     * Verwijdert een user
     * @param email
     * @return 
     */
    public boolean deleteUser(String email) {
        try {
            userRepository.removeByEmail(email);
            return true;
        } catch (Exception ex) {
            return false;
        }   
    }
    /**
     * Krijgt een filmobject dmv een path
     * @param genre
     * @return de gevraagde film
     */
    public Movie getMovieByPath(String genre) {
        String mediaDetails = "";
        
        if (genre.equals("comedy")) {
            mediaDetails = server_Comedy.getMediaDetails();
        } else if (genre.equals("action")) {
            mediaDetails = server_Action.getMediaDetails();
        } else if (genre.equals("horror")) {
            mediaDetails = server_Horror.getMediaDetails();
        }
        
        System.out.println("MediaDetails = " + mediaDetails);
        mediaDetails = mediaDetails.substring(8, mediaDetails.length()).replace("/", "\\");
        Movie movie = movieRepository.findByPath(mediaDetails);
        return movie;
    }
    
    /**
     * Krijgt de lijst van movies dmv het genre
     * @param genre
     * @return de gevraagde lijst met films
     */
    public List<Movie> getMoviesByGenre (String genre){
        List<Movie> moviesByGenre = new ArrayList<Movie>();
        if(genre.equals("all")) {
            moviesByGenre = movieRepository.findAll();
        } else {
            moviesByGenre = movieRepository.findAllByGenre(genre);
        }
        return moviesByGenre;
    } 
    /**
     * Probeert in te loggen 
     * @param email
     * @param wachtwoord
     * @return true als er geen users gevonden zijn
     */
    public boolean login(String email, String wachtwoord) {
        User usr = userRepository.findByWachtwoordAndEmail(wachtwoord, email);
        
        if (usr != null) {
            return true;
        }
        
        return false;
    }
    /**
     * wachtwoord wijzigen
     * @param email
     * @param password
     * @param newPassword
     * @return true als wachtwoord succesvol is veranderd
     */
    public boolean changePassword(String email, String password,String newPassword) {
        User usr = userRepository.findByWachtwoordAndEmail(password, email);
        if (usr != null) {
            usr.setWachtwoord(newPassword);
            userRepository.save(usr);
            return true;
        }
        
        return false;
    }
    /**
     * rating van film
     * @param email
     * @param rating
     * @param film
     * @return de rating van een bepaalde film
     */
    public boolean rating(String email, int rating, String film) {
        User usr = userRepository.findByEmail(email);
        Movie movie = movieRepository.findByNaam(film);
        
        try {
            ratingRepository.removeByUserAndMovie(usr, movie);
        } catch (Exception ex) {}
        
        if(usr != null && movie != null) {            
            ratingRepository.save(new Rating(rating, movie, usr));
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * returns the path of a movie
     * @param genre
     * @return full path of a movie
     */
    public String getPath(String genre) {
        String mediaDetails = "";
        
        if (genre.equals("comedy")) {
            mediaDetails = server_Comedy.getMediaDetails();
        } else if (genre.equals("action")) {
            mediaDetails = server_Action.getMediaDetails();
        } else if (genre.equals("horror")) {
            mediaDetails = server_Horror.getMediaDetails();
        }
        
        System.out.println("MediaDetails = " + mediaDetails);
        return mediaDetails.substring(8, mediaDetails.length()).replace("/", "\\");
    }
    
    /**
     * user registration
     * 
     * @param voornaam 
     * @param achternaam
     * @param email
     * @param wachtwoord
     * @return true if the user is succesfully created
     */
    public boolean registration(String voornaam, String achternaam, String email, String wachtwoord) {
        try {
            int id = 0;
            
            for (User customer : userRepository.findAll()) {
                id++;
            }
            
            userRepository.save(new User(id, voornaam, achternaam, email, wachtwoord));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    /**
     * gets user object by email
     * @param email
     * @return a user object of the requested user
     */
    public User getUser (String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception ex) {
            return null;
        }
    }
    
    /**
     * test if all movies in the folder are already added to the database
     */
    private void testNewMovies() {
        List<String> genres = Arrays.asList("comedy","action","horror");
        for (int i = 0; i < genres.size(); i++) {
            String directory = "C:/Movies/" + genres.get(i);
            List<String> moviePaths = new ArrayList<String>();
            List<String> movieNames = new ArrayList<String>();
            File[] files = new File(directory).listFiles();
            for(File file : files){
              if(file.isFile() && (file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3).equals("mp4") || file.getAbsolutePath().substring(file.getAbsolutePath().length() - 3).equals("avi"))){
                moviePaths.add(file.getAbsolutePath());
                movieNames.add(file.getName());
              }
            }
            List<String> dbPaths = new ArrayList<String>();
            List<Movie> movies = getMoviesByGenre(genres.get(i));
            for (int x = 0; x < movies.size(); x++) {
                dbPaths.add(movies.get(x).path);
            }
            for (int z = 0; z < moviePaths.size(); z++) {
                if (!(dbPaths.contains(moviePaths.get(z)))) {
                    int y = 0;
                    movieRepository.save(new Movie(movies.size() + 1, movieNames.get(z), genres.get(i), "Needs to be added", "Needs to be added","Needs to be added",moviePaths.get(z)));
                }
            }
       }
    }
    
    /**
     * create instances of 3 different servers with each a genre
     * 
     * @param args
     * @throws Exception 
     */
    @Override
    public void run(String... args) throws Exception {
        //check if there are new movies
        try {
            testNewMovies();
        } catch(Exception e) {
            
        }
        
        List<Movie> comedyMovies = getMoviesByGenre("comedy");
        server_Comedy = new Server("comedy", comedyMovies);
        List<Movie> actionMovies = getMoviesByGenre("action");
        server_Action = new Server("action",actionMovies);
        List<Movie> horrorMovies = getMoviesByGenre("horror");
        server_Horror = new Server("horror",horrorMovies);
        ServerRequestHandler serverL = new ServerRequestHandler(this);
        
        server_Comedy.start();
        server_Action.start();
        server_Horror.start();
        GreetingController.DAI = this;
        serverL.start();
    }
}
