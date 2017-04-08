package seng401Proj;

import javax.ws.rs.core.Response;

import com.mashape.unirest.http.HttpResponse;

public class CacheObject {

	Response response;
	long expirationTime;
	byte[] imageByteArr;
	
	CacheObject(Response response, long expirationTime){
		this.response = response;
		this.expirationTime = expirationTime;
	}
	
	CacheObject(byte[] imageByteArr , long expirationTime){
		this.imageByteArr = imageByteArr;
		this.expirationTime = expirationTime;
	}
}
