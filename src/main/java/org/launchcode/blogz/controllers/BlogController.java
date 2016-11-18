package org.launchcode.blogz.controllers;

import java.util.List;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogController extends AbstractController {
//need to use methods on the DAOs to fetch users and blogposts
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String index(Model model){
		
		// TODO - fetch users and pass to template
		List<User> userlist=userDao.findAll();//creates a list of users and stores it in userlist
		model.addAttribute("users", userlist);//adds userlist to template in a for loop
		
		return "index";
	}
	
	@RequestMapping(value = "/blog", method= RequestMethod.GET)
	public String blogIndex(Model model) {
		
		// TODO - fetch posts and pass to template
		List<Post> allpostslist= postDao.findAll();
		model.addAttribute("posts", allpostslist);
		return "blog";
	}
	
}
