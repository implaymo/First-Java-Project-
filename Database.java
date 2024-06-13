package playmo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Database {
    Properties properties = new Properties();
    String dbUser;
    String dbPass;

    public Database() {
    try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
        if (input == null){
            System.out.println("Sorry, unable to find config.properties");
        }
        properties.load(input);

        dbUser = properties.getProperty("db.user");
        dbPass = properties.getProperty("db.pass");
    } catch (IOException ex){
        ex.printStackTrace();
    }

    }

    final String DB_URL = "jdbc:mysql://localhost/";
    final String USER = dbUser;
    final String PASS = dbPass;


    public void printUser(){
        if (dbUser == null){
            System.out.println("CANT FIND USER");
        }
        else {
            System.out.println(dbUser);
        }

    }
}
