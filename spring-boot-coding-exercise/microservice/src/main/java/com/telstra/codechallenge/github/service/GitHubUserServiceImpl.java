package com.telstra.codechallenge.github.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.telstra.codechallenge.github.dto.GitHubUsersResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GitHubUserServiceImpl implements GitHubUserService {

	private static final String OLDEST_ZERO_FOLLOWER_USERS = "?q=followers:0&sort=joined&order=asc&per_page=%s";

	@Value("${github.user.baseurl}")
	private String USER_BASE_URL;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public GitHubUsersResponse getZeroFollwerOldestUser(int count) {
		String QUERY_URL = USER_BASE_URL + String.format(OLDEST_ZERO_FOLLOWER_USERS, count);
		log.debug(QUERY_URL);
		return restTemplate.getForObject(QUERY_URL, GitHubUsersResponse.class);
	}

}
