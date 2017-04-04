package seng401Proj;

import java.time.Instant;
import java.util.HashMap;
import javax.ws.rs.core.Response;


public class Cache {

	
	HashMap<String, CacheObject> specialHashMap = new HashMap<String, CacheObject>();
	
	HashMap<String, CacheObject> cacheMap = new HashMap<String, CacheObject>();
	
	public int cacheHit = 0;
	public int cacheMiss = 0;
	
	public void cacheClear(){
		specialHashMap.clear();	
		cacheMap.clear();
		cacheHit = 0;
		cacheMiss = 0;
	}
	private void incCacheHit(){
		cacheHit++;
	}
	private void incCacheMiss(){
		cacheMiss++;
	}
	
	public Response getFromSpecialHashMap(String key, long expiration){
		
		if (specialHashMap.get(key) != null){
			long currentTime = Instant.now().getEpochSecond();
			if (specialHashMap.get(key).expirationTime < currentTime){
				// Cache has expired, return null 
				incCacheMiss(); 
				return null;
			}else{
				// The Cache has not expired and still exists, so return the response
				incCacheHit();
				return specialHashMap.get(key).response;
			}		
		}
		// Cache doesn't exist, return null
		incCacheMiss();
		return null;
	}
	public Response getFromCacheMap(String key){
		if (cacheMap.get(key) != null){
			long currentTime = Instant.now().getEpochSecond();
			if (cacheMap.get(key).expirationTime < currentTime){
				// Cache has expired, return null 
				incCacheMiss();
				return null;
			}else{
				// The Cache has not expired and still exists, so return the response
				incCacheHit();
				return cacheMap.get(key).response;
			}		
		}
		// Cache doesn't exist, return null
		incCacheMiss();
		return null;
	}
	
	public void setSpecialCacheValue(String key, Response response, String type){
			
	}
	
	public void setCacheValue(String key, Response response, String type){
		
		
		CacheObject obj = new CacheObject(response, 0);
				
		switch (type) {
	        case "weather":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getWeatherCachePeriod();
	        	cacheMap.put(key, obj);
	        case "images":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getImagesCachePeriod();
	        	cacheMap.put(key, obj);
	        case "ace":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getAceCachePeriod();
	        	cacheMap.put(key, obj);
	        case "locations":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getLocationsCachePeriod();
	        	cacheMap.put(key, obj);
	        case "all":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getAllCachePeriod();
	        	cacheMap.put(key, obj);
	        case "archive":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getArchiveCachePeriod();
	        	cacheMap.put(key, obj);
	        case "map":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getMapCachePeriod();
	        	cacheMap.put(key, obj);
	        case "embed":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getEmbedCachePeriod();
	        	cacheMap.put(key, obj);  	

		
		}
	}
	
	
	
	
	
}
