package dk.alphasolutions.model;

public class Task {

    private int id;
    private int projectId;
    private String name;
    private String description;
    private double estimatedHours;
    private String startDate;
    private String endDate;
    private double ganttLeft;
    private double ganttWidth;

    public Task() {}

    public Task(int id, int projectId, String name, String description, double estimatedHours, String startDate, String endDate) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(double estimatedHours) { this.estimatedHours = estimatedHours; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public double getGanttLeft() { return ganttLeft; }
    public void setGanttLeft(double ganttLeft) { this.ganttLeft = ganttLeft; }

    public double getGanttWidth() { return ganttWidth; }
    public void setGanttWidth(double ganttWidth) { this.ganttWidth = ganttWidth; }
}