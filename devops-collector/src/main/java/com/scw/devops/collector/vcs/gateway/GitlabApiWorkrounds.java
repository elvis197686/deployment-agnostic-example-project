package com.scw.devops.collector.vcs.gateway;

import java.io.IOException;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.http.Query;
import org.gitlab.api.models.GitlabProject;

// This class only exists to allow bugs to be worked round
// Once these issues are fixed we can switch back to using the PAI directly
class GitlabApiWorkrounds {

	static byte[] getRawFileContentWithCorrectFileEncoding(GitlabAPI gitlabApi, Integer projectId, String sha,
			String filepath) throws IOException {
		// Dots are not encoded in UTF-8, but the Gitlab API (in the version we use)
		// expects it to be encoded.
		Query query = new Query().append("ref", sha);

		String tailUrl = GitlabProject.URL + "/" + projectId + "/repository/files/" + encode(filepath) + "/raw"
				+ query.toString();
		return gitlabApi.retrieve().to(tailUrl, byte[].class);
	}

	private static String encode(String filepath) {
		return filepath.replace(".", "%2E");
	}

}
