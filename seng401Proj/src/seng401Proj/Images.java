package seng401Aurora;



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
		
		System.out.println("action " + action);
		System.out.println("image " + image);
		
		

		
		String restCall = "https://api.auroras.live/v1/?type=images";
		if (action!=null && action.equals("list"))
		{
			getList(info);
		}
		
				
		if (image != null)
		{
			getImage(info);
		}
		
		return Response.status(400).build();
		
	}
	
	private static Response getImage(@Context UriInfo info) throws UnirestException{
		String image = info.getQueryParameters().getFirst("image");
		JSONArray jsonArray = new JSONArray();
		HttpResponse<JsonNode> locationsResponse = Unirest.get("https://api.auroras.live/v1/?type=locations").asJson();
		System.out.println(locationsResponse.getBody().getArray());
		jsonArray = locationsResponse.getBody().getArray();
		
		
		
		for(int i = 0; i < jsonArray.length(); i++){
			
			if (jsonArray.getJSONObject(i).get("id").equals(image)){
				
				String restCall = "https://api.auroras.live/v1/?type=images";
				restCall = restCall + "&image=" + image;
				HttpResponse<java.io.InputStream> response = Unirest.get(restCall).asBinary();
				return Response.status(200).entity(response.getBody()).type("image/png").build();
				
			}
		}
		return Response.status(400).build();


	}
	private static Response getList(@Context UriInfo info) throws UnirestException{
		String action = info.getQueryParameters().getFirst("action");
		if (action.equals("list")){	
			System.out.println("Got in the action place");
			String restCall = "https://api.auroras.live/v1/?type=images";
			restCall = restCall + "&action=" + action;
			
			HttpResponse<JsonNode> response = Unirest.get(restCall).asJson();
			JSONObject jsonObject = new JSONObject();
			jsonObject = response.getBody().getObject();
			String att = "Powered by Auroras.live";
			jsonObject.put("Attribution", att);
			return Response.status(200).entity(response.getBody().toString()).build();
		}
		else{
		return Response.status(400).build();
		}
		
	}
	
	}
