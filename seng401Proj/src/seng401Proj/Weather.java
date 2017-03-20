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
		
			String latitude = info.getQueryParameters().getFirst("lat");
			String longitude = info.getQueryParameters().getFirst("long");
			String forecast = info.getQueryParameters().getFirst("forecast");

			
			JSONObject jsonObject = new JSONObject();
			
			String restCall = "https://api.auroras.live/v1/?type=weather&lat=" 
					+ latitude + "&long=" + longitude;
			
			if (forecast.equals("true")){		//Even if you put false, it displays forecast
				restCall = restCall + "&forecast=" + forecast;
			}
			
			HttpResponse<JsonNode> response = Unirest.get(restCall).asJson();
										
			
            jsonObject = response.getBody().getObject();
			String att = "Powered by Auroras.live";
			jsonObject.put("Attribution", att);
			return Response.status(200).entity(response.getBody().toString()).build();

	}

}
