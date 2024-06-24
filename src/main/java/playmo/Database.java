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
import java.util.ArrayList;
import org.apache.commons.text.WordUtils;



public class Database {
    Properties properties = new Properties();
    Statement stmt = null;
    String dbUser;
    String dbPass;
    String dbUrl;
    Connection conn;
    boolean exists = false;
    int authorId;
    int bookId;
    ArrayList<Integer> allAuthorsId;
    ArrayList<String> allAuthorsName;
    ArrayList<Integer> allBooksId;
    ArrayList<String> allBookName;



    public Database() {
        // Constructor to initialize all the variables of the class
        this.allAuthorsId = new ArrayList<>();
        this.allAuthorsName = new ArrayList<>();
        this.allBooksId = new ArrayList<>();
        this.allBookName = new ArrayList<>();
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

    public Integer getBookId(String bookName){
        String bookTable = "SELECT * FROM book WHERE name LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(bookTable)) {
            ps.setString(1, bookName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bookId = rs.getInt("bookid");
                    String name = rs.getString("name");
                    Integer publicationDate = rs.getInt("publication_date");
                    System.out.println("Title: " + name + " / " +" Publish year: " + publicationDate);
                } else {
                    System.out.println("Book not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookId;
    }
    
    public void queryBookId() {
        String sqlBookId = "SELECT * FROM book WHERE bookid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlBookId)) {
            if (allBooksId.isEmpty()){
                System.out.println("Author not found1");
            } 
            else {
                    for (Integer book: allBooksId){
                        ps.setInt(1, book);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()){
                                String bookName = rs.getString("name");
                                allBookName.add(bookName);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }  
                    }
                    System.out.println("Books written: " + String.join(", ", allBookName));
                    allBookName = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> queryJuncBookId(Integer idBook) {
        allAuthorsId = new ArrayList<>();
        String sqlJunc = "SELECT * from book_authors WHERE BookId LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlJunc)) {
            ps.setInt(1, idBook);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    authorId = rs.getInt("AuthorId");
                    allAuthorsId.add(authorId);
                }
            } catch (Exception e){
                e.printStackTrace();
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allAuthorsId;
    }

    public ArrayList<Integer> queryJuncAuthorId(Integer idAuthor) {
        allBooksId = new ArrayList<>();
        String sqlJuncAuthor = "SELECT * from book_authors WHERE AuthorId LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlJuncAuthor)) {
            ps.setInt(1, idAuthor); 
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    bookId = rs.getInt("BookId");
                    allBooksId.add(bookId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allBooksId;
    }

    public void queryAuthorId() {
        String sqlAuthorId = "SELECT * from author WHERE authorid LIKE ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlAuthorId)) {
                    if (allAuthorsId.isEmpty()){
                        System.out.println("Authors not found2.");
                    }
                    else {
                        for (int author: allAuthorsId){ {
                            ps.setInt(1, author);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                        String authorName = rs.getString("name");
                                        allAuthorsName.add(authorName);
                                }
                                } catch (Exception e) {
                                    e.printStackTrace();            
                                } 
                            } 
                        }
                        System.out.println("Authors: " + String.join(", ", allAuthorsName));  
                        allAuthorsName = new ArrayList<>();                      
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

    public Integer getAuthorId(String name){
        String sqlAuthorName = "SELECT * from author WHERE name LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlAuthorName)){
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    authorId = rs.getInt("authorid");
                }
                else {
                    System.out.println("Author not found3");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorId;
    }

    
    public void addBook(String title, Integer publicationDate){
        // adds a book to the database
        String insertBook = "INSERT INTO book(name, publication_date) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertBook, Statement.RETURN_GENERATED_KEYS)) {
            title = WordUtils.capitalize(title);
            ps.setString(1, title);
            ps.setInt(2, publicationDate);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bookId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        } 
        

    public void addAuthor(String author){
        // adds an author to the database
        String insertAuthor = "INSERT INTO author(name) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(insertAuthor, Statement.RETURN_GENERATED_KEYS)) {
            author = WordUtils.capitalize(author);
            ps.setString(1, author);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                authorId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addJuncTable(Integer bookId, Integer authorId) {
        String insertJuncTable = "INSERT INTO book_authors(BookId, AuthorId) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(insertJuncTable)) {
            ps.setInt(1, bookId);
            ps.setInt(2, authorId);
            ps.executeUpdate();
        } catch (SQLException e ){
            e.printStackTrace();
        }
    }

    public boolean checkBook(String bookName) {
        // Checks if book exists in database
        String checkBook = "SELECT COUNT(*) FROM book WHERE name = ?";
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
    
    public void getRandomBook() {
        String randomBook = "SELECT bookid, name FROM book ORDER BY RAND() LIMIT 1;";
        try (PreparedStatement psmt = conn.prepareStatement(randomBook)) {
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    String bookName = rs.getString("name");
                    Integer bookId = rs.getInt("bookid");
                    System.out.println("The book you got is: " + bookName);
                    getAuthors(bookId);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAuthors(Integer bookId) {
        String getAuthors = "SELECT author.name FROM author JOIN book_authors ON author.authorId = book_authors.authorId WHERE book_authors.BookId = ?";
        try (PreparedStatement psmt = conn.prepareStatement(getAuthors)) {
            psmt.setInt(1, bookId);  
            try (ResultSet rs = psmt.executeQuery()) {

                StringBuilder totalAuthors = new StringBuilder(); 
                while (rs.next()) {
                    String author = rs.getString("name");
                    totalAuthors.append(author).append(", ");
                }
                if (totalAuthors.length() > 0) {
                    totalAuthors.setLength(totalAuthors.length() - 2);
                    System.out.println("Authors of the book: " + totalAuthors.toString());
                }
                else {
                    System.out.println("Authors of the book: " + totalAuthors.toString());
                }

            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}