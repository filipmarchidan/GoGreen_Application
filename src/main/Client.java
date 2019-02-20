import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class Client {

    private static String urlString = "https://google.com/"; // https://84.104.121.142:3000/pizza

    public static void main(String[] args) throws IOException {

        // create connection
        HttpsURLConnection con = (HttpsURLConnection) createConnection(urlString);

        // read and print response
        getFullResponse(con);
    }

    // creates a connection object, but does not establish the connection yet
    private static HttpsURLConnection createConnection(String urlString) throws IOException {

        try {
            URL url = new URL(urlString);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
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

    public static void getFullResponse(HttpsURLConnection con) {

        // print https certificate
        if (con != null) {

            try {

                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for (Certificate cert : certs) {
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
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
