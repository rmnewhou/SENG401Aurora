package seng401Aurora;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class All {

	public static Response getAll(@Context UriInfo info)throws JSONException, UnirestException{

		String lattitude = info.getQueryParameters().getFirst("lat");
	    String longitude = info.getQueryParameters().getFirst("long");
	   	String ace = info.getQueryParameters().getFirst("ace");
		String archive = info.getQueryParameters().getFirst("archive");
		String forecast = info.getQueryParameters().getFirst("forecast");
		String images = info.getQueryParameters().getFirst("images");
		String probability = info.getQueryParameters().getFirst("probability");
		String threeday = info.getQueryParameters().getFirst("threeday");
		String twentysevenday = info.getQueryParameters().getFirst("twentysevenday");
		String weather = info.getQueryParameters().getFirst("weather");

	    String apiCall = "http://api.auroras.live/v1/?type=all&lat="+lattitude+"&long="+longitude;

	    if (ace != null){
	        apiCall += "&ace="+ace;
	    }
	    if (archive != null){
	        apiCall += "&archive="+archive;
	    }
	    if (forecast != null){
	        apiCall += "&forecast="+forecast;
	    }
	    if (images != null){
	        apiCall += "&images="+images;
	    }
	    if (probability != null){
	        apiCall += "&probability="+probability;
	    }
	    if (threeday != null){
	        apiCall += "&threeday="+threeday;
	    }
	    if (twentysevenday != null){
	        apiCall += "&twentysevenday="+twentysevenday;
	    }
	    if (weather != null){
	        apiCall += "&weather="+weather;
	    }

	    JSONObject jsonObject = new JSONObject();
	    HttpResponse<JsonNode> response =
	                Unirest.get(apiCall)
	                 .asJson();
	    jsonObject = response.getBody().getObject();
	    String att = "Powered by Auroras.live";
	    jsonObject.put("Attribution", att);
	    return Response.status(200).entity(response.getBody().toString()).build();
	}

}
