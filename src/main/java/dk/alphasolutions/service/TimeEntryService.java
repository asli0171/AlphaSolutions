package dk.alphasolutions.service;

import dk.alphasolutions.dao.TimeEntryDAO;
import dk.alphasolutions.dao.UserDAO;
import dk.alphasolutions.model.TimeEntry;
import dk.alphasolutions.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimeEntryService {

    private final TimeEntryDAO timeEntryDAO;
    private final UserDAO userDAO;

    public TimeEntryService(TimeEntryDAO timeEntryDAO, UserDAO userDAO) {
        this.timeEntryDAO = timeEntryDAO;
        this.userDAO = userDAO;
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

    public Map<String, Double> calculateHoursByCompetence(List<Integer> taskIds) {
        Map<String, Double> hoursByCompetence = new HashMap<>();
        for (int taskId : taskIds) {
            List<TimeEntry> entries = timeEntryDAO.getByTaskId(taskId);
            for (TimeEntry entry : entries) {
                User user = userDAO.getById(entry.getUserId());
                String competence = (user != null && user.getCompetence() != null)
                        ? user.getCompetence() : "Ukendt";
                hoursByCompetence.merge(competence, entry.getHours(), Double::sum);
            }
        }
        return hoursByCompetence;
    }
}