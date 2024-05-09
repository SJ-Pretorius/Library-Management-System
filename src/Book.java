import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents a book with title, author, and ISBN information.
 */
public class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    private LocalDateTime bookReturnDueDate;

    /**
     * Constructs a new Book object with the specified title, author, and ISBN.
     * 
     * @param title  The title of the book.
     * @param author The author of the book.
     * @param isbn   The ISBN of the book.
     * @throws IllegalArgumentException if the title, author, or ISBN is empty.
     */
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;

        if (title.equals("")) {
            throw new IllegalArgumentException("The title of the book was not provided.");
        }

        if (author.equals("")) {
            throw new IllegalArgumentException("The author of the book was not provided.");
        }

        if (isbn.equals("")) {
            throw new IllegalArgumentException("The ISBN of the book was not provided.");
        }
    }

    /**
     * Gets the title of the book.
     * 
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the author of the book.
     * 
     * @return The author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets the ISBN of the book.
     * 
     * @return The ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Checks if the book is available.
     * 
     * @return true if the book is available, otherwise false.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Toggles the availability status of the book.
     */
    public void toggleAvailability() {
        this.isAvailable = !this.isAvailable;
    }

    public LocalDateTime getBookReturnDueDate() {
        return bookReturnDueDate;
    }

    public void setBookReturnDueDate(LocalDateTime e, int dueDays) {
        bookReturnDueDate = LocalDateTime.now().plus(dueDays, ChronoUnit.DAYS);
    }
}
