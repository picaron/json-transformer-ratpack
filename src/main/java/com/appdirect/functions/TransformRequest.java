package com.appdirect.functions;

import com.fasterxml.jackson.databind.JsonNode;

public class TransformRequest {
	private JsonNode input;
	private String query;

	public JsonNode getInput() {
		return input;
	}

	public void setInput(JsonNode input) {
		this.input = input;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
