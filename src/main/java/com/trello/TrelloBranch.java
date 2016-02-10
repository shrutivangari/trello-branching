package com.trello;

public class TrelloBranch {
	
	public static void main(String[] args) {
		ApiConnection apiConnection = ApiConnectionFactory.INSTANCE.createApiConnection("https://api.github.com/");
		String json = apiConnection.getApiData();
		System.out.println(json);
	}
}
