package com.twitter.api;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.*;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import flexjson.JSONSerializer;

public class SearchAPI {

	public void search() throws FileNotFoundException, UnsupportedEncodingException
		{
		
		//Search 
			try {				
				
				HttpResponse<String> response = Unirest.get("https://api.twitter.com/1.1/tweets/search/30day/prod.json?query=halifax")
						.header("Authorization", "OAuth oauth_consumer_key=\"ElZFdF9ZpeiXXIx7UHIL8BL10\",oauth_token=\"1095417676023902209-puzmI4LQyRD2xWl2NFWf733Y9NAxCa\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1550944678\",oauth_nonce=\"CtxL6Iy3NSw\",oauth_version=\"1.0\",oauth_signature=\"V%2Br13fsftM8yeQDeZ%2BYue5JqKoA%3D\"")
						.header("cache-control", "no-cache")
						.header("Postman-Token", "cbe33bec-8137-4929-89ac-db0b08f1692f")
						.asString();
				PrintWriter writer = new PrintWriter("twitter-search-data.json", "UTF-8");
				writer.println(response.getBody());
				writer.close();
				
				
			} catch (UnirestException e) {

				e.printStackTrace();
			}
		}
}
