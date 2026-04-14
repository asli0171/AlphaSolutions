package dk.alphasolutions.model;

public class Task {

    private int id;
    private int projectId;
    private String name;
    private String description;

    public Task() {}

    public Task(int id, int projectId, String name, String description) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}