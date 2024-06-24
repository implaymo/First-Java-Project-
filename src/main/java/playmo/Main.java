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
        
        Scanner scanner = new Scanner(System.in);
        
        // Start of program 
        System.out.print("What's your name? ");
        String name = scanner.nextLine();
        String capName = WordUtils.capitalize(name);
        String user_answer;  
        System.out.println("Welcome to our Library " + capName + "!");

        while (true){ 
        System.out.println("Would you like to get a random book, search or add a book to the library? Write 'random' or 'add' or 'search': ");

        user_answer = scanner.nextLine();
        

        if (!user_answer.equals("random") && !user_answer.equals("add") && !user_answer.equals("search")){
            System.out.println("You must choose random, add or search");
        }


        if (user_answer.equals("random")) {
            db.getRandomBook();
            restartGame(scanner);
        }
        else if (user_answer.equals("add")) {
            System.out.println("Which book do you want to add to Library? ");
            String nameBook = scanner.nextLine();
            System.out.println("Which year was it published? ");
            String publicationDate = scanner.nextLine();
            Integer pubDate = Integer.parseInt(publicationDate);
            db.addBook(nameBook, pubDate);
            System.out.println("Who are the authors? If the authors are more than 1, right 'and' after each author. ");
            String authorToAdd = scanner.nextLine();
            String[] totalAuthors = authorToAdd.split("\\s+");
            for (String author: totalAuthors){
                if (author.equals("and")){
                    continue;
                }
                else {
                    Integer authorId = db.getAuthorId(author);
                    if (authorId != null){
                        db.addJuncTable(db.bookId, db.authorId);   
                    }    
                    else {
                        db.addAuthor(author);
                        db.addJuncTable(db.bookId, db.authorId); 
                    }
                }
                restartGame(scanner);
                
            }
            break;
        }
        else if (user_answer.equals("search")) {
            System.out.println("What do you want to search? Type 'book' or 'author': ");
            String typeSearch = scanner.nextLine();
            if (typeSearch.equals("book")) {
                try {
                    System.out.println("What book you want to search? ");
                    String bookSearch = scanner.nextLine();
                    bookSearch = WordUtils.capitalize(bookSearch);
                    db.getBookId(bookSearch);
                    db.queryJuncBookId(db.bookId);
                    db.queryAuthorId();
                    restartGame(scanner);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (typeSearch.equals("author")) {
                try { 
                System.out.println("What author you want to search? ");
                String authorSearch = scanner.nextLine();
                authorSearch = WordUtils.capitalize(authorSearch);
                db.getAuthorId(authorSearch);
                db.queryJuncAuthorId(db.authorId);
                db.queryBookId();  
                } catch (Exception e) {
                    e.printStackTrace();
                }
                restartGame(scanner);
            }
        }
        }
    scanner.close();     
    }

    public static boolean restartGame(Scanner scanner) {
        boolean answer = false;
        while (answer == false) {
            String question = "Would you like to stay in the library? Press 'y' to stay or press 'n' to leave. ";
            System.out.println(question);
            String decision = scanner.nextLine();
            if (!decision.equals("y") && !decision.equals("n")){
                System.out.println("You must choose 'y' or 'n'.");
            }
            if (decision.equals("y")) {
                answer = true;
            }
            else if (decision.equals("n")){
                answer = true;
                System.out.println("Thank you for joining us today. See you soon!");
                System.exit(0);
            }
        }
        return answer;
    }
 }