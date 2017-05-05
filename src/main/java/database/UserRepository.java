/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for the User class
 * 
 * @author Cédric D'hooge
 */
public interface UserRepository extends MongoRepository<User, Integer> {
    
    public User findByWachtwoordAndEmail(String wachtwoord, String email);
    public User findByEmail(String email);
    public boolean removeByEmail(String email);
}
