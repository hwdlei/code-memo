package zx.soft.jetty.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import zx.soft.jetty.model.User;
import zx.soft.jetty.service.UserService;

/**
 * 用户统计数据
 * 
 * @author wanggang
 *
 */
@Controller
@RequestMapping("/users/{uid}")
public class UserController {

	@Inject
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	User add(@PathVariable long uid, @RequestBody User user) {
		user.setUid(uid);
		return userService.add(user);
	}

	@RequestMapping(value = "/{mid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long uid, @PathVariable long mid) {
		userService.deleteAllByUidAndMid(uid, mid);
	}

	@RequestMapping(value = "/{mid}/gender", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	int queryGenderByUid(@PathVariable long uid, @PathVariable long mid) {
		return userService.queryGenderByUid(uid, mid);
	}

}
