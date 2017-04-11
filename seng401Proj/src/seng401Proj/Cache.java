package seng401Proj;

import java.time.Instant;
import java.util.HashMap;
import javax.ws.rs.core.Response;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.HttpResponse;
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
	
	public Response getFromSpecialHashMap(String key){
		
		if (specialHashMap.get(key) != null){
			System.out.println("In special");
			long currentTime = Instant.now().getEpochSecond();
			if (specialHashMap.get(key).expirationTime < currentTime){
				// Cache has expired, return null 
				incCacheMiss(); 
				System.out.println("Cache Miss = " + cacheMiss);
				return null;
			}else{
				// The Cache has not expired and still exists, so return the response
				incCacheHit();
				System.out.println("Cache Hit = " + cacheHit);
				return specialHashMap.get(key).response;
			}		
		}
		// Cache doesn't exist, return null
		incCacheMiss();
		return null;
	}
	public byte[] getFromSpecialCacheMapImage(String key){
		System.out.println("Key = " + key);
		if (specialHashMap.get(key) != null){
			long currentTime = Instant.now().getEpochSecond();			
			if (specialHashMap.get(key).expirationTime < currentTime){
				// Cache has expired, return null 
				incCacheMiss();
				System.out.println("Cache Miss (Expired)= " + cacheMiss);
				return null;
			}else{
				// The Cache has not expired and still exists, so return the response
				incCacheHit();
				System.out.println("Cache Hit = " + cacheHit);
				return specialHashMap.get(key).imageByteArr;
			}		
		}
		// Cache doesn't exist, return null
		incCacheMiss();
		System.out.println("Cache Miss (Not found) = " + cacheMiss);
		return null;
	}
	
	
	
	public Response getFromCacheMap(String key){
		System.out.println("Key = " + key);
		if (cacheMap.get(key) != null){
			long currentTime = Instant.now().getEpochSecond();			
			if (cacheMap.get(key).expirationTime < currentTime){
				// Cache has expired, return null 
				incCacheMiss();
				System.out.println("Cache Miss (Expired)= " + cacheMiss);
				return null;
			}else{
				// The Cache has not expired and still exists, so return the response
				incCacheHit();
				System.out.println("Cache Hit = " + cacheHit);
				return cacheMap.get(key).response;
			}		
		}
		// Cache doesn't exist, return null
		incCacheMiss();
		System.out.println("Cache Miss (Not found) = " + cacheMiss);
		return null;
	}
	
	public byte[] getFromCacheMapImage(String key){
		System.out.println("Key = " + key);
		if (cacheMap.get(key) != null){
			long currentTime = Instant.now().getEpochSecond();			
			if (cacheMap.get(key).expirationTime < currentTime){
				// Cache has expired, return null 
				incCacheMiss();
				System.out.println("Cache Miss (Expired)= " + cacheMiss);
				return null;
			}else{
				// The Cache has not expired and still exists, so return the response
				incCacheHit();
				System.out.println("Cache Hit = " + cacheHit);
				return cacheMap.get(key).imageByteArr;
			}		
		}
		// Cache doesn't exist, return null
		incCacheMiss();
		System.out.println("Cache Miss (Not found) = " + cacheMiss);
		return null;
	}
	
	public void setSpecialCacheValue(String key, Response response){
		CacheObject obj = new CacheObject(response, 0);
		obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getSpecialTimes().get(key);
    	specialHashMap.put(key, obj);
	}
	public void setSpecialCacheValue(String key, byte[] imageByteArr){
		CacheObject obj = new CacheObject(imageByteArr, 0);
		obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getSpecialTimes().get(key);
    	specialHashMap.put(key, obj);
	}
	
	public void setCacheValue(String key, Response response, String type){
		
		
		CacheObject obj = new CacheObject(response, 0);
				
		switch (type) {
	        case "weather":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getWeatherCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;
	        case "images":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getImagesCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;
	        case "ace":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getAceCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;
	        case "locations":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getLocationsCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;
	        case "all":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getAllCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;
	        case "archive":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getArchiveCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;
	        case "map":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getMapCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;

		
		}
	}
		public void setCacheValue(String key, byte[] imageByteArr, String type){
		
		
		CacheObject obj = new CacheObject(imageByteArr, 0);
		System.out.println("Put in cache");
		switch (type) {
	        case "images":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getImagesCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;
	        case "map":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getMapCachePeriod();
	        	cacheMap.put(key, obj);
	        	break;
	        case "embed":
	        	obj.expirationTime = Instant.now().getEpochSecond() + CacheController.getInstance().getEmbedCachePeriod();
	        	cacheMap.put(key, obj);  	
	        	break;


		
		}
	}	 
}
