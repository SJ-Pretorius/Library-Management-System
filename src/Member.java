import java.util.regex.Pattern;

/**
 * Represents a member of the library with a name and email.
 */
public class Member {
    private String name;
    private String email;

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
}
