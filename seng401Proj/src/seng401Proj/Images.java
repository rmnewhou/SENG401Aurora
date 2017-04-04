package seng401Proj;



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

	public static Response getImages(@Context UriInfo info) throws UnirestException{

		String action = info.getQueryParameters().getFirst("action");
		String image = info.getQueryParameters().getFirst("image");
		
		String type = info.getQueryParameters().getFirst("type");
		String noCachingParam = info.getQueryParameters().getFirst("no-caching");
		
		
	
			
			
		if (action!=null && action.equals("list"))
		{
			getList(info, type, noCachingParam);
		}
		
				
		if (image != null)
		{
			getImage(info, type, noCachingParam);
		}
		
		return Response.status(400).build();
		
		
		
	}
	
	private static Response getImage(@Context UriInfo info, String type, String noCachingParam) throws UnirestException{
		String image = info.getQueryParameters().getFirst("image");
		JSONArray jsonArray = new JSONArray();
		HttpResponse<JsonNode> locationsResponse = Unirest.get("https://api.auroras.live/v1/?type=locations").asJson();
		System.out.println(locationsResponse.getBody().getArray());
		jsonArray = locationsResponse.getBody().getArray();
		
		
		
		for(int i = 0; i < jsonArray.length(); i++){
			
			if (jsonArray.getJSONObject(i).get("id").equals(image)){
				
				String key = "https://api.auroras.live/v1/?type=images";
				key = key + "&image=" + image;
				
				if (noCachingParam != null && noCachingParam.equals("true")){
					
					// We still need to save to cache though. 
					HttpResponse<java.io.InputStream> response = Unirest.get(key).asBinary();
					
					
					Response newResponse = Response.status(200).entity(response.getBody()).type("image/png").build();
					
					// Save the response in the cache controller. 
					CacheController.getInstance().getCache().setCacheValue(key, newResponse, type);
					return newResponse;
				}else{
					
					// Check to see if the response is already in the cache
					Response response = CacheController.getInstance().getCache().getFromCacheMap(key);
					if (response == null){
						
						HttpResponse<java.io.InputStream> auroraResponse = Unirest.get(key).asBinary();
						
						
						Response newResponse = Response.status(200).entity(auroraResponse.getBody()).type("image/png").build();
						
						// Save the response in the cache controller. 
						CacheController.getInstance().getCache().setCacheValue(key, newResponse, type);
						return newResponse;
					}else{
						// Response
						return response;
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
	
	}
