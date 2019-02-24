package hello;




import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**T his client class is able to send GET request to different routs;
 *
 */
public class HelloClient {

	private static String urlString = "http://localhost:8080/message";

	private static String getUrlString_json = "http://localhost:8080/hello.json";


	public static void main(String[] args) {

		GETRequest();
	}

	public static String GETRequest() {


		 // HttpHeaders
		 HttpHeaders headers = new HttpHeaders();

		 headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));

		 // Request to return JSON format
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.set("header_name", "header_value");

		 // HttpEntity<String>: To get result as String.
		 HttpEntity<String> entity = new HttpEntity<String>(headers);

		 // RestTemplate
		 RestTemplate restTemplate = new RestTemplate();

		 // Send request with GET method, and Headers.
		 ResponseEntity<String> response = restTemplate.exchange(urlString, //
					HttpMethod.GET, entity, String.class);

		 String result = response.getBody();
		 System.out.println("Retrieved " + result + " from the server!");
		 return result;

	}

	/*
	private void POSTRquest(){
	}
	*/

}



