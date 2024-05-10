import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Facilitates user interactions with the library by providing comprehensive prompts for operations and returning information in an understandable format.
 * <p>
 * Ensures the Library class functions with accurate information.
 */
public class LibraryOperations {
    private Library library = new Library(); // Library object
    private Scanner scanner = new Scanner(System.in); // Scanner object for user input

    /**
     * Adds example books and members to the library.
     * <p>
     * TO BE REMOVED WHEN ENTERING PRODUCTION.
     */
    public void loadData() {
        library.loadData();
    }

    public void saveData() {
        library.saveData();
    }

    /**
     * Prompts the user to input details of a new book and adds it to the library.
     */
    public void addBook() {
        // Prompt user to input book details
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        // Create a new Book object and add it to the library
        Book newBook = new Book(title, author, isbn);
        library.addBook(newBook);
    }

    /**
     * Prompts the user to input the ISBN of a book to remove and removes it from the library.
     */
    public void removeBook() {
        // Prompt user to input the ISBN of the book to remove
        System.out.print("Enter book ISBN to remove: ");
        String bookIsbn = scanner.nextLine();

        // Remove the book from the library
        library.removeBook(bookIsbn);
    }

    /**
     * Lists all the books in the library.
     */
    public void listAllBooks() {
        // Retrieve a list of all books in the library
        List<Book> books = library.listAllBooks();
        if (books.isEmpty()) {
            System.out.println("No Books.");
        } else {
            // Print details of each book
            for (Book book : books) {
                printBooks(book);
            }
        }
    }

    /**
     * Prompts the user to input details of a new member and adds them to the library.
     */
    public void addMember() {
        // Prompt user to input member details
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();

        // Create a new Member object and add it to the library
        Member newMember = new Member(name, email);
        library.addMember(newMember);
    }

    /**
     * Prompts the user to input the name of a member to remove and removes them from the library.
     */
    public void removeMember() {
        // Prompt user to input the name of the member to remove
        System.out.print("Enter member name: ");
        String memberName = scanner.nextLine();

        // Remove the member from the library
        library.removeMember(memberName);
    }

    /**
     * Searches for members by name.
     */
    public void searchMember() {
        // Prompt user to input the name of the member to search
        System.out.print("Enter member name to search: ");
        String memberName = scanner.nextLine();

        // Search for members by name in the library
        List<Member> members = library.searchMember(memberName);
        if (members.isEmpty()) {
            System.out.println("\nNo Members Found.");
        } else {
            // Print details of found members
            System.out.println("\nMembers Found:\n");
            for (Member member : members) {
                printMembers(member);
            }
        }
    }

    /**
     * Lists all members of the library.
     */
    public void listAllMembers() {
        // Retrieve a list of all members in the library
        List<Member> members = library.listAllMembers();
        if (members.isEmpty()) {
            System.out.println("No Members.");
        } else {
            // Print details of each member
            for (Member member : members) {
                printMembers(member);
            }
        }
    }

    /**
     * Prompts the user to input the title of a book to search for and searches for it by title.
     */
    public void searchBooksTitle() {
        // Prompt user to input the title of the book to search for
        System.out.print("Enter book title to search: ");
        String searchTitle = scanner.nextLine();

        // Search for books by title in the library
        List<Book> searchResults = library.searchBooksByTitle(searchTitle);
        if (searchResults.isEmpty()) {
            System.out.println("\nNo Books Found.");
        } else {
            // Print details of found books
            System.out.println("\nBooks Found:\n");
            for (Book book : searchResults) {
                printBooks(book);
            }
        }
    }

    /**
     * Prompts the user to input the name of an author to search for and searches for books by author.
     */
    public void searchBooksAuthor() {
        // Prompt user to input the name of the author to search for
        System.out.print("Enter author name to search: ");
        String searchAuthor = scanner.nextLine();

        // Search for books by author in the library
        List<Book> searchResults = library.searchBooksByAuthor(searchAuthor);
        if (searchResults.isEmpty()) {
            System.out.println("\nNo books found.");
        } else {
            // Print details of found books
            System.out.println("\nBooks found:\n");
            for (Book book : searchResults) {
                printBooks(book);
            }
        }
    }

    /**
     * Prompts the user to input the ISBN of a book to search for and searches for it by ISBN.
     */
    public void searchBooksIsbn() {
        // Prompt user to input the exact ISBN of the book to search for
        System.out.print("Enter the exact ISBN to search: ");
        String searchIsbn = scanner.nextLine();

        // Search for a book by ISBN in the library
        Book book = library.searchBooksByIsbn(searchIsbn);
        if (book == null) {
            System.out.println("\nNo Books Found.");
        } else {
            // Print details of the found book
            System.out.println("\nBook found:\n");
            printBooks(book);
        }
    }

