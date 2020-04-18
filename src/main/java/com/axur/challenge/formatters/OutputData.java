package com.axur.challenge.formatters;

public class OutputData {
	private String client;
	private String regex;
	private String url;
	private String correlationId;
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getRegex() {
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
	
	public String toString() {
        return String.format("client:%s, regex:%s, url:%s, correlationId:%s", client, regex, url, correlationId);
    }
}
