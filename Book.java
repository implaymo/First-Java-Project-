package playmo;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Book {
    List<String> bookTitles;

    public Book() {
        this.bookTitles = new ArrayList<>(Arrays.asList(
                "The Secret Garden",
                "The Great Gatsby",
                "To Kill a Mockingbird",
                "Pride and Prejudice",
                "The Catcher in the Rye",
                "Lord of the Flies",
                "1984",
                "Animal Farm",
                "The Hobbit",
                "Harry Potter and the Philosopher's Stone",
                "The Da Vinci Code",
                "The Hunger Games",
                "The Alchemist",
                "The Girl with the Dragon Tattoo",
                "The Lord of the Rings"
            ));
    }
        
    public String getRandomBook() {
        Random rand = new Random();
        String book = bookTitles.get(rand.nextInt(bookTitles.size()));
        return book;
    }

    public void addNewBook(String newBook){
        bookTitles.add(newBook);
        System.out.println("Added new book \"" + newBook + "\"");
    }
}
