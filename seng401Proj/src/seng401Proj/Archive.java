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
		String type = info.getQueryParameters().getFirst("type");
		String noCachingParam = info.getQueryParameters().getFirst("no-caching");
		    
		String key = "http://api.auroras.live/v1/?type=archive&action="+action;
		
		if (startDate!=null&&endDate!=null){
	        key += "&start="+startDate +"&end="+endDate;
	        key = key.replaceAll(" ", "");
	        key = key.replaceAll("%20", "");
		}
		
		if (noCachingParam != null && noCachingParam.equals("true")){
			
			// We still need to save to cache though. 
			return getResponse(key, type);
		}else{
			
			// Check to see if the response is already in the cache
			Response response = CacheController.getInstance().getCache().getFromCacheMap(key);
			if (response == null){
				
				response = getResponse(key, type);

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
	    HttpResponse<JsonNode> response = Unirest.get(key).asJson();    
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
	    Response newResponse = Response.status(200).entity(response.getBody().toString()).build();
		
		// Save the response in the cache controller. 
		CacheController.getInstance().getCache().setCacheValue(key, newResponse, type);
		return newResponse;
	}
	
}
