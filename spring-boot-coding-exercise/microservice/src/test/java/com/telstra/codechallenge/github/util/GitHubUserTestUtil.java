package com.telstra.codechallenge.github.util;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.codechallenge.github.dto.GitHubUsersResponse;
import com.telstra.codechallenge.github.dto.UserInfo;

/**
 * Utility Class for GitHubUserTest
 * 
 * @author Abhijit
 *
 */
public class GitHubUserTestUtil {

	public static final String MOCK_INVALID_REST_URL = "/api/github/users?count=a";
	public static final String MOCK_VALID_API_URL = "/api/github/users?count=2";
	public static final String MOCK_GITHUB_API_URL = "https://api.github.com/search/users?q=followers:0&sort=joined&order=asc&per_page=2";
	
	/**
	 * Creates and returns mock object of GitHubUsersResponse
	 * 
	 * @return GitHubUsersResponse :Mocked GitHubUsersResponse object
	 */
	public static GitHubUsersResponse getMockGitHubUserResponse() {
		GitHubUsersResponse mockGitHubUserResponse = new GitHubUsersResponse();
		mockGitHubUserResponse
				.setItems(Arrays.asList(new UserInfo(1l, "abhi", "abhi_url"), new UserInfo(2l, "kiran", "kiran_url")));
		return mockGitHubUserResponse;

	}

	/**
	 * Converts the JSON String value into given class object
	 * 
	 * @param {@link String} 
	 * @param {@link Class<T>} 
	 * @return T
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	
}
