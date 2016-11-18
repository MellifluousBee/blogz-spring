package org.launchcode.blogz.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.blogz.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional//include it on daos
@Repository//treat it like an object repository and automatically creates instances for you
public interface UserDao extends CrudRepository<User, Integer> {
// create one DAO for each model class stored in the database
//use dao to query one of the class in the database within the application
//Spring builds a class that implements this interface for me
//we can't create instances of interfaces, so we have to use spring to autowire the dao to use it for queries	
	User findByUid(int uid);
    
	User findByUsername(String username);
	
    List<User> findAll();
    
    // TODO - add method signatures as needed
    
}
