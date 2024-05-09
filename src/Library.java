import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private List<Book> books;
    private List<Member> members;
    private Map<Book, Member> borrowedBooks;

    /**
     * Constructs a Library object with empty book and member collections and an empty map for borrowed books.
     */
    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.borrowedBooks = new HashMap<>();
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
        LocalDateTime bookReturnDueDate = LocalDateTime.now().plus(dueDays, ChronoUnit.DAYS);
        book.setBookReturnDueDate(bookReturnDueDate, dueDays);
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
}
