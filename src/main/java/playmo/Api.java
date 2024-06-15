package playmo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

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
            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray readingLogEntries = jsonObject.getJSONArray("reading_log_entries");
            if (readingLogEntries.length() > 0){
                for (int i = 0; i < readingLogEntries.length(); i++){
                    JSONObject entry = readingLogEntries.getJSONObject(i);
                    JSONObject work = entry.getJSONObject("work");
                    String title = work.getString("title");
                    JSONArray author = work.getJSONArray("author_names");
                    System.out.println(author);
                }   
            } else {
                System.out.println("Error: " + response.statusCode());
            }            

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();;
        }

    }   
}

