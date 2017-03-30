package seng401Proj;

import javax.ws.rs.core.Response;

public class CacheObject {

	Response response;
	long expirationTime;
	CacheObject(Response response, long expirationTime){
		this.response = response;
		this.expirationTime = expirationTime;
	}
	
}
