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
@Path ("/config")
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
		
		if(!username.equals("admin") || !password.equals("abc123"))
			return Response.status(401).build();
		
		if(config == null)
			return Response.status(404).build();

		switch (config) {
			case "clear_cache":
				System.out.println("Clear cache");
				return ClearCache.clearCache();
			case "get_cache_contents":
				System.out.println("Get cache");
				return GetCache.getCacheContents(info);
			case "cache_period":
				System.out.println("Cache Period");
				return CachePeriod.CachePeriod(info);
			case "save_configurations":
				return ConfigFile.getInstance().saveToConfigFile();
		
			default:
	        	//Should be 400
				System.out.println("Config = " + config);
				System.out.println("Error 400");
				return Response.status(400).build();
		}
	}
}
