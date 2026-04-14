package models;

public class Project {
    private int    id;
    private String name;
    private String owner;

    // CTMSServer called new Project(projectName, owner)
    public Project(String name, String owner) {
        this.name  = name;
        this.owner = owner;
    }

    // full constructor (for reading from DB)
    public Project(int id, String name, String owner) {
        this.id    = id;
        this.name  = name;
        this.owner = owner;
    }

    // getters/setters
    public int    getId()    { return id; }
    public String getName()  { return name; }
    public String getOwner() { return owner; }
}
