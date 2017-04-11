package seng401Proj;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Archive {

	// getArchive

	public static Response getArchive(@Context UriInfo info)throws JSONException, UnirestException{
	    String action = info.getQueryParameters().getFirst("action");
		String startDate = info.getQueryParameters().getFirst("start");
		String endDate = info.getQueryParameters().getFirst("end");
		    
		System.out.println("Start Date:"+ startDate + "\n" +"End Date: " + endDate);
		String apiCall = "http://api.auroras.live/v1/?type=archive&action="+action;
		
		if (startDate!=null&&endDate!=null){
	        apiCall += "&start="+startDate +"&end="+endDate;
	        apiCall = apiCall.replaceAll(" ", "");
	        apiCall = apiCall.replaceAll("%20", "");
	        
	       System.out.println(apiCall);
		}

		JSONObject jsonObject = new JSONObject();
	    HttpResponse<JsonNode> response =
	                Unirest.get(apiCall)
	                 .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0")
	                 .asJson();
	    
	    jsonObject = response.getBody().getObject();
	    
	    if (jsonObject.has("statusCode")){
	    	if (jsonObject.get("statusCode") != null){
	    	System.out.println("Status code: " + jsonObject.get("statusCode").toString());
	    	 if (jsonObject.get("statusCode").toString().matches("400")){
	 	    	System.out.println("\n\nIn the if statement");
	 	    	return Response.status(400).build();
	    	 }
	    }
	    }
	   
	    String att = "Powered by Auroras.live";
	    jsonObject.put("Attribution", att);
	    return Response.status(200).entity(response.getBody().toString()).build();

	}
}
