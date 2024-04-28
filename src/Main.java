import java.util.Scanner;

/**
 * Facilitates all user interactions with the library by offering comprehensive prompts.
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static LibraryOperations theLibrary = new LibraryOperations();

    /**
     * Main method to run the Library Management System.
     */
    public static void main(String[] args) {
        clearConsole();
        System.out.println();
        printDash(44);
        System.out.println("Salomon Jansen Pretorius\nStudent Number: 20231348\nJD521 Formative Assessment 2 (19 April 2024) \nCTU Training Solutions Gqeberha");
        printDash(44);
        theLibrary.addExampleData();
        while (true) {
            try {
                displayMenu("Library Management System", "1. Manage Books", "2. Manage Members", "3. Search For a Book", "4. Checkout a Book", "5. Return a Book", "6. List Checked Out Books", "7. Exit");
                String choice = scanner.nextLine();
                clearConsole();
                switch (choice) {
                    case "1":
                        manageBooks();
                        break;
                    case "2":
                        manageMembers();
                        break;
                    case "3":
                        searchBooks();
                        break;
                    case "4":
                        System.out.println("\nCheckout a Book");
                        printDash(15);
                        theLibrary.checkout();
                        System.out.println();
                        printDash(30);
                        System.out.println("Book Checked Out Successfully.");
                        printDash(30);
                        System.out.println("\nPress enter to return to main menu.");
                        scanner.nextLine();
                        break;
                    case "5":
                        System.out.println("\nReturn a Book");
                        printDash(14);
                        theLibrary.returnBook();
                        System.out.println();
                        printDash(28);
                        System.out.println("Book Returned Successfully.");
                        printDash(28);
                        System.out.println("\nPress enter to return to main menu.");
                        scanner.nextLine();
                        break;
                    case "6":
                        System.out.println("\nList Checked Out Books");
                        printDash(44);
                        System.out.println();
                        theLibrary.listCheckOutBooks();
                        System.out.println();
                        printDash(44);
                        System.out.println("\nPress enter to return to main menu.");
                        scanner.nextLine();
                        break;
                    case "7":
                        exit();
                    default:
                        System.out.println();
                        printDash(33);
                        System.out.println("Invalid choice. Please try again.");
                        printDash(33);
                        continue;
                }
                clearConsole();
            } catch (Exception e) {
                System.out.println("");
                int messageLength = e.getMessage().length();
                for (int i = -19; i < messageLength; i++) {
                    System.out.print("-");
                }
                System.out.println("\nAn error occurred: " + e.getMessage());
                for (int i = 0; i < messageLength; i++) {
                    System.out.print("-");
                }
                printDash(19);
                System.out.println("\nPress enter to return to main menu.");
                scanner.nextLine();
                clearConsole();
            }
        }
    }

    /**
     * Manages book operations such as adding, removing, and listing books.
     */
    public static void manageBooks() {
        while (true) {
            displayMenu("Manage Books", "1. Add a Book", "2. Remove a Book", "3. List All Books", "4. Return To Main Menu");
            String choice = scanner.nextLine();
            clearConsole();
            switch (choice) {
                case "1":
                    System.out.println("\nAdd a Book");
                    printDash(10);
                    theLibrary.addBook();
                    System.out.println();
                    printDash(24);
                    System.out.println("Book added successfully.");
                    printDash(24);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "2":
                    System.out.println("\nRemove a Book");
                    printDash(13);
                    theLibrary.removeBook();
                    System.out.println();
                    printDash(27);
                    System.out.println("Book removed successfully.");
                    printDash(27);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "3":
                    System.out.println("\nList All Books");
                    printDash(44);
                    System.out.println();
                    theLibrary.listAllBooks();
                    System.out.println();
                    printDash(44);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "4":
                    break;
                default:
                    System.out.println();
                    printDash(33);
                    System.out.println("Invalid choice. Please try again.");
                    printDash(33);
                    continue;
            }
            break;
        }
    }

    /**
     * Manages member operations such as adding, removing, searching, and listing members.
     */
    public static void manageMembers() {
        while (true) {
            displayMenu("Manage Members", "1. Add a Member", "2. Remove a Member", "3. Search For a Member", "4. List All Members", "5. Return To Main Menu");
            String choice = scanner.nextLine();
            clearConsole();
            switch (choice) {
                case "1":
                    System.out.println("\nAdd a Member");
                    printDash(12);
                    theLibrary.addMember();
                    System.out.println();
                    printDash(31);
                    System.out.println("Member registered successfully.");
                    printDash(31);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "2":
                    System.out.println("\nRemove a Member");
                    printDash(15);
                    theLibrary.removeMember();
                    printDash(29);
                    System.out.println("Member removed successfully.");
                    printDash(29);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "3":
                    System.out.println("\nSearch For a Member");
                    printDash(20);
                    theLibrary.searchMember();
                    System.out.println();
                    printDash(44);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "4":
                    System.out.println("\nList All Members");
                    printDash(44);
                    System.out.println();
                    theLibrary.listAllMembers();
                    System.out.println();
                    printDash(44);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "5":
                    break;
                default:
                    System.out.println();
                    printDash(33);
                    System.out.println("Invalid choice. Please try again.");
                    printDash(33);
                    continue;
            }
            break;
        }
    }

    /**
     * Searches for books based on various criteria such as title, author, or ISBN.
     */
    public static void searchBooks() {
        while (true) {
            displayMenu("Search For a Book", "1. Search For a Book By Title", "2. Search For a Book By Author", "3. Search For a Book By ISBN", "4. Return To Main Menu");
            String choice = scanner.nextLine();
            clearConsole();
            switch (choice) {
                case "1":
                    System.out.println("\nSearch For a Book By Title");
                    printDash(26);
                    theLibrary.searchBooksTitle();
                    System.out.println();
                    printDash(44);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "2":
                    System.out.println("\nSearch For a Book By Author");
                    printDash(27);
                    theLibrary.searchBooksAuthor();
                    System.out.println();
                    printDash(44);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "3":
                    System.out.println("\nSearch For a Book By ISBN");
                    printDash(25);
                    theLibrary.searchBooksIsbn();
                    System.out.println();
                    printDash(44);
                    System.out.println("\nPress enter to return to main menu.");
                    scanner.nextLine();
                    break;
                case "4":
                    break;
                default:
                    System.out.println();
                    printDash(33);
                    System.out.println("Invalid choice. Please try again.");
                    printDash(33);
                    continue;
            }
            break;
        }
    }

    /**
     * Clears the console screen.
     */
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                return;
            }
        } catch (final Exception e) {
            return;
        }
    }

    /**
     * Displays a menu with the specified title and options.
     * 
     * @param title   The title of the menu.
     * @param options The options to be displayed in the menu.
     */
    public static void displayMenu(String title, String... options) {
        System.out.println("\n" + title);
        int titleLength = title.length();
        for (int i = 0; i < titleLength; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Enter your choice: ");
    }

    /**
     * Prints a line of dashes.
     * 
     * @param amount The number of dashes to print.
     */
    public static void printDash(int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }

    /**
     * Exits the application.
     */
    public static void exit() {
        System.out.println("Exiting the Library Management System.");
        scanner.close();
        System.exit(0);
    }
}
