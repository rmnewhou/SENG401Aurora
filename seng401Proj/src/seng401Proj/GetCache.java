package seng401Proj;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo; 
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

//return the current cached content for each section
public class GetCache {
	public static Response getCacheContents(@Context UriInfo info) {
		
String section = info.getQueryParameters().getFirst("section");
		
		switch (section) {
			case "Ace":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
			
			case "All":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
			
			case "Archive":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
				
			case "Embed":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
				
			case "Images":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
				
			case "Map":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
				
			case "Weather":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
				
			//missing case "Location":?
				
			default:
        	//Should be 400
				return Response.status(400).build();

		}

		
		return null;
	}
}
