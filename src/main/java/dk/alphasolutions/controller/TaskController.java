package dk.alphasolutions.controller;

import dk.alphasolutions.model.Task;
import dk.alphasolutions.service.TaskService;
import dk.alphasolutions.service.TimeEntryService;
import dk.alphasolutions.security.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final TimeEntryService timeEntryService;

    public TaskController(TaskService taskService,
                          TimeEntryService timeEntryService) {
        this.taskService = taskService;
        this.timeEntryService = timeEntryService;
    }

    @GetMapping("/tasks/{projectId}")
    public String showTasks(@PathVariable int projectId,
                            HttpSession session,
                            Model model) {
        if (!SecurityUtil.isLoggedIn(session)) {
            return "redirect:/login";
        }

        List<Task> tasks = taskService.getTasksByProject(projectId);
        List<Integer> taskIds = tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toList());

        double totalHours = timeEntryService.calculateTotalHoursForProject(taskIds);

        model.addAttribute("tasks", tasks);
        model.addAttribute("projectId", projectId);
        model.addAttribute("totalHours", totalHours);

        return "tasks";
    }

    @PostMapping("/tasks")
    public String createTask(@ModelAttribute Task task,
                             HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/tasks/" + task.getProjectId();
        }
        taskService.createTask(task);
        return "redirect:/tasks/" + task.getProjectId();
    }

    @GetMapping("/tasks/delete/{id}/{projectId}")
    public String deleteTask(@PathVariable int id,
                             @PathVariable int projectId,
                             HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/tasks/" + projectId;
        }
        taskService.deleteTask(id);
        return "redirect:/tasks/" + projectId;
    }
}