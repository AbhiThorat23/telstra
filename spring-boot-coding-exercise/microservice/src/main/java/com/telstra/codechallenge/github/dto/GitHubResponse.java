package com.telstra.codechallenge.github.dto;

import java.util.List;

import lombok.Data;

/**
 * This is Generic class to map Response of GitHub API call.
 * 
 * @author abhijit
 *
 * @param <T> : Any Model Object whose fields needs to be mapped
 */
@Data
public class GitHubResponse<T> {
	private List<T> items;
}
