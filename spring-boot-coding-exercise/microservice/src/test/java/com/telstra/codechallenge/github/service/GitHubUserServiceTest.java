package com.telstra.codechallenge.github.service;

import static com.telstra.codechallenge.github.util.GitHubUserTestUtil.getMockGitHubUserResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.telstra.codechallenge.github.dto.GitHubUsersResponse;
import com.telstra.codechallenge.github.dto.UserInfo;
import com.telstra.codechallenge.github.util.GitHubUserTestUtil;

@RunWith(SpringRunner.class)
public class GitHubUserServiceTest {

	@InjectMocks
	private GitHubUserServiceImpl gitHubUserService;

	@Mock
	private RestTemplate restTemplate;

	@Before
	public void setup() {
		ReflectionTestUtils.setField(gitHubUserService, "USER_BASE_URL", "https://api.github.com/search/users");
	}

	@Test
	public void whenValidCall_ThenOK() {
		// Given
		GitHubUsersResponse mockGitHubResponse = getMockGitHubUserResponse();

		// When-Then
		when(restTemplate.getForObject(GitHubUserTestUtil.MOCK_GITHUB_API_URL, GitHubUsersResponse.class))
				.thenReturn(mockGitHubResponse);

		// call
		GitHubUsersResponse gitHubUsersResponse = gitHubUserService.getZeroFollwerOldestUser(2);

		// Asserts
		List<UserInfo> actualResponseItems = gitHubUsersResponse.getItems();
		List<UserInfo> expectedResponseItems = mockGitHubResponse.getItems();
		assertEquals(actualResponseItems.get(0).getId(), expectedResponseItems.get(0).getId());
		assertEquals(actualResponseItems.get(0).getHtml_url(), expectedResponseItems.get(0).getHtml_url());
		assertEquals(actualResponseItems.get(0).getLogin(), expectedResponseItems.get(0).getLogin());
	}

}
