import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Handles the processing of overdue notifications by sending email reminders to members.
 */
public class NotificationProcessing implements Runnable {
    private Map<Book, Member> borrowedBooks; // Map of borrowed books to members

    /**
     * Constructs a NotificationProcessing object with the borrowed books map from the library.
     *
     * @param library The library object containing the borrowed books information.
     */
    public NotificationProcessing(Library library) {
        borrowedBooks = library.listBorrowedBooks();

    }

    /**
     * Continuously runs the notification processing task.
     */
    public void run() {
        try {
            // Initial delay before starting notifications
            Thread.sleep(1000);
            while (true) {
                notifications();
                // Sleep for 5 minutes before the next check
                Thread.sleep(1000 * 60 * 5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks for overdue books and sends notifications to the corresponding members.
     */
    public void notifications() {
        for (Map.Entry<Book, Member> entry : borrowedBooks.entrySet()) {
            Book book = entry.getKey(); // Get the book
            if (book.getOverDueState() > 0) { // Check if book is overdue
                if (book.getOverDueState() > book.getNotificationState()) { // Check if notification has already been sent
                    Member member = entry.getValue(); // Get the member
                    book.incrementNotificationState(); // Increment notification state
                    sendEmail(book, member); // Send email notification
                }
            }
        }
    }

    /**
     * Sends an email notification to the member regarding the overdue book and associated fines.
     *
     * @param book   The overdue book.
     * @param member The member who borrowed the book.
     */
    public void sendEmail(Book book, Member member) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLL-yyyy HH:mm"); // Format date and time
        // Print email notification message
        System.out.println("Email: " + member.getEmail() + " Please return the book: " + book.getTitle() + " that should've been returned on " + formatter.format(book.getBookReturnDueDate()) + " and pay your fine of R" + member.getFines() + ".");
    }
}
