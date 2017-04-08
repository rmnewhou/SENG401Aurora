package seng401Proj;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONObject;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Images {

	public static Response getImages(@Context UriInfo info) throws UnirestException, IOException{

		String action = info.getQueryParameters().getFirst("action");
		String image = info.getQueryParameters().getFirst("image");
		
		String type = info.getQueryParameters().getFirst("type");
		String noCachingParam = info.getQueryParameters().getFirst("no-caching");
		
		
	
			
			
		if (action!=null && action.equals("list"))
		{
			return getList(info, type, noCachingParam);
		}
		
				
		if (image != null)
		{
			return getImage(info, type, noCachingParam);
		}
		
		return Response.status(400).build();
		
		
		
	}
	
	private static Response getImage(@Context UriInfo info, String type, String noCachingParam) throws UnirestException, IOException{
		String image = info.getQueryParameters().getFirst("image");
		JSONArray jsonArray = new JSONArray();
		HttpResponse<JsonNode> locationsResponse = Unirest.get("https://api.auroras.live/v1/?type=locations").asJson();
		jsonArray = locationsResponse.getBody().getArray();
		
		
		System.out.println("Going in");
		for(int i = 0; i < jsonArray.length(); i++){
			System.out.println("Not found yet");
			if (jsonArray.getJSONObject(i).get("id").equals(image)){
				System.out.println("found the location");
				String key = "https://api.auroras.live/v1/?type=images";
				key = key + "&image=" + image;
				
				if (noCachingParam != null && noCachingParam.equals("true")){
					
					// We still need to save to cache though. 
					return getResponse(key, type);
				}else{

					// Check to see if the response is already in the cache
					byte[] imageByteArr = CacheController.getInstance().getCache().getFromCacheMapImage(key);
					if (imageByteArr == null){
						
						// We need to get response from Aurora
						return getResponse(key, type);
					}else{
						// Response
						ByteArrayInputStream byteInputStream = new ByteArrayInputStream(imageByteArr); 
						return Response.status(200).entity(byteInputStream).type("image/png").build();
					}
				}
			}
		}
		return Response.status(400).build();


	}
	private static Response getList(@Context UriInfo info, String type, String noCachingParam) throws UnirestException{
		String action = info.getQueryParameters().getFirst("action");
		if (action.equals("list")){	
			System.out.println("Got in the action place");
			String key = "https://api.auroras.live/v1/?type=images";
			key = key + "&action=" + action;
			
			
			if (noCachingParam != null && noCachingParam.equals("true")){
				
				// We still need to save to cache though. 
				HttpResponse<JsonNode> auroraResponse = Unirest.get(key).asJson();
				JSONObject jsonObject = new JSONObject();
				jsonObject = auroraResponse.getBody().getObject();
				String att = "Powered by Auroras.live";
				jsonObject.put("Attribution", att);
				Response response = Response.status(200).entity(auroraResponse.getBody().toString()).build();
	
				CacheController.getInstance().getCache().setCacheValue(key, response, type);
				// Now response has been created, so return it. 
				return response; 
			}else{
				
				// Check to see if the response is already in the cache
				Response response = CacheController.getInstance().getCache().getFromCacheMap(key);
				if (response == null){
					
					HttpResponse<JsonNode> auroraResponse = Unirest.get(key).asJson();
					JSONObject jsonObject = new JSONObject();
					jsonObject = auroraResponse.getBody().getObject();
					String att = "Powered by Auroras.live";
					jsonObject.put("Attribution", att);
					response = Response.status(200).entity(auroraResponse.getBody().toString()).build();
		
					CacheController.getInstance().getCache().setCacheValue(key, response, type);
					// Now response has been created, so return it. 
					return response; 
				}else{
					// Return fetched response
					return response;
				}
			}
		}
		else{
			return Response.status(400).build();
		}
		
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
