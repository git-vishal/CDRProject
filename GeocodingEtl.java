package com.bigdata.etl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class GeocodingEtl
{

	public String openConn(String lat, String lng) throws MalformedURLException, IOException,UnknownHostException
	{

		String https_url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ lat + "," + lng + "&key=AIzaSyBcybkxACtUoQFUjjDMdzjpoluWNL-Z3co";
		URL url;
		String formattedAddress = "";
		String input = "";		
		url = new URL(https_url);
 		HttpsURLConnection con = (HttpsURLConnection) url.openConnection(); // open URL connection
		
		//formattedAddress = printURLContent(con); // get data from https URL and print it..
			if (con != null)
			{
				try
				{
					// get data from URL and store in br object of BufferedReader class
					InputStream in = url.openStream();
					BufferedReader br = new BufferedReader(new InputStreamReader((in)));
					String line = br.readLine();
					input = line;
					// to print output to screen				
					while ((line = br.readLine()) != null) 
					{
						input+=line;
					}
					//br.close(); // close reader
  					JSONParser parser = new JSONParser();
		            //if(parser!= null);
		            JSONObject rsp = (JSONObject) parser.parse(input);	
		        	//System.out.println(input);
		            if (rsp.containsKey("status")) 
		            {	            
		            	if (rsp.containsValue("OK"))
		            	{
		                JSONArray matches = (JSONArray) rsp.get("results");		                
		                JSONObject data = (JSONObject) matches.get(3); 	 
		                JSONArray matches1 = (JSONArray) data.get("address_components");		                
		                JSONObject data1 = (JSONObject) matches1.get(3); 
		                JSONObject data2 = (JSONObject) matches1.get(4);
		                //formattedAddress = (String) data.get("formatted_address");
		                String cm = ",";
		               formattedAddress = (String) data1.get("short_name")+cm+data2.get("short_name");           
		               		                
		                //formattedAddress = s+(String) data1.get("State - "+"short_name")+c+data2.get("country- "+"short_name");
		                //System.out.println("Address for given co-ordinates is ->"+formattedAddress);
		            	}
		            	else
		            	{
		             		formattedAddress = (String) rsp.get("error_message");
		            		//JSONArray matches = (JSONArray) rsp.get("error_message");		                
			                //JSONObject data = (JSONObject) matches.get(0); 	                
			                //formattedAddress = (String) data.get("formatted_address");
		            	}
		             }
		            else if (rsp.containsKey("error_message"))
		            {
		                JSONArray matches = (JSONArray) rsp.get("error_message");
		                JSONObject data = (JSONObject) matches.get(0); //TODO: check if idx=0 exists
		                formattedAddress = (String) data.get("error_message");
		                //System.out.println("Address for given co-ordinates is ->"+formattedAddress);
		            }	            
		            else System.out.println("wrong call");					
				  }
				 catch(IOException|NullPointerException|org.json.simple.parser.ParseException|IndexOutOfBoundsException e )
		        {
		        	String ff = e.getMessage();
		        	System.out.println(ff);		        	
		        }		        
		        finally 
		        {
		        	Boolean t = con.getAllowUserInteraction();
		        	if (t = true)
		        	{
		        		con.disconnect();
		        		//br.close(); 
		        	}
		        	else
		        		formattedAddress = "No connection available check internet";
		        }				
			}
			else
			{
				formattedAddress = "No connection available check internet";				
			}
			return formattedAddress;	
		}	
	 public static void main(String[] args)throws MalformedURLException,NullPointerException,IOException, ParseException,UnknownHostException
	{
		GeocodingEtl msg = new GeocodingEtl();
		//openConn(); // calling openConn method of GeocodingEtl class
		String fm = msg.openConn("40.714224","-73.961452");	
		//String fm = msg.openConn("40.714224","-73.961452");
		System.out.println(fm); 
	}
}
