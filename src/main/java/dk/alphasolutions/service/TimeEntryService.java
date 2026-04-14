package dk.alphasolutions.service;

import dk.alphasolutions.dao.TimeEntryDAO;
import dk.alphasolutions.model.TimeEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeEntryService {

    private final TimeEntryDAO timeEntryDAO;

    public TimeEntryService(TimeEntryDAO timeEntryDAO) {
        this.timeEntryDAO = timeEntryDAO;
    }

    public void addTimeEntry(TimeEntry entry) {
        timeEntryDAO.create(entry);
    }

    public List<TimeEntry> getByTask(int taskId) {
        return timeEntryDAO.getByTaskId(taskId);
    }

    public void deleteTimeEntry(int id) {
        timeEntryDAO.delete(id);
    }

    public void updateTimeEntry(TimeEntry entry) {
        timeEntryDAO.update(entry);
    }

    public double calculateTotalHours(int taskId) {
        List<TimeEntry> entries = timeEntryDAO.getByTaskId(taskId);
        double total = 0;
        for (TimeEntry e : entries) {
            total += e.getHours();
        }
        return total;
    }

    public double calculateTotalHoursForProject(List<Integer> taskIds) {
        double total = 0;
        for (int taskId : taskIds) {
            total += calculateTotalHours(taskId);
        }
        return total;
    }
}