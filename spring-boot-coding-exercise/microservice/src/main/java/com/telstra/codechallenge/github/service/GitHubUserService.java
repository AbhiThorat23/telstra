package com.telstra.codechallenge.github.service;

import org.springframework.stereotype.Service;

import com.telstra.codechallenge.github.dto.GitHubUsersResponse;

@Service
public interface GitHubUserService {

	/**
	 * This method fetches the Oldest Github users having zero followers
	 * 
	 * @param count : Number of users account to be fetched
	 * @return GitHubUsersResponse
	 */
	GitHubUsersResponse getZeroFollwerOldestUser(int count);
}
