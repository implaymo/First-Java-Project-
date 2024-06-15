package playmo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class Api {
    String apiUrl;

    public Api(){
        apiUrl = "https://openlibrary.org/people/mekBot/books/want-to-read.json";
    }

    public void apiRequest(){

        HttpClient httpclient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).GET().build();
        try {
            HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println("API response: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();;
        }

    }   
}

