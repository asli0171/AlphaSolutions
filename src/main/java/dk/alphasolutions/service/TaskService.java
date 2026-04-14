package dk.alphasolutions.service;

import dk.alphasolutions.dao.TaskDAO;
import dk.alphasolutions.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
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
}