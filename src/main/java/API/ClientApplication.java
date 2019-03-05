package API;




import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

/**T his client class is able to send GET request to different routs;
 *
 */
public class ClientApplication {
	Gson gson;
	private  String address;
	private RestTemplate restTemplate;
	private HttpHeaders headers;

	private static String getUrlString_json = "http://localhost:8080/API.json";

	public ClientApplication(String serverAdress){

		gson = new Gson();
		//instantiate the variables, template and headers.
		address = serverAdress;
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();


		headers.add("Content-Type","application/json");
		headers.add("Accept", "*/*");

		// Request to return JSON format
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));


	}

	public String getRequest(String endpoint,Object obj)
	{

		String json = gson.toJson(obj);
		HttpEntity<String> request = new HttpEntity<String>(json,headers);

		// Send request with GET method, and Headers.
		ResponseEntity<String> response = restTemplate.exchange(address + endpoint,HttpMethod.GET, request, String.class);
		String result = response.getBody();

		return result;
	}

	public String postRequest(String endpoint, Object obj){

		String json = gson.toJson(obj);
		HttpEntity<String> request = new HttpEntity<String>(json,headers);

		// Send request with POST method, and Headers.
		ResponseEntity<String> response = restTemplate.exchange(address + endpoint,HttpMethod.POST, request, String.class);
		String result = response.getBody();

		return result;
	}

	
	public static void main(String[] args) {
		
		ClientApplication client = new ClientApplication("http://localhost:8080/");
		
		System.out.println(client.getRequest("all", null));
		System.out.println(client.postRequest("add", new User("asjhfv", "skldfgfja")));
		System.out.println(client.getRequest("all", null));
		
		
	}
	
	

}



