package com.scw.devops.collector.vcs.data;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlFormatter {

	static RepositoryLocation splitSourceRepository(String repositoryUrl) {
		URL url;
		try {
			url = new URL(repositoryUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed gitlab URL : " + repositoryUrl);
		}
		String[] seperatedPath = url.getPath().split("/");
		if (seperatedPath.length != 3) { // The first entry will be an empty string
			throw new RuntimeException("Expected 2 elements in gitlab URL path: " + repositoryUrl);
		}
		String projectName = seperatedPath[2];
		if (projectName.endsWith(".git")) {
			projectName = projectName.substring(0, projectName.length() - 4);
		}
		return new RepositoryLocation(seperatedPath[1], projectName);
	}

	static String constructHttpUrl(String gitlabUrl, String groupName, String projectName) {
		return gitlabUrl + "/" + groupName + "/" + projectName + ".git";
	}

}
