package playmo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Database {
    Properties properties = new Properties();
    String dbUser;
    String dbPass;
    String dbUrl;


    public Database() {
        getInfo();
    }
    public void getInfo(){
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            dbUser = properties.getProperty("db.user");
            dbPass = properties.getProperty("db.pass");
            dbUrl = properties.getProperty("db.url");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    
    }
    
    public void printUser(){
        if (dbUser == null || dbPass == null){
            System.out.println("CANT FIND USER");
        }
        else {
            System.out.println(dbUser);
            System.out.println(dbPass);
        }

    }
}
