package models;

public class User {
    private int    id;
    private String username;
    private String password;
    private String fullName;

    // used when reading from DB
    public User(int id, String username, String password, String fullName) {
        this.id       = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    // used for creating a new user (ID is auto‑generated)
    public User(String username, String password, String fullName) {
        this(0, username, password, fullName);
    }

    public int    getId()          { return id; }
    public String getUsername()    { return username; }
    public String getPassword()    { return password; }
    public String getFullName()    { return fullName; }
}
