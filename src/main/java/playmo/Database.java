package playmo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Database {
    Properties properties = new Properties();
    Statement stmt = null;
    String dbUser;
    String dbPass;
    String dbUrl;
    String bookTable = "SELECT * FROM book";
    String checkBook = "SELECT COUNT(*) FROM book WHERE name = ?";
    Connection conn;
    boolean exists = false;
    int authorId;
    int bookId;



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
        try (PreparedStatement ps = conn.prepareStatement(bookTable);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("bookid");
                String name = rs.getString("name");
                Integer publicationDate = rs.getInt("publication_date");
    
                // Process the data (for now, we can just print it out)
                System.out.println("ID: " + id + ", Name: " + name + ", Publish year: " + publicationDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBook(Connection conn, String title, Integer publicationDate){
        // adds a book to the database
        String insertBook = "INSERT INTO book(name, publication_date) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertBook, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setInt(2, publicationDate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bookId = rs.getInt(1);
                System.out.println("BOOK ID " + bookId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }      

    public void addAuthor(Connection conn, String author){
        // adds an author to the database
        String insertAuthor = "INSERT INTO author(name) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(insertAuthor, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                authorId = rs.getInt(1);
                System.out.println("AUTHOR ID " + authorId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addJuncTable(Connection conn, Integer bookId, Integer authorId) {
        String insertJuncTable = "INSERT INTO book_authors(BookId, AuthorId) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(insertJuncTable)) {
            ps.setInt(1, bookId);
            ps.setInt(2, authorId);
            ps.executeUpdate();
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

    public boolean checkBookDb(String bookName) {
        try (PreparedStatement psmt = conn.prepareStatement(checkBook)) {
            psmt.setString(1, bookName);
            try (ResultSet rs = psmt.executeQuery()){
                if (rs.next()) {
                    exists = rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }       
}