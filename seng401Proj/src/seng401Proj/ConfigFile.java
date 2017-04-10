package seng401Proj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

import javax.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigFile {
	private static final String FILENAME = "config.txt";
	
	 private static ConfigFile instance;
	    // Period should be treated as seconds. 0 implies no caching.
	 
	    
	    private ConfigFile() {
	    	
	    }
	    
	    public static synchronized ConfigFile getInstance(){
	        if(instance == null)
	        {
	            instance = new ConfigFile();
	        }
	        return instance;
	    }
	    
	    public Response saveToConfigFile() {

    		BufferedWriter bw = null;
    		FileWriter fw = null;
    		String content = "";
    		
	    	try {
	    		content = content + "AceCachePeriod: " + CacheController.getInstance().getAceCachePeriod() + "\n";
	    		content = content + "AllCachePeriod: " + CacheController.getInstance().getAllCachePeriod() + "\n";
	    		content = content + "ArchiveCachePeriod: " + CacheController.getInstance().getArchiveCachePeriod() + "\n";
	    		content = content + "EmbedCachePeriod: " + CacheController.getInstance().getEmbedCachePeriod() + "\n";
	    		content = content + "ImagesCachePeriod: " + CacheController.getInstance().getImagesCachePeriod() + "\n";
	    		content = content + "MapCachePeriod: " + CacheController.getInstance().getMapCachePeriod() + "\n";
	    		content = content + "WeatherCachePeriod: " + CacheController.getInstance().getWeatherCachePeriod() + "\n";
	    		content = content + "LocationsCachePeriod: " + CacheController.getInstance().getLocationsCachePeriod() + "\n";
	    		
	    		fw = new FileWriter(FILENAME);
    			bw = new BufferedWriter(fw);
    			bw.write(content);
    			
    			
	    	} catch (IOException e) {
	    			e.printStackTrace();
	    	} finally {
	    		try {
	    			if (bw != null)
    					bw.close();
	    			if (fw != null)
    					fw.close();
	    			
	    			String data = "Cache Configurations saved!\n" + content;
	    			return Response.status(200).entity(data).build();
	    		} catch (IOException ex) {
	    				ex.printStackTrace();
	    		}
	    	}
	    	System.out.println("Error 500");
			return Response.status(500).build();
	    }
	    
	    public void getFromConfigFile() {
	    	
	    	BufferedReader br = null;
	    	
	    	try {
	    		br = new BufferedReader(new FileReader(FILENAME));
	    	
	    	    String Ace = br.readLine();
	    	    String All = br.readLine();
	    	    String Archive = br.readLine();
	    	    String Embed = br.readLine();
	    	    String Images = br.readLine();
	    	    String Map = br.readLine();
	    	    String Weather = br.readLine();
	    	    String Locations = br.readLine();
	    	    
	    	    Ace = Ace.substring(17);
	    	    All = All.substring(17);
	    	    Archive = Archive.substring(21);
	    	    Embed = Embed.substring(19);
	    	    Images = Images.substring(20);
	    	    Map = Map.substring(17);
	    	    Weather = Weather.substring(21);
	    	    Locations = Locations.substring(23);
	    	    
	    	    CacheController.getInstance().setAceCachePeriod(Long.parseLong(Ace));
	    	    CacheController.getInstance().setAllCachePeriod(Long.parseLong(All));
	    	    CacheController.getInstance().setArchiveCachePeriod(Long.parseLong(Archive));
	    	    CacheController.getInstance().setEmbedCachePeriod(Long.parseLong(Embed));
	    	    CacheController.getInstance().setImagesCachePeriod(Long.parseLong(Images));
	    	    CacheController.getInstance().setMapCachePeriod(Long.parseLong(Map));
	    	    CacheController.getInstance().setWeatherCachePeriod(Long.parseLong(Weather));
	    	    CacheController.getInstance().setLocationsCachePeriod(Long.parseLong(Locations));
	    	    
	    	} catch(FileNotFoundException e) {
	    		saveToConfigFile();
	    	} catch (IOException e) {
    			e.printStackTrace();
	    	} finally {
	    		try {
	    			if (br != null)
    					br.close();
	    		} catch (IOException ex) {
	    				ex.printStackTrace();
	    		}
	    	}
	    }
	    
}
