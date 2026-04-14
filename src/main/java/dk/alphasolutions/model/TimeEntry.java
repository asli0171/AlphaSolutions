package dk.alphasolutions.model;

public class TimeEntry {

    private int id;
    private int taskId;
    private int userId;
    private double hours;
    private String date;

    public TimeEntry() {}

    public TimeEntry(int id, int taskId, int userId, double hours, String date) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.hours = hours;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getHours() { return hours; }
    public void setHours(double hours) { this.hours = hours; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}