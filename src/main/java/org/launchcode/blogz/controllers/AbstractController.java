package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
//want all controllers to have access to methods in this controller
public abstract class AbstractController {
	//dependency inject DAOs to query database for user and post objects
	@Autowired//spring creates objects for us that implement the database
    protected UserDao userDao;
	
	@Autowired
	protected PostDao postDao;
	//static variables can be accessed in other classes without creating an instances of the origin class
    public static final String userSessionKey = "user_id";
    //sessions let you store little bits of info on the server about the requester, like cookies, so we can keep the user logged in 
    
    protected User getUserFromSession(HttpSession session) {//all classes can ask for the logged in user using this method
    	
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findByUid(userId);
    }
    //if someone correctly authenticates, you need to set them as logged in
    protected void setUserInSession(HttpSession session, User user) {
    	session.setAttribute(userSessionKey, user.getUid());
    }
	
}
