package com.telstra.codechallenge.github.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telstra.codechallenge.github.dto.GitHubUsersResponse;
import com.telstra.codechallenge.github.service.GitHubUserService;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

	@Autowired
	GitHubUserService userService;

	/**
	 * Get the Oldest GitHub User with zero followers
	 * @param count
	 * @return {@link ResponseEntity<GitHubUsersResponse>}
	 */
	@GetMapping("/users")
	public ResponseEntity<GitHubUsersResponse> getUsers(@RequestParam(required = true) Integer count) {
		GitHubUsersResponse  userInfo=  userService.getZeroFollwerOldestUser(count);
		if(userInfo!=null){
			return new ResponseEntity<>(userInfo, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new GitHubUsersResponse(), HttpStatus.NO_CONTENT);
		}
	}
}
