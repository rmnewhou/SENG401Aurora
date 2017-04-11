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


public class Ace{
		
		// NOTE : Need to add error checking 
	
	
	public static Response getAce(@Context UriInfo info)throws JSONException, UnirestException{
		//Optional Paramaters Lat and Long are only required when data = Probability
		// Should always check for lat and long though.
		
		String data = info.getQueryParameters().getFirst("data");
		String lattitude = info.getQueryParameters().getFirst("lat");
		String longitude = info.getQueryParameters().getFirst("long");
		String type = info.getQueryParameters().getFirst("type");
		String noCachingParam = info.getQueryParameters().getFirst("no-caching");
		boolean isSpecial = false;
		
		String key = "https://api.auroras.live/v1/?type=ace&data="+data; 
		if (lattitude != null){
			key = key + "&lat=" +lattitude;
		}
		if (longitude != null){
			key = key + "&long=" +longitude;
		} 
		
		
		if (noCachingParam != null && noCachingParam.equals("true")){
			
			// We still need to save to cache though. 
			return getResponse(key, type, false);
		}else{
			
			// Check to see if the response is already in the special cache
			if (CacheController.getInstance().getSpecialTimes().get(key) != null){
				// Exists inside special cache
				Response response = CacheController.getInstance().getCache().getFromSpecialHashMap(key);
				if (response == null){
					System.out.println("We are using special cache!");
					response = getResponse(key, type, true);
					
					// Now response has been created, so return it. 
					return response; 
				}else{
					// Response
					return response;
				}
			}
			// Check to see if the response is already in the cache
			Response response = CacheController.getInstance().getCache().getFromCacheMap(key);
			if (response == null){
				
				response = getResponse(key, type, false);
	
				// Now response has been created, so return it. 
				return response; 
			}else{
				// Response
				return response;
			}
		}
	}
		
		public static Response getResponse(String key, String type, boolean isSpecial) throws UnirestException{
			JSONObject jsonObject = new JSONObject();
			HttpResponse<JsonNode> response = Unirest.get(key)
											.asJson();
			               jsonObject = response.getBody().getObject();
			String att = "Powered by Auroras.live";
			jsonObject.put("Attribution", att);
			
	
			Response newResponse = Response.status(200).entity(response.getBody().toString()).build();
			
			// Save the response in the cache controller. 
			if (isSpecial){
				CacheController.getInstance().getCache().setSpecialCacheValue(key, newResponse);
			}else{
				CacheController.getInstance().getCache().setCacheValue(key, newResponse, type);
			}
			return newResponse;
		}
		
	
}

