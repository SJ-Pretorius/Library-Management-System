import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the library by providing operations for adding, removing, searching, and listing books and members.
 * <p>
 * Handles book checkouts and returns.
 */
public class Library {
    private List<Book> books = new ArrayList<>(); // Collection of books in the library
    private List<Member> members = new ArrayList<>(); // Collection of library members
    private Map<Book, Member> borrowedBooks = new HashMap<>(); // Map of borrowed books to members

    private FineProcessing fines = new FineProcessing(this); // Fine processing module
    private Thread fineThread = new Thread(fines); // Thread for fine processing
    private NotificationProcessing notifications = new NotificationProcessing(this); // Notification processing module
    private Thread notificationThread = new Thread(notifications); // Thread for notification processing

    private static final String BOOKS_FILE = "books.txt"; // File path for storing books data
    private static final String MEMBERS_FILE = "members.txt"; // File path for storing members data
    private static final String LOG_FILE = "logs.txt"; // File path for storing members data

    /**
     * Constructs a Library object with empty collections for books, members, and borrowed books.
     */
    public Library() {
        fineThread.start(); // Start fine processing thread
        notificationThread.start(); // Start notification processing thread
    }

    /**
     * Loads data from files into the library.
     */
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split each line by ", " to separate book attributes
                String[] parts = line.split(", ");
                if (parts.length == 8) {
                    String title = parts[0];
                    String author = parts[1];
                    String isbn = parts[2];
                    boolean isAvailable = Boolean.parseBoolean(parts[3]);
                    int overDueState = Integer.parseInt(parts[5]);
                    int notificationState = Integer.parseInt(parts[6]);
                    String memberEmail = parts[7];
                    Book newBook;
                    if (parts[4].equals("null")) {
                        newBook = new Book(title, author, isbn);
                    } else {
                        LocalDateTime localDateTime = LocalDateTime.parse(parts[4]);
                        newBook = new Book(title, author, isbn, isAvailable, localDateTime, overDueState, notificationState, memberEmail);
                    }
                    // Create a new Book object and add it to the library
                    books.add(newBook);
                }
            }
            reader.close();
            System.out.println("Books have been loaded from " + BOOKS_FILE);
        } catch (IOException e) {
            System.out.println("Books file not found or is corrupted, will be created on next save.");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split each line by ", " to separate member attributes
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    String name = parts[0];
                    String email = parts[1];
                    int fines = Integer.parseInt(parts[2]);
                    // Create a new Member object and add it to the list
                    Member newMember = new Member(name, email, fines);
                    members.add(newMember);
                }
            }
            reader.close();
            System.out.println("Members have been loaded from " + MEMBERS_FILE);
        } catch (IOException e) {
            System.out.println("Members file not found or is corrupted, will be created on next save.");
        }
        for (Book book : books) {
            if (book.getMemberEmail() != null) {
                for (Member member : members) {
                    if (member.getEmail().contains(book.getMemberEmail())) {
                        borrowedBooks.put(book, member);
                    }
                }
            }
        }

    }

    /**
     * Saves data from the library to files.
     */
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                String title = book.getTitle();
                String author = book.getAuthor();
                String isbn = book.getIsbn();
                boolean isAvailable = book.isAvailable();
                LocalDateTime localDateTime = book.getBookReturnDueDate();
                int overDueState = book.getOverDueState();
                int notificationState = book.getNotificationState();
                String memberEmail = book.getMemberEmail();
                writer.write(title + ", " + author + ", " + isbn + ", " + isAvailable + ", " +
                        localDateTime + ", " + overDueState + ", " + notificationState + ", " + memberEmail);
                writer.newLine();
            }
            writer.close();
            System.out.println("Books have been saved to " + BOOKS_FILE);
        } catch (IOException e) {
            System.err.println("An error occurred while saving books: " + e.getMessage());
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                String name = member.getName();
                String email = member.getEmail();
                int fines = member.getFines();
                writer.write(name + ", " + email + ", " + fines);
                writer.newLine();
            }
            writer.close();
            System.out.println("Members have been saved to " + MEMBERS_FILE);
        } catch (IOException e) {
            System.err.println("An error occurred while saving members: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds a book to the library.
     * 
     * @param book The book to be added to the library.
     * @throws IllegalArgumentException if the book is already in the library.
     */
    public void addBook(Book newBook) {
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(newBook.getIsbn())) {
                throw new IllegalArgumentException("Book already in library.");
            }
        }
        books.add(newBook);
        assert books.contains(newBook) : "Book not added to the library.";
    }

    /**
     * Adds a member to the library.
     * 
     * @param member The member to be added to the library.
     * @throws IllegalArgumentException if the member name already exists in the library.
     */
    public void addMember(Member newMember) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(newMember.getName())) {
                throw new IllegalArgumentException("Member name already exists.");
            }
        }
        members.add(newMember);
        assert members.contains(newMember) : "Member not added to the library.";
    }

    /**
     * Removes a member from the library.
     * 
     * @param memberName The name of the member to be removed.
     * @throws IllegalArgumentException If the member is not found.
     */
    public void removeMember(String memberName) {
        boolean found = false;
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(memberName)) {
                members.remove(member);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Member not found.");
        }
        assert !members.stream().anyMatch(member -> member.getName().equalsIgnoreCase(memberName)) : "Member not removed from the library.";
    }

    /**
     * Removes a book from the library.
     * 
     * @param bookIsbn The ISBN of the book to be removed.
     * @throws IllegalArgumentException If the book is not found.
     */
    public void removeBook(String bookIsbn) {
        boolean found = false;
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(bookIsbn)) {
                books.remove(book);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Book not found.");
        }
        assert !books.stream().anyMatch(book -> book.getIsbn().equalsIgnoreCase(bookIsbn)) : "Book not removed from the library.";
    }

    /**
     * Checks for a member by exact name match.
     * 
     * @param memberName The name of the member to check.
     * @return The member if found.
     * @throws IllegalArgumentException If member is not found.
     */
    public Member checkMember(String memberName) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(memberName)) {
                return member;
            }
        }
        throw new IllegalArgumentException("Member not found.");
    }

    /**
     * Searches for members whose names contain the specified string.
     * 
     * @param memberName The partial or full name to search for.
     * @return A list of matching members.
     */
    public List<Member> searchMember(String memberName) {
        List<Member> results = new ArrayList<>();
        for (Member member : members) {
            if (member.getName().toLowerCase().contains(memberName.toLowerCase())) {
                results.add(member);
            }
        }
        return results;
    }

    /**
     * Searches for books whose titles contain the specified string.
     * 
     * @param title The partial or full title to search for.
     * @return A list of matching books.
     */
    public List<Book> searchBooksByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Searches for books by a specific author.
     * 
     * @param author The name of the author to search for.
     * @return A list of books by the specified author.
     */
    public List<Book> searchBooksByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Searches for a book by its ISBN.
     * 
     * @param isbn The ISBN of the book to search for.
     * @return The book if found, otherwise null.
     */
    public Book searchBooksByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Checks out a book to a member.
     * 
     * @param isbn       The ISBN of the book to be checked out.
     * @param memberName The name of the member checking out the book.
     * @throws IllegalStateException If the book is not available for checkout.
     */
    public void checkoutBook(String isbn, String memberName, int dueDays) {
        Book book = searchBooksByIsbn(isbn);
        if (book == null) {
            throw new IllegalStateException("Book not found.");
        }
        Member member = checkMember(memberName);
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for checkout.");
        }
        book.toggleAvailability();
        book.setBookReturnDueDate(dueDays);
        book.setMemberEmail(member.getEmail());
        borrowedBooks.put(book, member);
        assert !book.isAvailable() : "Book availability not updated after checkout.";
    }

    /**
     * Returns a book to the library.
     * 
     * @param isbn       The ISBN of the book to be returned.
     * @param memberName The name of the member returning the book.
     * @throws IllegalStateException If the book is not borrowed or not borrowed by the specified member.
     */
    public void returnBook(String isbn, String memberName) {
        Book book = searchBooksByIsbn(isbn);
        if (book == null) {
            throw new IllegalStateException("Book not found.");
        }
        Member member = checkMember(memberName);
        if (book.isAvailable()) {
            throw new IllegalStateException("Book is not borrowed.");
        }
        if (!borrowedBooks.get(book).equals(member)) {
            throw new IllegalStateException("Book is not borrowed by this member.");
        }
        book.toggleAvailability();
        book.resetOverDueState();
        book.resetNotificationState();
        book.setMemberEmail(null);
        borrowedBooks.remove(book);
        assert book.isAvailable() : "Book availability not updated after return.";
    }

    /**
     * Lists all the books currently borrowed.
     * 
     * @return A map of borrowed books with their respective borrowers.
     */
    public Map<Book, Member> listBorrowedBooks() {
        return borrowedBooks;
    }

    /**
     * Lists all members of the library.
     * 
     * @return A list of all members.
     */
    public List<Member> listAllMembers() {
        return members;
    }

    /**
     * Lists all books in the library.
     * 
     * @return A list of all books.
     */
    public List<Book> listAllBooks() {
        return books;
    }

    public void writeLog(String log) {
        LocalDateTime time = LocalDateTime.now();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(time + " | " + log);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing logs: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
