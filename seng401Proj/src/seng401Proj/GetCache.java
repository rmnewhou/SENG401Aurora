package seng401Proj;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo; 
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

//return the current cached content for each section
public class GetCache {
	public static Response getCacheContents(@Context UriInfo info) {
		
String section = info.getQueryParameters().getFirst("section");
//key is the url that we send to the server
String key = "";

String data = "";
String latitude = "";
String longitude = "";
String ace = "";
String archive = "";
String forecast = "";
String images = "";
String probability = "";
String threeday = "";
String twentysevenday = "";
String weather = "";
String action = "";
String startDate = "";
String endDate = "";

Response response = null;

		switch (section) {
			case "Ace":
				data = info.getQueryParameters().getFirst("data");
				latitude = info.getQueryParameters().getFirst("lat");
				longitude = info.getQueryParameters().getFirst("long");
				key = "https://api.auroras.live/v1/?type=ace&data="+data+"&lat="+latitude+"&long="+longitude;
				
				response = CacheController.getInstance().getCache().getFromCacheMap(key);
				return response;
			
			case "All":
				latitude = info.getQueryParameters().getFirst("lat");
			    longitude = info.getQueryParameters().getFirst("long");
			   	ace = info.getQueryParameters().getFirst("ace");
				archive = info.getQueryParameters().getFirst("archive");
				forecast = info.getQueryParameters().getFirst("forecast");
				images = info.getQueryParameters().getFirst("images");
				probability = info.getQueryParameters().getFirst("probability");
				threeday = info.getQueryParameters().getFirst("threeday");
				twentysevenday = info.getQueryParameters().getFirst("twentysevenday");
				weather = info.getQueryParameters().getFirst("weather");

			    key = "http://api.auroras.live/v1/?type=all&lat="+latitude+"&long="+longitude;

			    if (ace != null){
			        key += "&ace="+ace;
			    }
			    if (archive != null){
			        key += "&archive="+archive;
			    }
			    if (forecast != null){
			        key += "&forecast="+forecast;
			    }
			    if (images != null){
			        key += "&images="+images;
			    }
			    if (probability != null){
			        key += "&probability="+probability;
			    }
			    if (threeday != null){
			        key += "&threeday="+threeday;
			    }
			    if (twentysevenday != null){
			        key += "&twentysevenday="+twentysevenday;
			    }
			    if (weather != null){
			        key += "&weather="+weather;
			    }
				
				response = CacheController.getInstance().getCache().getFromCacheMap(key);
				return response;
				
			case "Archive":
				action = info.getQueryParameters().getFirst("action");
				startDate = info.getQueryParameters().getFirst("start");
				endDate = info.getQueryParameters().getFirst("end");
				    
				key = "http://api.auroras.live/v1/?type=archive&action="+action;
				
				if (startDate!=null&&endDate!=null){
					key += "&start="+startDate +"&end="+endDate;
				    key = key.replaceAll(" ", "");
				    key = key.replaceAll("%20", "");
				}
				
				response = CacheController.getInstance().getCache().getFromCacheMap(key);
				return response;
				
			case "Embed":
				key = "https://api.auroras.live/v1/?type=embed";
				if (info.getQueryParameters().getFirst("image").matches("current")){
					
					key = key + "&image=current";
					
					response = CacheController.getInstance().getCache().getFromCacheMap(key);
					return response;
				}
				
				
				if (info.getQueryParameters().getFirst("image").matches("weather")){
					// if lat [-90,90] and long [-180,180] and not null.
					
					if (info.getQueryParameters().getFirst("lat") != null && (info.getQueryParameters().getFirst("long")!= null))
					{	
						latitude = info.getQueryParameters().getFirst("lat");
						longitude = info.getQueryParameters().getFirst("long");
						if((Float.valueOf(latitude) >= -90 && Float.valueOf(latitude) <= 90) && (Float.valueOf(longitude) >= -180 && Float.valueOf(longitude) <= 180)){	
							key = key + "&image="+ info.getQueryParameters().getFirst("image") + "&lat=" + info.getQueryParameters().getFirst("lat") + "&long=" + info.getQueryParameters().getFirst("long");
							response = CacheController.getInstance().getCache().getFromCacheMap(key);
							return response;
						}
					}
					else{
						return null;
					}
				}
				
				response = CacheController.getInstance().getCache().getFromCacheMap(key);
				return response;
				
			case "Images":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
				
			case "Map":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
				
			case "Weather":
				CacheController.getInstance();
				
				return GetCache.getCacheContents(info);
				
			case "Locations":
				
				return GetCache.getCacheContents(info);
				
			default:
        	//Should be 400
				return Response.status(400).build();

		}

		
		return null;
	}
}
