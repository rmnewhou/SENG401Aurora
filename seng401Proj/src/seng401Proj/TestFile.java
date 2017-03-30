package seng401Proj;




import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam; 
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException; 
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;



@Path("/")
public class TestFile {
	@Path("hello")
	@GET
	@Produces("application/json")
	public Response helloWorld() throws JSONException {
		JSONObject jsonObject = new JSONObject(); jsonObject.put("Message", "Hello World!");
		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	} 

	@Path("user={user}")
	@GET
	@Produces("application/json")
	public Response helloUser(@PathParam("user") String userName) throws JSONException {
		JSONObject jsonObject = new JSONObject(); jsonObject.put("Message", "Hello " + userName + "!");
		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	}
	
	@Path("getACE")
	@GET
	@Produces("application/json")
	public Response getACE() throws JSONException, UnirestException {
		System.out.println("\n\nIn getAce");
		JSONObject jsonObject = new JSONObject();
		HttpResponse<JsonNode> response = Unirest.get("http://api.auroras.live/v1/?type=ace&data=kp")
		                               .header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0")
		                               .asJson();
		               jsonObject = response.getBody().getObject();
		String att = "Powered by Auroras.live";
		jsonObject.put("Attribution", att);
		return Response.status(200).entity(response.getBody().toString()).build();
	}
	/*
	@Path("getWeather")
	@GET
	@Produces("application/json")
	public Response getWeather() throws JSONException, UnirestException {
		System.out.println("\n\nIn getWeather");
		JSONObject jsonObject = new JSONObject();
		HttpResponse<JsonNode> response = Unirest.get("https://api.auroras.live/v1/?type=weather&lat=40.7813913&long=-73.976902")
										.header("cookie", "PHPSESSID=MW2MMg7reEHx0vQPXaKen0")
										.asJson();
		               jsonObject = response.getBody().getObject();
		String att = "Powered by Auroras.live";
		jsonObject.put("Attribution", att);
		return Response.status(200).entity(response.getBody().toString()).build();
	}*/
	
	@Path("/")
	@GET
	@Produces({"application/json","images/png"})
	public Response getType(@Context UriInfo info) throws JSONException, UnirestException {

		// Get the type we will be working with. 
		String type = info.getQueryParameters().getFirst("type");
		
		System.out.println("\n\nType = " + type);
		
		System.out.println("\n\nParameters = " + info.getQueryParameters());
		
		System.out.println("Get path: " + info.getPath());
		
		

		switch (type) {
	        case "weather": 	
	        	return Weather.getWeather(info);
	        case "images":
	        	return Images.getImages(info);
	        default:
	        	JSONObject jsonObject = new JSONObject();
	        	jsonObject.put("Message", "Hi!");
				String result = jsonObject.toString();
				return Response.status(200).entity(result).build();
		}
	}

}
