package seng401Proj;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo; 
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ClearCache {
	public static Response clearCache() {
		CacheController.getInstance().clearCache();
		
		String data = "Cache cleared!";
		
		return Response.status(200).entity(data).build();
	}
}
