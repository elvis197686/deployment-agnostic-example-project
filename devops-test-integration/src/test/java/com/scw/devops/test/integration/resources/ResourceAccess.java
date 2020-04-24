package com.scw.devops.test.integration.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ResourceAccess {

	private static final String REST_API_BASE_FOLDER = "integration";
	private static final String REST_API_URI_FILE = "requestUri.txt";
	private static final String REST_API_REQUEST_BODY_FILE = "requestBody.json";
	private static final String REST_API_EXPECTED_RESPONSE_JSON_FILE = "expectedResponse.json";

	private static final String GIT_FILES_BASE_FOLDER = "gitfiles";
	private static final String WEBHOOK_BASE_FOLDER = "webhooks";

	public static String getRestApiRequestUri(String restApiExampleName) {
		return readResourceFile(REST_API_BASE_FOLDER + "/" + restApiExampleName + "/" + REST_API_URI_FILE);
	}

	public static String getRestApiRequestBody(String restApiExampleName) {
		String body = readResourceFile(
				REST_API_BASE_FOLDER + "/" + restApiExampleName + "/" + REST_API_REQUEST_BODY_FILE);
		return body;
	}

	public static String getRestApiExpectedResponseJson(String restApiExampleName) {
		return readResourceFile(
				REST_API_BASE_FOLDER + "/" + restApiExampleName + "/" + REST_API_EXPECTED_RESPONSE_JSON_FILE);
	}

	public static String getGitInputDirectory(String gitInputExampleName) {
		return GIT_FILES_BASE_FOLDER + "/" + gitInputExampleName;
	}

	public static Collection<String> getSubDirectories(String projectDirectory, String groupName, String projectName) {
		String fullFilename = getResourceAbsoluteFilename(projectDirectory) + File.separatorChar + groupName
				+ ((projectName == null ? "" : File.separatorChar + projectName));
		try {
			return StreamSupport.stream(Files.newDirectoryStream(Paths.get(fullFilename)).spliterator(), false)
					.filter(p -> p.toFile().isDirectory()).map(p -> p.toFile().getName()).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getGitFileContents(String rootName, String groupName, String projectName,
			String branchOrTagname, String fileName) {
		String body = readResourceFile(rootName + File.separatorChar + groupName + File.separatorChar + projectName
				+ File.separatorChar + branchOrTagname + File.separatorChar + fileName);
		return body;
	}

	public static String getWebhookBody(String webhookName) {
		return readResourceFile(GIT_FILES_BASE_FOLDER + "/" + WEBHOOK_BASE_FOLDER + "/" + webhookName);
	}

	private static String readResourceFile(String fileLocationInClasspath) {
		Resource resource = new ClassPathResource(fileLocationInClasspath);
		try {
			InputStream resourceInputStream = resource.getInputStream();
			return IOUtils.toString(resourceInputStream, StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getResourceAbsoluteFilename(String fileLocationInClasspath) {
		Resource resource = new ClassPathResource(fileLocationInClasspath);
		try {
			System.out.println(resource.getFile().getAbsolutePath());
			return resource.getFile().getAbsolutePath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
