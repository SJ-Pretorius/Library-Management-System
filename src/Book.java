import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents a book with title, author, and ISBN information.
 */
public class Book {
    private String title; // The title of the book.
    private String author; // The author of the book.
    private String isbn; // The ISBN of the book.
    private boolean isAvailable = true; // Indicates whether the book is available or not.
    private LocalDateTime bookReturnDueDate; // The due date for returning the book.
    private int overDueState = 0; // Tracks the number of times the book is overdue.
    private int notificationState = 0; // Tracks the notification state for the book.
    private String memberEmail; // The email of the member who borrowed the book.

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
     * Constructs a new Book object with the specified title, author, ISBN, availability status,
     * return due date, overdue state, notification state, and member email.
     *
     * @param title             The title of the book.
     * @param author            The author of the book.
     * @param isbn              The ISBN of the book.
     * @param isAvailable       Indicates whether the book is available or not.
     * @param bookReturnDueDate The due date for returning the book.
     * @param overDueState      Tracks the number of times the book is overdue.
     * @param notificationState Tracks the notification state for the book.
     * @param memberEmail       The email of the member who borrowed the book.
     */
    public Book(String title, String author, String isbn, boolean isAvailable,
            LocalDateTime bookReturnDueDate, int overDueState, int notificationState,
            String memberEmail) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.bookReturnDueDate = bookReturnDueDate;
        this.overDueState = overDueState;
        this.notificationState = notificationState;
        this.memberEmail = memberEmail;
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

    /**
     * Sets the email of the member who borrowed the book.
     * 
     * @param email The email of the member.
     */
    public void setMemberEmail(String email) {
        memberEmail = email;
    }

    /**
     * Gets the email of the member who borrowed the book.
     * 
     * @return The email of the member.
     */
    public String getMemberEmail() {
        return memberEmail;
    }

    /**
     * Gets the due date for returning the book.
     * 
     * @return The due date for returning the book.
     */
    public LocalDateTime getBookReturnDueDate() {
        return bookReturnDueDate;
    }

    /**
     * Sets the return due date of the book.
     * 
     * @param dueDays The number of days until the book should be returned.
     */
    public void setBookReturnDueDate(int dueDays) {
        bookReturnDueDate = LocalDateTime.now().plus(dueDays, ChronoUnit.DAYS);
    }

    /**
     * Increments the overdue state of the book.
     * 
     * @return The updated overdue state.
     */
    public int incrementOverDueState() {
        overDueState++;
        return overDueState;
    }

    /**
     * Resets the overdue state of the book to zero.
     */
    public void resetOverDueState() {
        overDueState = 0;
    }

    /**
     * Gets the overdue state of the book.
     * 
     * @return The overdue state of the book.
     */
    public int getOverDueState() {
        return overDueState;
    }

    /**
     * Increments the notification state of the book.
     */
    public void incrementNotificationState() {
        notificationState++;
    }

    /**
     * Gets the notification state of the book.
     * 
     * @return The notification state of the book.
     */
    public int getNotificationState() {
        return notificationState;
    }

    /**
     * Resets the notification state of the book to zero.
     */
    public void resetNotificationState() {
        notificationState = 0;
    }
}
