package com.twitter.api;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.stream.JsonReader;
import com.google.gson.Gson;

public class SearchProcessing {

	public static void main(String[] args) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = null;
		JSONArray arr = new JSONArray();

		obj = parser.parse(new FileReader("C:\\test\\sample\\rawDataSearch.json"));

		JSONArray jarray = new JSONArray();
		JSONObject json = new JSONObject();
		JSONArray jsonResults = new JSONArray();
		String str = "";
		String temp = "";
		Gson gson = new Gson();

		// To Remove emoticons
		String regex = "[^\\u1F600-\\u1F6FF\\s]";

		// To Remove url
		String url = "[http[sS]://[A-Za-z0-9].+]";
		
		// To Remove Stop words
		Pattern pattern = Pattern.compile("\\b(the|this|A|you|why|I|its)\\b\\s?", Pattern.CASE_INSENSITIVE);

		PrintWriter pw_json = new PrintWriter(new FileWriter("ExtractedSearchData.json"));
		PrintWriter pw_text = new PrintWriter(new FileWriter("Twitter_Search_Data.txt"));

		jarray = (JSONArray) obj;

		for (int i = 0; i < jarray.size(); i++) {
			JSONObject ob = new JSONObject();
			json = (JSONObject) jarray.get(i);
			jsonResults = (JSONArray) json.get("results");

			TwitterPojo[] data = gson.fromJson(jsonResults.toString(), TwitterPojo[].class);

			for (int j = 0; j < data.length; j++) {
				str = data[j].getText().replaceAll(regex, "");
				Matcher match = pattern.matcher(str);
				while (match.find()) {
					str = str.replace(match.group(), "");
				}
				
				str.replaceAll("#","");
				str.replaceAll("@","");
				
				temp = temp + str;
				ob.put("tweet", str);
				ob.put("Id", data[j].getId());
				arr.add(ob);

			}

			pw_json.println(arr);
			pw_text.println(temp);
			pw_json.close();
			pw_text.close();
		}
	
	}
}
