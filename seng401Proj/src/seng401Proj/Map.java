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

public class Map {

	public static Response getMap(@Context UriInfo info) throws UnirestException{
		
		
		String id = info.getQueryParameters().getFirst("id");
		 String description = null;
		 String latitude = null;
		 String longitude = null;
		
		JSONArray jsonArray = new JSONArray();
		HttpResponse<JsonNode> locationsResponse = Unirest.get("https://api.auroras.live/v1/?type=locations").asJson();
		System.out.println(locationsResponse.getBody().getArray());
		jsonArray = locationsResponse.getBody().getArray();
		if(id == null || id.equals("")){
			return Response.status(404).build();
		}
		for(int i = 0; i < jsonArray.length(); i++){
			
			if (jsonArray.getJSONObject(i).get("id").equals(id)){
				description = jsonArray.getJSONObject(i).getString("description");
				latitude = jsonArray.getJSONObject(i).getString("lat");
				longitude = jsonArray.getJSONObject(i).getString("long");
				break;
			}
			
		}
		if (description == null){
			//Also return a 404 response, because their id was not valid (no matches found)
			return Response.status(404).build();

		}
		description = description.replaceAll("\\s+","");
	
		String googleMapsBase = "https://maps.googleapis.com/maps/api/staticmap?";
		String center = "center=" + description;
		String mapsize = "&zoom=10&size=400x400";
		String marker = "&markers=color:red%7C" + latitude + "," + longitude;
		String googleAPIKey = "&key=AIzaSyBbRMDcRJxulPRPVnPtbJYEvXv18CD3mco";
		String googleMapsCall = googleMapsBase + center + mapsize + marker + googleAPIKey;
		
		HttpResponse<java.io.InputStream> mapsResponse = Unirest.get(googleMapsCall).asBinary();
		return Response.status(200).entity(mapsResponse.getBody()).type("image/png").build();
	}
}
