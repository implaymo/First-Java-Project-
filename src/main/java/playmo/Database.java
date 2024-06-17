package playmo;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.beans.Statement;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.json.JSONArray;



public class Database {
    Properties properties = new Properties();
    Statement stmt = null;
    String dbUser;
    String dbPass;
    String dbUrl;
    String bookTable = "SELECT * FROM book";
    Connection conn;




    public Database() {
        // Constructor to initialize all the variables of the class
        getInfo();
        conn = connectDb();
    }

    public void getInfo(){
        // Gets sensitive that from config.properties file
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            dbUser = properties.getProperty("db.user");
            dbPass = properties.getProperty("db.pass");
            dbUrl = properties.getProperty("db.url");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    
    }

    public Connection connectDb() {
        try{
             conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void queryBookTable(Connection conn){
        // Query data from book table to check if values are on it
        try (PreparedStatement pstmt = conn.prepareStatement(bookTable);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                Integer publishYear = rs.getInt("publish_year");
    
                // Process the data (for now, we can just print it out)
                System.out.println("ID: " + id + ", Name: " + name + ", Publish year: " + publishYear);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBook(Connection conn, String title, Integer publishedYear){
        // adds a book to the database
        String insertBook = "INSERT INTO book (title, published_year) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertBook)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, publishedYear);
            pstmt.executeUpdate();
            System.out.println("Book added: " + title + " (" + publishedYear + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
        }      

    public void addAuthor(Connection conn, String author){
        // adds an author to the database
        String insertAuthor = "INSERT INTO author(name) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertAuthor)) {
            pstmt.setString(1, author);
            pstmt.executeUpdate();
            System.out.println("Author added: " + author);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}