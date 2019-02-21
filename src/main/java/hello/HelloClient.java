package hello;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.cert.Certificate;


public class HelloClient {

	 private static String urlString = "http://localhost:8080/hello";
	 private static String name = "World";

	 public static void main(String[] args) throws IOException {

	 	 addNameToUrl(urlString, name);
	 	 HttpURLConnection con = requestToUrl(urlString);

	 	 getFullResponse(con);
	 }


	 private static HttpURLConnection requestToUrl(String urlString) throws IOException  {


	 	try{
	 	 URL url = new URL(urlString);

	 	 HttpURLConnection con = (HttpURLConnection)url.openConnection();
	 	 con.setRequestMethod("GET");
	 	 return con;

	 } catch (MalformedURLException e) {
		  e.printStackTrace();
		  throw new MalformedURLException();
	 } catch (IOException e) {
		  e.printStackTrace();
		  throw new IOException();
	 }

	 }



	 private static void addNameToUrl(String urlString, String name) {

	 	 urlString += "?name=" + name;
	 	 System.out.println(urlString);

	 }


	 private static void getFullResponse(HttpURLConnection con) {

		  // print https certificate
		  if (con != null) {

				try {

					 System.out.println("Response Code : " + con.getResponseCode());

					 System.out.println("\n");
				} catch (IOException e) {
					 e.printStackTrace();
				}

				// print the content of the URL
				try {

					 System.out.println("****** Content of the URL ********");
					 BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

					 String input;

					 while ((input = br.readLine()) != null) {
						  System.out.println(input);
					 }
					 br.close();

				} catch (IOException e) {
					 e.printStackTrace();
				}

		  }

	 }

	 }

