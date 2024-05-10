import java.time.LocalDateTime;
import java.util.Map;

/**
 * Manages fines for overdue books in the library system.
 */
public class FineProcessing implements Runnable {
    private Map<Book, Member> borrowedBooks; // Map of borrowed books and corresponding members
    private Library library; // Reference to the library

    public final static int DUEDAYS = 14; // Default days before a book is considered overdue
    public final static int MAXOVERDUEDAYS = 5; // Maximum days a book can be overdue before removal
    public final static int FINEPERDAY = 50; // Fine amount per overdue day

    /**
     * Initializes FineProcessing with a library reference.
     */
    public FineProcessing(Library library) {
        this.library = library;
        borrowedBooks = library.listBorrowedBooks(); // Get borrowed books
    }

    /**
     * Main processing loop to manage fines for overdue books.
     */
    public void run() {
        try {
            Thread.sleep(1000); // Initial delay before processing
            while (true) {
                fineManagement(); // Manage fines for overdue books
                Thread.sleep(1000 * 60); // Wait for 1 minute before next check
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions during processing
        }
    }

    /**
     * Manages fines for overdue books.
     */
    public void fineManagement() {
        for (Map.Entry<Book, Member> entry : borrowedBooks.entrySet()) {
            Book book = entry.getKey();
            Member member = entry.getValue();
            LocalDateTime dueDate = book.getBookReturnDueDate();
            LocalDateTime todayDate = LocalDateTime.now();
            if (todayDate.isAfter(dueDate)) { // Book is overdue
                if (book.incrementOverDueState() <= MAXOVERDUEDAYS) { // Increment and check overdue days
                    member.incrementFines(FINEPERDAY); // Apply fine
                    book.setBookReturnDueDate(1); // Extend due date
                    System.out.println("\nA fine of " + FINEPERDAY + " has been added to " + member.getName() + ".");
                    library.writeLog("A fine of " + FINEPERDAY + " has been added to " + member.getName() + ".");
                } else { // Book is excessively overdue
                    System.out.println("Book with ISBN " + book.getIsbn() + " has been removed due to not being returned for " + MAXOVERDUEDAYS + " days after due date.");
                    borrowedBooks.remove(book); // Remove book from borrowed list
                    library.removeBook(book.getIsbn()); // Remove book from library
                }
            }
        }
    }
}
