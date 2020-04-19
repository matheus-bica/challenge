package com.axur.challenge.formatters;

public class JsonFormatter {
	private String client;
	private String regex;
	private String url;
	private String correlationId;
	private boolean match;

	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getRegex() {
		if (regex == "") {
			return null;
		}
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public boolean getMatch() { return match; }
	public void setMatch(boolean match) { this.match = match; }
	
	public String toString() {
        return String.format("{'client':'%s', 'regex':'%s', 'url':'%s', 'correlationId':'%s', 'match':%s}", client, regex, url, correlationId, match);
    }

    public String getOutputMessage() {
		if (regex == "") {
			return String.format("{'match':%s , 'regex':%s, 'correlationId':%s}", match, getRegex(), correlationId);
		}
		else {
			return String.format("{'match':%s , 'regex':'%s', 'correlationId':%s}", match, regex, correlationId);
		}
	}
}
