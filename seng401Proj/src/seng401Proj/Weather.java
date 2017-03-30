package seng401Proj;




import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo; 
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Weather {
	
	public static Response getWeather(@Context UriInfo info) throws UnirestException{
		
			String restCall = "";
			
			String latitude = info.getQueryParameters().getFirst("lat");
			String longitude = info.getQueryParameters().getFirst("long");
			String forecast = info.getQueryParameters().getFirst("forecast");

			System.out.println("lat = " + latitude);
			System.out.println("long = " + longitude);
			System.out.println("forecast = " + forecast);
			
			JSONObject jsonObject = new JSONObject();
			if (latitude != null && longitude != null){
				restCall = "https://api.auroras.live/v1/?type=weather&lat=" 
						+ latitude + "&long=" + longitude;

			}else{
				//Respond appropriately. 
			}
			
			if(forecast != null){
				if (forecast.equals("true")){		//Even if you put false, it displays forecast
					System.out.println("Here 2");
					restCall = restCall + "&forecast=" + forecast;
				}
			}

			
			System.out.println("Rest Call = " + restCall);
			
			HttpResponse<JsonNode> response = Unirest.get(restCall).asJson();
										
			
            jsonObject = response.getBody().getObject();
			String att = "Powered by Auroras.live";
			jsonObject.put("Attribution", att);
			return Response.status(200).entity(response.getBody().toString()).build();

	}

}
