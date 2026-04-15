package dk.alphasolutions.service;

import dk.alphasolutions.dao.TaskDAO;
import dk.alphasolutions.dao.TimeEntryDAO;
import dk.alphasolutions.model.Task;
import dk.alphasolutions.model.TimeEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;

@Service
public class TaskService {

    private final TaskDAO taskDAO;
    private final TimeEntryDAO timeEntryDAO;

    public TaskService(TaskDAO taskDAO, TimeEntryDAO timeEntryDAO) {
        this.taskDAO = taskDAO;
        this.timeEntryDAO = timeEntryDAO;
    }

    public void createTask(Task task) {
        taskDAO.create(task);
    }

    public List<Task> getTasksByProject(int projectId) {
        return taskDAO.getByProjectId(projectId);
    }

    public Task getTaskById(int id) {
        return taskDAO.getById(id);
    }

    public void updateTask(Task task) {
        taskDAO.update(task);
    }

    public void deleteTask(int id) {
        taskDAO.delete(id);
    }

    public double calculateTotalEstimatedHours(List<Task> tasks) {
        return tasks.stream().mapToDouble(Task::getEstimatedHours).sum();
    }

    public double calculateTotalHoursForProject(List<Integer> taskIds) {
        return taskIds.stream()
                .mapToDouble(id -> timeEntryDAO.getByTaskId(id)
                        .stream()
                        .mapToDouble(t -> t.getHours())
                        .sum())
                .sum();
    }

    public double calculateHoursPerWorkday(double remainingHours, String endDate) {
        LocalDate deadline = LocalDate.parse(endDate);
        LocalDate today = LocalDate.now();
        if (!today.isBefore(deadline)) return remainingHours;

        long workdays = today.datesUntil(deadline)
                .filter(d -> d.getDayOfWeek() != DayOfWeek.SATURDAY
                        && d.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();

        if (workdays == 0) return remainingHours;
        return Math.round((remainingHours / workdays) * 100.0) / 100.0;
    }

    public void addTimeEntry(int taskId, int userId, double hours, String date, String comment) {
        TimeEntry timeEntry = new TimeEntry(0, taskId, userId, hours, date, comment);
        timeEntryDAO.create(timeEntry);
    }
}