    /**
     * Prompts the user to input the name of a member and the ISBN of a book to check out and checks out the book to the member.
     */
    public void checkout() {
        // Prompt user to input member name and book ISBN
        System.out.print("Enter member name: ");
        String checkoutMember = scanner.nextLine();
        System.out.print("Enter book ISBN to checkout: ");
        String checkoutIsbn = scanner.nextLine();

        // Check out the book to the member
        library.checkoutBook(checkoutIsbn, checkoutMember, FineProcessing.DUEDAYS);
    }

    /**
     * Prompts the user to input the name of a member and the ISBN of a book to return and returns the book to the library.
     */
    public void returnBook() {
        // Prompt user to input member name and book ISBN
        System.out.print("Enter member name: ");
        String returnMember = scanner.nextLine();
        System.out.print("Enter book ISBN to return: ");
        String returnIsbn = scanner.nextLine();

        // Return the book to the library
        library.returnBook(returnIsbn, returnMember);
    }

    /**
     * Lists all checked out books.
     */
    public void listCheckOutBooks() {
        // Retrieve a map of all borrowed books
        Map<Book, Member> borrowedBooks = library.listBorrowedBooks();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLL-yyyy HH:mm");
        boolean nothing = true; // Flag to track if there are no borrowed books
        for (Map.Entry<Book, Member> entry : borrowedBooks.entrySet()) {
            nothing = false;
            Book book = entry.getKey();
            Member member = entry.getValue();
            // Print details of each borrowed book
            System.out.println("Book: " + book.getTitle() + " | ISBN: " + book.getIsbn() + " | Member Name: " + member.getName() + " | Member Email: " + member.getEmail() + " | Member Fines: R" + member.getFines() + " | Due Date: " + formatter.format(book.getBookReturnDueDate()));
        }
        if (nothing == true) {
            System.out.println("No Borrowed Books.");
        }
    }

    /**
     * Allows a member to pay fines.
     * 
     * @throws NumberFormatException if the entered fine amount is not a valid integer.
     */
    public void payFine() {
        System.out.print("Enter member name: "); // Prompt for member name
        String name = scanner.nextLine();
        System.out.print("Enter amount: "); // Prompt for fine amount
        String fineString = scanner.nextLine();
        int fine;
        try {
            fine = Integer.parseInt(fineString); // Parse fine amount to integer
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid fine amount. Please enter a valid integer."); // Throw exception if the entered fine amount is not a valid integer
        }
        Member member = library.checkMember(name);
        member.decrementFines(fine); // Decrease member's fines by the specified amount
        library.writeLog("Member: " + member.getName() + " payed R" + fine + " in fines.");
    }

    /**
     * Lists all members with fines and their respective fine amounts.
     * If there are no members with fines, it prints "No Fines.".
     */
    public void listAllFines() {
        List<Member> members = library.listAllMembers();
        boolean nothing = true; // Flag to track if there are no members with fines
        for (Member member : members) {
            if (member.getFines() > 0) {
                nothing = false;
                printMembers(member); // Print details of members with fines
            }
        }
        if (nothing) {
            System.out.println("No Fines."); // Print if there are no members with fines
        }
    }

    /**
     * Prints details of a book, including its title, author, ISBN, and availability.
     * If the book is not available, it also prints the due date and whether it is late.
     * 
     * @param book The book object to print details of.
     */
    private void printBooks(Book book) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLL-yyyy HH:mm");
        if (book.isAvailable()) {
            System.out.println("Title: " + book.getTitle() + " | Author: " + book.getAuthor() + " | ISBN: " + book.getIsbn() + " | Available: Yes"); // Print book details when available
        } else {
            String late = book.getOverDueState() > 0 ? "Yes" : "No"; // Determine if the book is late
            System.out.println("Title: " + book.getTitle() + " | Author: " + book.getAuthor() + " | ISBN: " + book.getIsbn() + " | Available: No | Due Date: " + formatter.format(book.getBookReturnDueDate()) + " | Late : " + late); // Print book details including due date and late status
        }
    }

    /**
     * Prints details of a member, including their name, email, and fines.
     * 
     * @param member The member object to print details of.
     */
    private void printMembers(Member member) {
        System.out.println("Name: " + member.getName() + " | Email: " + member.getEmail() + " | Fines: R" + member.getFines()); // Print member details
    }

}
