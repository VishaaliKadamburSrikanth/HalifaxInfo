package com.twitter.api;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@SuppressWarnings("deprecation")
public class SteamingAPI {
	
	// Stream Search

	public void streamingSearch() throws Exception {

		String consumerKey = "ElZFdF9ZpeiXXIx7UHIL8BL10";
		String consumerSecret = "m4uZEtDKEhST9k7ZhxuKT2GzWqbxOb7QV2Pt9qTvqdmz6IM3UH";
		String accessToken = "1095417676023902209-puzmI4LQyRD2xWl2NFWf733Y9NAxCa";
		String tokenSecret = "sHgMsLcmRnroHDeTSEuNTPy1ptzXBHaqSgHn7oDUCStO3";

		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
		BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

		List<String> terms = Lists.newArrayList("Halifax");

		hosebirdEndpoint.trackTerms(terms);

		Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, accessToken, tokenSecret);

		ClientBuilder builder = new ClientBuilder().name("Hosebird-Client-01").hosts(hosebirdHosts)
				.authentication(hosebirdAuth).endpoint(hosebirdEndpoint)
				.processor(new StringDelimitedProcessor(msgQueue)).eventMessageQueue(eventQueue);

		Client hosebirdClient = builder.build();
		hosebirdClient.connect();

		JSONArray json_array = new JSONArray();
		while (!hosebirdClient.isDone() && json_array.size() != 1000) {
			String msg = msgQueue.take();
			JSONObject jsonObj = new JSONObject(msg);
			json_array.add(jsonObj);
		}

		PrintWriter writer = new PrintWriter("twitter-live-data.json", "UTF-8");
		writer.println(json_array.toString());
		writer.close();
		hosebirdClient.stop();
	}

}
