# Terminal Library

This project is a terminal-based library system that interacts with an external API to fetch book data and store it in a MySQL database. The database is designed to handle books and authors, including the relationships between them, allowing for books with multiple authors.

## Features

- Fetches books from an external API and stores them in a MySQL database.
- Database schema includes three tables: `book`, `author`, and a junction table `book_authors` for many-to-many relationships between books and authors.
- Users can search for books or authors, add new books and authors to the database, and get a random book with its author(s).

## Tech Used

- **Java**: For the application logic.
- **MySQL**: For data storage.
- **API Requests**: To fetch book data.

## Database Schema

1. **book**
   - `bookid` (Primary Key)
   - `name`
   - `publication_date`

2. **author**
   - `authorid` (Primary Key)
   - `name`

3. **book_authors**
   - `bookid` (Foreign Key referencing book.bookid)
   - `authorid` (Foreign Key referencing author.authorid)

## Setup

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd <repository-directory>

# 2. **Set up the MySQL database:**

#    - Create a new MySQL database.
#    - Create the tables using the following SQL commands:

## SQL
CREATE TABLE book (
    bookid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    publication_date INT
);

CREATE TABLE author (
    authorid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE book_authors (
    bookid INT,
    authorid INT,
    FOREIGN KEY (bookid) REFERENCES book(bookid),
    FOREIGN KEY (authorid) REFERENCES author(authorid)
);

# 3. **Create the `config.properties` file:**

    - In the root directory of the project, create a file named `config.properties`.
    - Add the following properties to the file and update the values with your database credentials and URL:

## Properties
 -`db.user=yourDatabaseUsername`
 -`db.pass=yourDatabasePassword`
 -`db.url=jdbc:mysql://yourDatabaseUrl:3306/yourDatabaseName`

# 4. **Configure the application:**

    - Ensure your Java application is set up to read the `config.properties` file and use the properties for database connection.

# 5. **Run the application:**

   - Compile and run your Java application. The main functionality includes fetching books from the API, searching for books and authors, adding new entries, and retrieving random books with authors.

# Usage

 - **Search for a book or author:** Use the search functionality to find books or authors in the database.
 - **Add a new book and author:** Add new books and authors through the provided commands.
 - **Get a random book and author:** Retrieve a random book along with its associated author(s).

# Future Enhancements

 - Add more functionalities, such as updating and deleting books/authors.
 - Improve the user interface for better usability.
 - Enhance error handling and input validation.

# Contributing
   Feel free to fork the repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.
