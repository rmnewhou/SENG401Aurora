package seng401Proj;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo; 
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

//Get the cache periods that are currently assigned
//Set the cache periods to a value in seconds
public class CachePeriod {
	/**
	 * @param info
	 * @return
	 */
	public static Response CachePeriod(@Context UriInfo info) {
		String type = info.getQueryParameters().getFirst("type");
		
		String data = "";

		switch (type) {
		case "get":
			data = "AceCachePeriod: " + String.valueOf(CacheController.getInstance().getAceCachePeriod()) +
			"\nAllCachePeriod: " + String.valueOf(CacheController.getInstance().getAllCachePeriod()) +
			"\nArchiveCachePeriod: " + String.valueOf(CacheController.getInstance().getArchiveCachePeriod()) +
			"\nEmbedCachePeriod: " + String.valueOf(CacheController.getInstance().getEmbedCachePeriod()) +
			"\nImagesCachePeriod: " + String.valueOf(CacheController.getInstance().getImagesCachePeriod()) +
			"\nMapCachePeriod: " + String.valueOf(CacheController.getInstance().getMapCachePeriod()) +
			"\nWeatherCachePeriod: " + String.valueOf(CacheController.getInstance().getWeatherCachePeriod()) +
			"\nLocationCachePeriod: " + String.valueOf(CacheController.getInstance().getLocationsCachePeriod());
			return Response.status(200).entity(data).build();
		case "set":
			String section = info.getQueryParameters().getFirst("section");
			String value = info.getQueryParameters().getFirst("value");
			
			switch (section) {
				case "Ace":
					data = "AceCachePeriod was: " + String.valueOf(CacheController.getInstance().getAceCachePeriod()) +
					"\nAceCachePeriod is now: " + value;
					
					CacheController.getInstance().setAceCachePeriod(Long.parseLong(value));
					
					return Response.status(200).entity(data).build();
				
				case "All":
					data = "AllCachePeriod was: " + String.valueOf(CacheController.getInstance().getAllCachePeriod()) +
					"\nAllCachePeriod is now: " + value;
					
					CacheController.getInstance().setAllCachePeriod(Long.parseLong(value));
					
					return Response.status(200).entity(data).build();
				
				case "Archive":
					data = "ArchiveCachePeriod was: " + String.valueOf(CacheController.getInstance().getArchiveCachePeriod()) +
					"\nArchiveCachePeriod is now: " + value;
					
					CacheController.getInstance().setArchiveCachePeriod(Long.parseLong(value));
					
					return Response.status(200).entity(data).build();
					
				case "Embed":
					data = "EmbedCachePeriod was: " + String.valueOf(CacheController.getInstance().getEmbedCachePeriod()) +
					"\nEmbedCachePeriod is now: " + value;
					
					CacheController.getInstance().setEmbedCachePeriod(Long.parseLong(value));
					
					return Response.status(200).entity(data).build();
					
				case "Images":
					data = "ImagesCachePeriod was: " + String.valueOf(CacheController.getInstance().getImagesCachePeriod()) +
					"\nImagesCachePeriod is now: " + value;
					
					CacheController.getInstance().setImagesCachePeriod(Long.parseLong(value));
					
					return Response.status(200).entity(data).build();
					
				case "Map":
					data = "MapCachePeriod was: " + String.valueOf(CacheController.getInstance().getMapCachePeriod()) +
					"\nMapCachePeriod is now: " + value;
					
					CacheController.getInstance().setMapCachePeriod(Long.parseLong(value));
					
					return Response.status(200).entity(data).build();
					
				case "Weather":
					data = "WeatherCachePeriod was: " + String.valueOf(CacheController.getInstance().getWeatherCachePeriod()) +
					"\nWeatherCachePeriod is now: " + value;
					
					CacheController.getInstance().setWeatherCachePeriod(Long.parseLong(value));
					
					return Response.status(200).entity(data).build();
					
				case "Locations":
					data = "LocationCachePeriod was: " + String.valueOf(CacheController.getInstance().getLocationsCachePeriod()) +
					"\nLocationCachePeriod is now: " + value;
					
					CacheController.getInstance().setLocationsCachePeriod(Long.parseLong(value));
					
					return Response.status(200).entity(data).build();
					
				default:
	        	//Should be 400
					return Response.status(400).build();
			}	

	
		default:
        	//Should be 400
			return Response.status(400).build();
		}
	}
	
}
