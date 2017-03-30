package seng401Proj;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Locations {
	public static Response getLocations(@Context UriInfo info)throws JSONException, UnirestException{
			String type = info.getQueryParameters().getFirst("type");
			String noCachingParam = info.getQueryParameters().getFirst("no-caching");
			
			String key = "https://api.auroras.live/v1/?type=locations";
			
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
		JSONArray jsonArray = new JSONArray();
		HttpResponse<JsonNode> response = Unirest.get(key).asJson();
		System.out.println(response.getBody().getArray());
		jsonArray = response.getBody().getArray();
		String att = "Powered by Auroras.live";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Attribution", att);
		jsonArray.put(jsonObject);
		Response newResponse = Response.status(200).entity(response.getBody().toString()).build();
		
		// Save the response in the cache controller. 
		CacheController.getInstance().getCache().setCacheValue(key, newResponse, type);
		return newResponse;
	}
	
	
}

