package seng401Proj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

public class Embed {
	
	public static Response getEmbed(@Context UriInfo info)throws JSONException, UnirestException, IOException
	{
		// Required Params: Image: current, weather, Lat, Long. Lat long only required if Image = weather
		//?type=embed&image=weather&lat=40.7813913&long=-73.976902
		
		String type = info.getQueryParameters().getFirst("type");
		String noCachingParam = info.getQueryParameters().getFirst("no-caching");		
		String key = "https://api.auroras.live/v1/?type=embed";
		
		if (info.getQueryParameters().getFirst("image").matches("current")){
			
			key = key + "&image=current";
			
			if (noCachingParam != null && noCachingParam.equals("true")){
				
				// We still need to save to cache though. 
				return getResponse(key, type);
			}else{
				
				// Check to see if the response is already in the cache
				byte[] imageByteArr = CacheController.getInstance().getCache().getFromCacheMapImage(key);
				if (imageByteArr == null){
		
					// Now response has been created, so return it. 
					return getResponse(key, type); 
				}else{
					// Response
					ByteArrayInputStream byteInputStream = new ByteArrayInputStream(imageByteArr); 
					
					return Response.status(200).entity(byteInputStream).type("image/png").build();
				}
			}		 
		}
		
		
		if (info.getQueryParameters().getFirst("image").matches("weather")){
			// if lat [-90,90] and long [-180,180] and not null.
			
			if (info.getQueryParameters().getFirst("lat") != null && (info.getQueryParameters().getFirst("long")!= null))
			{	
				float latitude = Float.valueOf(info.getQueryParameters().getFirst("lat"));
				float longitude = Float.valueOf(info.getQueryParameters().getFirst("long"));
				if((latitude >= -90 && latitude <= 90) && (longitude >= -180 && longitude <= 180)){	
					key = key + "&image="+ info.getQueryParameters().getFirst("image") + "&lat=" + info.getQueryParameters().getFirst("lat") + "&long=" + info.getQueryParameters().getFirst("long");
					if (noCachingParam != null && noCachingParam.equals("true")){
						
						// We still need to save to cache though. 
						return getResponse(key, type);
					}else{
						
						// Check to see if the response is already in the cache
						byte[] imageByteArr = CacheController.getInstance().getCache().getFromCacheMapImage(key);
						if (imageByteArr == null){
				
							// Now response has been created, so return it. 
							return getResponse(key, type); 
						}else{
							// Response
							ByteArrayInputStream byteInputStream = new ByteArrayInputStream(imageByteArr); 
							
							return Response.status(200).entity(byteInputStream).type("image/png").build();
						}
					}
				}
			}
			else{
				return Response.status(400).build();
			}
		}	
		return Response.status(400).build();

	}
	
	public static Response getResponse(String key, String type) throws UnirestException, IOException{
		HttpResponse<java.io.InputStream> auroraResponse = Unirest.get(key).asBinary();
		
		java.io.InputStream is = auroraResponse.getBody();
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    int nRead;
	    byte[] data = new byte[16384];
	    while ((nRead = is.read(data, 0, data.length)) != -1) {
	        buffer.write(data, 0, nRead);
	    }
	 
	    buffer.flush();
	    
	    byte[] byteArray = buffer.toByteArray();
	
		// Save the response in the cache controller. 
		CacheController.getInstance().getCache().setCacheValue(key, byteArray, type);
		
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray); 
		
		return Response.status(200).entity(byteInputStream).type("image/png").build();
		
	}

}