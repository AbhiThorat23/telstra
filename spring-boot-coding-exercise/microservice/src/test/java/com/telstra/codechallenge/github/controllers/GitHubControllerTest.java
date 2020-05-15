package com.telstra.codechallenge.github.controllers;

import static com.telstra.codechallenge.github.util.GitHubUserTestUtil.getMockGitHubUserResponse;
import static com.telstra.codechallenge.github.util.GitHubUserTestUtil.mapFromJson;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.telstra.codechallenge.github.dto.GitHubUsersResponse;
import com.telstra.codechallenge.github.dto.UserInfo;
import com.telstra.codechallenge.github.service.GitHubUserService;
import com.telstra.codechallenge.github.util.GitHubUserTestUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GitHubController.class)
public class GitHubControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private GitHubUserService service;

	@Test
	public void whenMethodArgumentMismatch_thenBadRequest() throws Exception {
		mvc.perform(get(GitHubUserTestUtil.MOCK_INVALID_REST_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1)));
	}

	@Test
	public void whenNullResultFromService_thenNoContent() throws Exception {
		// When-then
		when(service.getZeroFollwerOldestUser(anyInt())).thenReturn(null);

		// call
		mvc.perform(get(GitHubUserTestUtil.MOCK_VALID_API_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void whenValidMethodArgument_thenOk() throws Exception {
		// Mock Data
		GitHubUsersResponse mockGitHubResponse = getMockGitHubUserResponse();

		// When-then
		when(service.getZeroFollwerOldestUser(anyInt())).thenReturn(mockGitHubResponse);

		// call
		MvcResult result = mvc.perform(get(GitHubUserTestUtil.MOCK_VALID_API_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.items", hasSize(2)))
				.andReturn();

		GitHubUsersResponse gitHubUsersResponse = mapFromJson(result.getResponse().getContentAsString(),
				GitHubUsersResponse.class);

		// Asserts
		List<UserInfo> actualResponseItems = gitHubUsersResponse.getItems();
		List<UserInfo> expectedResponseItems = mockGitHubResponse.getItems();
		assertEquals(actualResponseItems.get(0).getId(), expectedResponseItems.get(0).getId());
		assertEquals(actualResponseItems.get(0).getHtml_url(), expectedResponseItems.get(0).getHtml_url());
		assertEquals(actualResponseItems.get(0).getLogin(), expectedResponseItems.get(0).getLogin());
	}

}
