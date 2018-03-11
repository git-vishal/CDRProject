
// change to your respective package name....
package httpsConnection;

// imports
import java.net.MalformedURLException;
import java.net.URL;
//import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLPeerUnverifiedException;

/*
 * 
 * 
 * ***** ABOUT ****
 * Aim of this program is 
 * 1. to receive longitude & latitude values as input
 * 2. open Google API url for Reverse geocoding via Https connection using Google API Key
 * 3. get its output (Output is being printed in screen here)
 * 
 * This program will be merged with rest of pieces of code for overall CDR project
 * 
 * 
 * 
 * **** Input / Output ****
 * Input to Google API URL >> Longitude & Latitude values of any location [hard-coded in this example]
 * Output from Google API URL >> received in JASON format >> printed in output console
 *  
 * 
 * 
 * 
 * ***** Methods defined by us in this program ****
 * openConn ---> contains logic to open https connection
 * printURLContent --> contains logic to fetch content from https URL in JASON format and print on screen
 * 
 * 
 * **** Existing Java Classes & Methods used ****
 * 
 * Class: 	HttpsURLConnection --> supports https specific functions
 * Method:	openConnection --> used to open connection of URL
 * 
 * Class: 	URLConnection --> supports communication link between Java application & URL
 * Method:	getinputStream --> this gets data from URL as a stream
 *  
 *  
 * 
 */

public class HttpsClient2 {

	public static void main(String[] args) {
		new HttpsClient2().openConn(); // calling openConn method of HttpsClient2 class
	}

	private void openConn() {

		// temporarily assigning single latitude & longitude value (hard-coding input
		// values)
		String https_url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=AIzaSyBcybkxACtUoQFUjjDMdzjpoluWNL-Z3co";

		URL url;

		try {

			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection(); // open URL connection

			printURLContent(con); // get data from https URL and print it..

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void printURLContent(HttpsURLConnection con) {

		if (con != null) {

			try {

				// get data from URL and store in br object of BufferedReader class
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				// to print output to screen
				String input;
				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close(); // close reader

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
