package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		//get request parameters
		String title=request.getParameter("title");
		String postbody=request.getParameter("body");
		HttpSession thisSession= request.getSession();//define thissession
		User author=getUserFromSession(thisSession);//pass this session into getuserfromsession method so you can use author to instantiate new post
		//validate parameters
		if (title!=null){
			if(postbody != null){//if valid, create new Post using post model and saved using post DAO
				Post p= new Post(title, postbody, author);
				postDao.save(p);
				return "redirect:/blog/" +author.getUsername() +"/" + p.getUid(); 
	
				
			}else{
				model.addAttribute("error", "You must enter something!");
			}
		}else{
			model.addAttribute("error", "You must enter a title");
		}
		
		//if not valid, send them back to form with an error message
		return null;
		// TODO - this redirect should go to the new post's page  		
	}
	
	//handles requests like blog/chris/5
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		//get the given post
		Post p= postDao.findByUid(uid);
		String title=p.getTitle();
		String body=p.getBody();
		//pass the post into the template
		model.addAttribute("title", title);
		model.addAttribute("body", body);
		model.addAttribute("post", p);
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
	
		User u= userDao.findByUsername(username);//this method returns a user, saved as user u
		//get all of the user's posts
		List<Post> postlist=postDao.findByAuthor(u);//this method returns a list of the users posts
		// pass the posts into the template
		//define a list of posts
		// model.addAttribute("nameofattribute", List of posts)
		model.addAttribute("posts", postlist);
		
		return "blog";
	}
	
}
