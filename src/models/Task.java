package models;

public class Task {
    private int    id;
    private String projectName;
    private String title;
    private String description;
    private String dueDate;   // or LocalDate
    private String status;
    private String assignee;

    // CTMSServer called new Task(projectName, title, description, dueDate, status, assignee)
    public Task(String projectName,
                String title,
                String description,
                String dueDate,
                String status,
                String assignee)
    {
        this.projectName = projectName;
        this.title       = title;
        this.description = description;
        this.dueDate     = dueDate;
        this.status      = status;
        this.assignee    = assignee;
    }

    // full constructor (for reading from DB)
    public Task(int id, String projectName, String title,
                String description, String dueDate,
                String status, String assignee)
    {
        this(projectName, title, description, dueDate, status, assignee);
        this.id = id;
    }

    // getters
    public int    getId()          { return id; }
    public String getProjectName(){ return projectName; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public String getDueDate()     { return dueDate; }
    public String getStatus()      { return status; }
    public String getAssignee()    { return assignee; }
}
