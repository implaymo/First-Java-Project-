package playmo;

/**
 * main
 */
import java.util.Scanner;
import org.apache.commons.text.WordUtils;

 public class Main {
 
     /**
     * @param args
     */
    public static void main(String[] args) {
        Database db = new Database();
        db.connectDb();

        Api api = new Api();
        api.apiRequest();
        
        Book bookLibrary = new Book();
        Scanner scanner = new Scanner(System.in);
        
        // Start of program 
        System.out.print("What's your name? ");
        String name = scanner.nextLine();
        String capName = WordUtils.capitalize(name);
        String user_answer;  
        
        while (true){

        System.out.print("Hello " + capName +  " Would you like to get a random book, search or add a book to the library? Write 'random' or 'add''or 'search': ");

        user_answer = scanner.nextLine();
        

        if (!user_answer.equals("random") && !user_answer.equals("add")){
            System.out.println("You must choose random or add");

        }


        if (user_answer.equals("random")) {
            String random_book = bookLibrary.getRandomBook();
            System.out.println("Here is your new bookLibrary: " + random_book);
            System.exit(0);
        }
        else if (user_answer.equals("add")) {
            System.out.println("Which book do you want to add to Library? ");
            String bookToAdd = scanner.nextLine();
            bookLibrary.addNewBook(bookToAdd);
            System.out.println(bookLibrary.bookTitles);
            break;
        }
        }
    scanner.close();     
    }
 }