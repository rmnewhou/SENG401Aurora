package seng401Proj;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
@Path ("/")
public class Config {
	
	
	
	
	
	@Path("/")
	@GET
	@Produces({"application/json","images/png"})
	public Response getType(@Context UriInfo info) throws JSONException, UnirestException {

		// Get the type we will be working with. 
		String config = info.getQueryParameters().getFirst("config");
		
		System.out.println("\n\nLogin = " + config);
		
		System.out.println("Parameters = " + info.getQueryParameters()+"\n\n");
		
		String username = info.getQueryParameters().getFirst("login");
		String password = info.getQueryParameters().getFirst("password");	
		
		if(!username.equals("admin") || password.equals("abc123"))
			return Response.status(401).build();
		
		if(config == null)
			return Response.status(404).build();

		switch (config) {
			case "clear_cache":
				return ClearCache.clearCache();
			case "get_cache_contents":
				return GetCache.getCacheContents(info);
			case "cache_period":
				return CachePeriod.CachePeriod(info);
		
			default:
	        	//Should be 400
				return Response.status(400).build();
		}
		/*switch (type) {
	        case "weather": 	
	        	return Weather.getWeather(info);
	        case "images":
	        	return Images.getImages(info);
	        case "ace":
	        	return Ace.getAce(info);
	        case "locations":
	        	return Locations.getLocations(info);
	        case "all":
	        	return All.getAll(info);
	        case "archive":
	        	return Archive.getArchive(info);
	        case "map":
	        	return Map.getMap(info);
	        case "embed":
	        	return Embed.getEmbed(info);		// Will be implemented 
*/	        	
	        	
	        
			
	}
}
