import java.util.regex.Pattern;

/**
 * Represents a member of the library with a name, email, and fines.
 */
public class Member {
    private String name;
    private String email;
    private int fines = 0;

    /**
     * Constructs a new Member object with the specified name and email.
     * 
     * @param name  The name of the member.
     * @param email The email of the member.
     * @throws IllegalArgumentException If the name or email is empty, or if the email format is invalid.
     */
    public Member(String name, String email) {
        this.name = name;
        this.email = email;

        // Validate input parameters
        if (name.equals("")) {
            throw new IllegalArgumentException("The name of the member was not provided.");
        }

        if (email.equals("")) {
            throw new IllegalArgumentException("The email of the member was not provided.");
        }

        // Validate email format using regular expressions
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    /**
     * Constructs a new Member object with the specified name, email, and fines.
     * 
     * @param name  The name of the member.
     * @param email The email of the member.
     * @param fines The fines associated with the member.
     */
    public Member(String name, String email, int fines) {
        this.name = name;
        this.email = email;
        this.fines = fines;
    }

    /**
     * Gets the name of the member.
     * 
     * @return The name of the member.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the email of the member.
     * 
     * @return The email of the member.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Increments fines for the member.
     * 
     * @param fine The amount of fine to increment.
     * @return The total fines after increment.
     */
    public int incrementFines(int fine) {
        fines += fine;
        return fines;
    }

    /**
     * Gets the fines associated with the member.
     * 
     * @return The fines associated with the member.
     */
    public int getFines() {
        return fines;
    }

    /**
     * Decrements fines for the member.
     * 
     * @param fine The amount of fine to decrement.
     * @return The total fines after decrement.
     */
    public int decrementFines(int fine) {
        fines -= fine;
        return fines;
    }
}
