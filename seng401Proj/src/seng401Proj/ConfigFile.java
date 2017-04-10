package seng401Proj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.ws.rs.core.Response;


import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.coders.StringUtf8Coder;
import com.google.cloud.dataflow.sdk.io.TextIO;
import com.google.cloud.dataflow.sdk.options.PipelineOptions;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.transforms.Create;
import com.google.cloud.dataflow.sdk.values.PCollection;

import java.util.ArrayList;
import java.util.List;

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

	    	
	    	
	    	List<String> LINES = Arrays.asList(
	    		      "" + CacheController.getInstance().getAceCachePeriod(),
	    		      "" + CacheController.getInstance().getAllCachePeriod() + "\n",
	    		      "" + CacheController.getInstance().getArchiveCachePeriod() + "\n",
	    		      "" + CacheController.getInstance().getEmbedCachePeriod() + "\n",
	    		      "" + CacheController.getInstance().getImagesCachePeriod() + "\n", 
	    		      "" + CacheController.getInstance().getMapCachePeriod() + "\n",
	    		      "" + CacheController.getInstance().getWeatherCachePeriod() + "\n",
	    		      "" + CacheController.getInstance().getLocationsCachePeriod() + "\n");
	    		     
	    	
	    	 PipelineOptions options = PipelineOptionsFactory.create();
	    	  Pipeline p = Pipeline.create(options);

	    	  PCollection<String> pc1 =  p.apply(Create.of(LINES)).setCoder(StringUtf8Coder.of());
	    	
	    	  pc1.apply(TextIO.Write.named("WriteMyFile")
                    .to("gs://aurora-cache-info/config"));
	    	

	    			
    		String data = "Cache Configurations saved!\n";
    		return Response.status(200).entity(data).build();
	    	
	    }
	    
	    public void getFromConfigFile() {
	    	
	    
	    	
	    	 PipelineOptions options = PipelineOptionsFactory.create();
	    	  Pipeline p = Pipeline.create(options);

	    	  PCollection<String> lines = p.apply(
	    	    TextIO.Read.named("ReadMyFile").from("gs://aurora-cache-info/config.txt"));
	    	
//	    	    String Ace = br.readLine();
//	    	    String All = br.readLine();
//	    	    String Archive = br.readLine();
//	    	    String Embed = br.readLine();
//	    	    String Images = br.readLine();
//	    	    String Map = br.readLine();
//	    	    String Weather = br.readLine();
//	    	    String Locations = br.readLine();
//	    	    
//	    	    Ace = Ace.substring(17);
//	    	    All = All.substring(17);
//	    	    Archive = Archive.substring(21);
//	    	    Embed = Embed.substring(19);
//	    	    Images = Images.substring(20);
//	    	    Map = Map.substring(17);
//	    	    Weather = Weather.substring(21);
//	    	    Locations = Locations.substring(23);
//	    	    
//	    	    CacheController.getInstance().setAceCachePeriod(Long.parseLong(Ace));
//	    	    CacheController.getInstance().setAllCachePeriod(Long.parseLong(All));
//	    	    CacheController.getInstance().setArchiveCachePeriod(Long.parseLong(Archive));
//	    	    CacheController.getInstance().setEmbedCachePeriod(Long.parseLong(Embed));
//	    	    CacheController.getInstance().setImagesCachePeriod(Long.parseLong(Images));
//	    	    CacheController.getInstance().setMapCachePeriod(Long.parseLong(Map));
//	    	    CacheController.getInstance().setWeatherCachePeriod(Long.parseLong(Weather));
//	    	    CacheController.getInstance().setLocationsCachePeriod(Long.parseLong(Locations));
	    	    
	    	
	    }
	    
}
