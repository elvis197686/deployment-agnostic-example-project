package com.scw.devops.contract.collector.data;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class GitlabWebhookData {

	final Map<String, Object> additionalProperties = new HashMap<>();

}
