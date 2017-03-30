package seng401Proj;

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
	
	public static Response getEmbed(@Context UriInfo info)throws JSONException, UnirestException
	{
		// Required Params: Image: current, weather, Lat, Long. Lat long only required if Image = weather
		//?type=embed&image=weather&lat=40.7813913&long=-73.976902
		
		String url = "https://api.auroras.live/v1/?type=embed";
		if (info.getQueryParameters().getFirst("image").matches("current")){
			
			url = url + "&image=current";
			
			HttpResponse<java.io.InputStream> response = Unirest.get(url).asBinary();
			return Response.status(200).entity(response.getBody()).type("image/png").build();
				
			
		 
		}
		
		
		if (info.getQueryParameters().getFirst("image").matches("weather")){
			// if lat [-90,90] and long [-180,180] and not null.
			
			if (info.getQueryParameters().getFirst("lat") != null && (info.getQueryParameters().getFirst("long")!= null))
			{	
				float latitude = Float.valueOf(info.getQueryParameters().getFirst("lat"));
				float longitude = Float.valueOf(info.getQueryParameters().getFirst("long"));
				if((latitude >= -90 && latitude <= 90) && (longitude >= -180 && longitude <= 180)){	
					url = url + "&image="+ info.getQueryParameters().getFirst("image") + "&lat=" + info.getQueryParameters().getFirst("lat") + "&long=" + info.getQueryParameters().getFirst("long");
					HttpResponse<java.io.InputStream> response = Unirest.get(url).asBinary();
					return Response.status(200).entity(response.getBody()).type("image/png").build();
				}
			}
			else{
				return Response.status(404).build();
			}
		}
		
		
		
		return Response.status(404).build();

	}

}