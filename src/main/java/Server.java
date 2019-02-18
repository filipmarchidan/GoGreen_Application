import com.sun.net.httpserver.*;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyStore;

public class Server{

    public static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException{
            System.out.println("attempted connection!");
            String response = "hi, i see you like pizza!";
            t.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream OS = t.getResponseBody();
            OS.write(response.getBytes());
            OS.close();
        }
    }
    static class ThyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static void main(String[] args)
    {
        runHTTP();
        runHTTPS(args[0]);
        System.out.println("Succesfully started servers!");

    }
    private static void runHTTP(){

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/test", new ThyHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println("Failed to create HTTPS server on port " + 8000 + " of localhost");
        }
    }
    private static void runHTTPS(String pass){
        try {
            InetSocketAddress ia = new InetSocketAddress(3000);
            HttpsServer hs = HttpsServer.create(ia, 0);
            KeyStore ks = KeyStore.getInstance("JKS");

            SSLContext sc = SSLContext.getInstance("TLS");

            char[] pw = pass.toCharArray();


            FileInputStream fis = new FileInputStream("keystore.jks");
            ks.load(fis,pw);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunX509");
            kmf.init(ks,pw);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunX509");
            tmf.init(ks);
            sc.init(kmf.getKeyManagers(),tmf.getTrustManagers(),null);
            hs.setHttpsConfigurator(new HttpsConfigurator(sc){
                public void  configure(HttpsParameters h){
                    try{
                        SSLContext  context = getSSLContext();
                        SSLEngine engine = context.createSSLEngine();
                        h.setNeedClientAuth(mfalse);
                        h.setCipherSuites(engine.getEnabledCipherSuites());
                        h.setProtocols(engine.getEnabledProtocols());

                        SSLParameters sp = context.getSupportedSSLParameters();
                        h.setSSLParameters(sp);
                    }
                    catch(Exception e){
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });
            hs.createContext("/pizza",new MyHandler());
            hs.setExecutor(null);
            hs.start();

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to create HTTP server on port " + 3000 + " of localhost");
        }
    }
}