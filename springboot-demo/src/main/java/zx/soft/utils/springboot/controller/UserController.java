package zx.soft.utils.springboot.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zx.soft.utils.springboot.dao.UserDao;
import zx.soft.utils.springboot.domain.User;
import zx.soft.utils.springboot.domain.User.Name;

//Imports ...

@Controller
public class UserController {

	@Autowired
	private UserDao userDao;

	/**
	* GET /create  --> Create a new user and save it in the database.
	*/
	@RequestMapping("/create")
	@ResponseBody
	public String create(String email, String firstName, String lastName) {
		User user = null;
		try {
			user = new User(email, new Name(firstName, lastName), new Date());
			userDao.save(user);
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created! (id = " + user.getId() + ")";
	}

	/**
	* GET /delete  --> Delete the user having the passed id.
	*/
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(long id) {
		try {
			User user = new User(id);
			userDao.delete(user);
		} catch (Exception ex) {
			return "Error deleting the user:" + ex.toString();
		}
		return "User succesfully deleted!";
	}

	/**
	* GET /get-by-email  --> Return the id for the user having the passed
	* email.
	*/
	@RequestMapping("/get-by-email")
	@ResponseBody
	public String getByEmail(String email) {
		String userId;
		try {
			User user = userDao.findByEmail(email);
			userId = String.valueOf(user.getId());
		} catch (Exception ex) {
			return "User not found";
		}
		return "The user id is: " + userId;
	}

	/**
	* GET /update  --> Update the email and the name for the user in the
	* database having the passed id.
	*/
	@RequestMapping("/update")
	@ResponseBody
	public String updateUser(long id, String email, String firstName, String lastName) {
		try {
			User user = userDao.findOne(id);
			user.setEmail(email);
			user.setName(new Name(firstName, lastName));
			userDao.save(user);
		} catch (Exception ex) {
			return "Error updating the user: " + ex.toString();
		}
		return "User succesfully updated!";
	}

}