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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TransformRequest that = (TransformRequest) o;
		if (!input.equals(that.input)) return false;
		return query.equals(that.query);
	}

	@Override
	public int hashCode() {
		int result = input.hashCode();
		result = 31 * result + query.hashCode();
		return result;
	}
}
