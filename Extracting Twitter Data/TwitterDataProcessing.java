package com.twitter.api;

public class TwitterDataProcessing {

	public static void main(String[] args) throws Exception {

		SearchAPI search = new SearchAPI();
		search.search();

		SteamingAPI steamingSearch = new SteamingAPI();
		steamingSearch.streamingSearch();

	}
}
