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
		
		String type = info.getQueryParameters().getFirst("type");
		String noCachingParam = info.getQueryParameters().getFirst("no-caching");

	    String key = "http://api.auroras.live/v1/?type=all&lat="+lattitude+"&long="+longitude;

	    if (ace != null){
	        key += "&ace="+ace;
	    }
	    if (archive != null){
	        key += "&archive="+archive;
	    }
	    if (forecast != null){
	        key += "&forecast="+forecast;
	    }
	    if (images != null){
	        key += "&images="+images;
	    }
	    if (probability != null){
	        key += "&probability="+probability;
	    }
	    if (threeday != null){
	        key += "&threeday="+threeday;
	    }
	    if (twentysevenday != null){
	        key += "&twentysevenday="+twentysevenday;
	    }
	    if (weather != null){
	        key += "&weather="+weather;
	    }
	    
	    if (noCachingParam != null && noCachingParam.equals("true")){
			
			// We still need to save to cache though. 
			return getResponse(key, type);
		}else{
			
			// Check to see if the response is already in the cache
			Response response = CacheController.getInstance().getCache().getFromCacheMap(key);
			if (response == null){
				
				response = getResponse(key, type);
	
				CacheController.getInstance().getCache().setCacheValue(key, response, type);
				// Now response has been created, so return it. 
				return response; 
			}else{
				// Response
				return response;
			}
		}
	    
	    
	}   
	    
	public static Response getResponse(String key, String type) throws UnirestException{
	    JSONObject jsonObject = new JSONObject();
	    HttpResponse<JsonNode> response =
	                Unirest.get(key)
	                 .asJson();
	    jsonObject = response.getBody().getObject();
	    String att = "Powered by Auroras.live";
	    jsonObject.put("Attribution", att);
	    
	    Response newResponse = Response.status(200).entity(response.getBody().toString()).build();
		
		// Save the response in the cache controller. 
		CacheController.getInstance().getCache().setCacheValue(key, newResponse, type);
		return newResponse;
	}

}
