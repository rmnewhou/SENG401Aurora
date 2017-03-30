package seng401Proj;

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

public class Locations {
	public static Response getLocations(@Context UriInfo info)throws JSONException, UnirestException{
			System.out.println(info.getQueryParameters().getFirst("type"));
			JSONArray jsonArray = new JSONArray();
			HttpResponse<JsonNode> response = Unirest.get("https://api.auroras.live/v1/?type=locations").asJson();
			System.out.println(response.getBody().getArray());
			jsonArray = response.getBody().getArray();
			String att = "Powered by Auroras.live";
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Attribution", att);
			jsonArray.put(jsonObject);
			return Response.status(200).entity(response.getBody().toString()).build();

		}
	}

