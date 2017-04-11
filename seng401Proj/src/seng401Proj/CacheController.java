package seng401Proj;

import java.util.HashMap;

public class CacheController {

    private static CacheController instance;
    // Period should be treated as seconds. 0 implies no caching.
    private long aceCachePeriod = 6000;
    private long allCachePeriod = 6000;
    private long archiveCachePeriod = 12000;
    private long embedCachePeriod = 12000;
    private long imagesCachePeriod = 12000;
    private long mapCachePeriod = 12000;
    private long weatherCachePeriod = 6000;
    private long locationsCachePeriod = 6000;
    private Cache cache;
    private HashMap<String, Long> specialTimes = new HashMap<String, Long>();
    
    private CacheController(){
    	//cache = new Cache();
    }
    private void initSpecialTimes(){
    	specialTimes.put("https://api.auroras.live/v1/?type=ace&data=kp", Long.valueOf(200));
    }
    
    public static synchronized CacheController getInstance(){
        if(instance == null)
        {
        	System.out.println("Making new instance");
            instance = new CacheController();
            instance.cache = new Cache();
            instance.initSpecialTimes();
        }
        return instance;
    }
    public HashMap<String, Long> getSpecialTimes(){
    	return specialTimes;
    }
    
    public void clearCache(){
    	cache.cacheClear();
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
