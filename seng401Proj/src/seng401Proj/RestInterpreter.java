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
public class RestInterpreter {

		@Path("/")
		@GET
		@Produces({"application/json","images/png"})
		public Response getType(@Context UriInfo info) throws JSONException, UnirestException {

			// Get the type we will be working with. 
			String type = info.getQueryParameters().getFirst("type");
			
			System.out.println("\n\nType = " + type);
			
			System.out.println("Parameters = " + info.getQueryParameters()+"\n\n");

				if(type == null){
					return Response.status(404).build();
				}

			switch (type) {
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
		        	
		        	
		        default:
		        	//Should be 400
					return Response.status(400).build();
				
			}
		}
	} 	

