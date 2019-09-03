package com.twitter.api;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.stream.JsonReader;
import com.google.gson.Gson;

public class SteamProcessing {

	public static void main(String[] args) throws IOException  {		

		JsonReader reader = new JsonReader(new FileReader("C:\\test\\sample\\rawDataStreaming.json"));
		PrintWriter pw_text = new PrintWriter(new FileWriter("Twitter_Streaming_Data.txt"));	
		PrintWriter  pw_json= new PrintWriter(new FileWriter("ExtractedStreamingData.json"));

		Gson gson = new Gson();
		TwitterPojo[] data  = gson.fromJson(reader, TwitterPojo[].class);

		// To Remove emoticons
		String regex = "[^\\u1F600-\\u1F6FF\\s]";

		// To Remove url
		String url = "[http[sS]://[A-Za-z0-9].+]";
		
		// To Remove Stop words
		Pattern pattern = Pattern.compile("\\b(|the|this|A|you|why|I|its)\\b\\s?", Pattern.CASE_INSENSITIVE);

		String str="";
		JSONArray jarray = new JSONArray();

		for(int i=0;i<data.length;i++)
		{

			JSONObject json = new JSONObject();
			str = data[i].text.toString().replaceAll(regex, "");
			Matcher match = pattern.matcher(str);
			while(match.find()) {
				str = str.replace(match.group(), "");
			}
			str.replaceAll("#","");
			str.replaceAll("@","");
			
			json.put("tweet", str);
			json.put("Id", data[i].getId());
			jarray.add(json);

			pw_text.println(str);
		}
		pw_json.println(jarray);		
		pw_json.close();	
		pw_text.close();
	}
	
}
