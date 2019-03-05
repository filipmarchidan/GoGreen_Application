package API;




import API.messages.Activities;
import API.messages.Activity;
import API.messages.LogInRequest;
import API.messages.Message;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
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

	public ClientApplication(String serverAdress) {

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

	public String getRequest(String endpoint,Object obj) {

		String json = gson.toJson(obj);
		HttpEntity<String> request = new HttpEntity<String>(json,headers);

		// Send request with GET method, and Headers.
		ResponseEntity<String> response = restTemplate.exchange(address + endpoint,HttpMethod.GET, request, String.class);
		String result = response.getBody();

		return result;
	}

	public String postRequest(String endpoint, Object obj) {

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

	/** Mockup that sends log in info to server, to be replaced.
	 *
	 * @param email email of user
	 * @param pass password of user
	 */
	public void logIn(String email,String pass) {


		LogInRequest logInRequest = new LogInRequest(email,pass);

		String result = postRequest("/login",logInRequest);
		System.out.println(result);
	}

	/** Adds an Activity to the Server.
	 *
	 * @param activity Activity to be added
	 * @param date Date of the activity to be added.
	 */
	public void addActivity(Activities activity, Date date) {

		Activity act = new Activity(activity,date);
		String result = postRequest("/addactivity",activity);
		Message message = gson.fromJson(result,Message.class);
		System.out.println(message.getContent());
	}

	/** Method that requests the leaderboard from the server.
	 *
	 */
	public void seeLeaderboard() {
		System.out.println("fetching leaderboard from server");

		String result = getRequest("/leaderboard",null);
		Message message = gson.fromJson(result,Message.class);
		System.out.println(message.getContent());
	}
	
	

}



