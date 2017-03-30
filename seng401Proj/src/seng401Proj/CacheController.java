package seng401Proj;

import java.util.HashMap;

public class CacheController {

    private static CacheController instance;
    // Period should be treated as seconds. 0 implies no caching.
    private long aceCachePeriod = 0;
    private long allCachePeriod = 0;
    private long archiveCachePeriod = 0;
    private long embedCachePeriod = 0;
    private long imagesCachePeriod = 0;
    private long mapCachePeriod = 0;
    private long weatherCachePeriod = 0;
    private long locationsCachePeriod = 0;
    private Cache cache;
    private HashMap<String, Long> specialTimes = new HashMap<String, Long>();
    
    private CacheController(){
    	cache = new Cache();
    }
    
    public static synchronized CacheController getInstance(){
        if(instance == null)
        {
            instance = new CacheController();
        }
        return instance;
    }
    
    public void clearCache(){
    	
    }

	public long getAceCachePeriod() {
		return aceCachePeriod;
	}

	public void setAceCachePeriod(long aceCachePeriod) {
		this.aceCachePeriod = aceCachePeriod;
	}

	public long getAllCachePeriod() {
		return allCachePeriod;
	}

	public void setAllCachePeriod(long allCachePeriod) {
		this.allCachePeriod = allCachePeriod;
	}

	public long getArchiveCachePeriod() {
		return archiveCachePeriod;
	}

	public void setArchiveCachePeriod(long archiveCachePeriod) {
		this.archiveCachePeriod = archiveCachePeriod;
	}

	public long getEmbedCachePeriod() {
		return embedCachePeriod;
	}

	public void setEmbedCachePeriod(long embedCachePeriod) {
		this.embedCachePeriod = embedCachePeriod;
	}

	public long getImagesCachePeriod() {
		return imagesCachePeriod;
	}

	public void setImagesCachePeriod(long imagesCachePeriod) {
		this.imagesCachePeriod = imagesCachePeriod;
	}

	public long getMapCachePeriod() {
		return mapCachePeriod;
	}

	public void setMapCachePeriod(long mapCachePeriod) {
		this.mapCachePeriod = mapCachePeriod;
	}

	public long getWeatherCachePeriod() {
		return weatherCachePeriod;
	}

	public void setWeatherCachePeriod(long weatherCachePeriod) {
		this.weatherCachePeriod = weatherCachePeriod;
	}
	
	public long getLocationsCachePeriod() {
		return locationsCachePeriod;
	}

	public void setLocationsCachePeriod(long locationsCachePeriod) {
		this.locationsCachePeriod = locationsCachePeriod;
	}

	public Cache getCache() {
		return cache;
	}

	
    
    
    
}
