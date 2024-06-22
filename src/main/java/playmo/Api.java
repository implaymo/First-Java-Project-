package playmo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import java.io.IOException;

public class Api {
    String apiUrl;
    String title;
    JsonArray author;
    Integer published_year;
    Database db;

    public Api(){
        apiUrl = "https://openlibrary.org/people/mekBot/books/want-to-read.json";
        db = new Database();
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
                    Integer publicationDate = work.getInt("first_publish_year");
                    String title = work.getString("title");
                    JSONArray author = work.getJSONArray("author_names");
                    if (db.checkBook(title)) {
                        continue;
                    } else {
                        db.addBook(title, publicationDate);
                        for (Integer j = 0; j < author.length(); j++){
                            String authorName = author.getString(j);
                            db.addAuthor(authorName);
                            db.addJuncTable(db.bookId, db.authorId);
                        }     
                    }                                   
                }
            }
            else {
                System.out.println("Error: " + response.statusCode());
            } 
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();;
        }
    }   
}

