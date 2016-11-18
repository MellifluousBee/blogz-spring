package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}//display template
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		
		//1st- get parameters from request object 
		String username= request.getParameter("username");
		String password=request.getParameter("password");
		String verify=request.getParameter("verify");
		if(User.isValidUsername(username)){//if the username meets minimum standards
			if(userDao.findByUsername(username)!=null){
				model.addAttribute("error", "This username already exists.");
				return "signup";
			}else{
//			try{
//				userDao.findByUsername(username);
//				model.addAttribute("error", "This username already exists.");
//				return "signup";
//				}
//			catch(Exception e){//and if the username is not already in the db
//				//validate the password
				if(User.isValidPassword(password)){
					if (password.equals(verify)){//and if password and verify password are equal
						//create a new user and setup the session
						User u= new User(username, password);
						HttpSession thisSession= request.getSession();
						setUserInSession(thisSession, u);
						//add user to database
						userDao.save(u);
					}else{//put error message into the template
						model.addAttribute("verify_error", "Your passwords don't match. Try again.");
						return "signup";
					}
				}else{
					model.addAttribute("password_error", "This is an invalid password. Nice try.");
					return "signup";
				}
			}
		}else{
			model.addAttribute("username_error", "This username is not valid.");
			return "signup";
		}
		
		//2nd-validate parameters (username, password, and verify password using methods in User model class)
		//also make sure the passwords match
		//if they validate, create a new user and store them in the session
		// can access session by saying HttpSession thisSession= request.getSession();
		//going to use the setUserInSession method in the abstract controller
		
		return "redirect:blog/newpost";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}//displays login template
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		//get parameters from request, get user by their username
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		
		//check password is correct, if incorrect, pass an error through the template
		if(userDao.findByUsername(username)!= null){//checks if user exists in DB
			//establish a user object
			User u= userDao.findByUsername(username);//this method returns a user, saved as user u
			//check password against database
			if(u.isMatchingPassword(password)){//checks if the password variable hashes to be the same as the hashed password in the database
				HttpSession thisSession= request.getSession();//log them in if so (by setting the user in the session, same as above)
				setUserInSession(thisSession, u);
			}else{
				model.addAttribute("error", "This is not the correct password");
				return "login";
			}
		}else{
			model.addAttribute("error", "Um... this user doesn't exist");
			return "login";
		}
		
		
		return "redirect:blog/newpost";
	}
	//redirects
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
