package dk.alphasolutions.controller;

import dk.alphasolutions.model.Project;
import dk.alphasolutions.model.Task;
import dk.alphasolutions.model.User;
import dk.alphasolutions.service.ProjectService;
import dk.alphasolutions.service.TaskService;
import dk.alphasolutions.service.TimeEntryService;
import dk.alphasolutions.security.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final TimeEntryService timeEntryService;

    public ProjectController(ProjectService projectService, TaskService taskService, TimeEntryService timeEntryService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.timeEntryService = timeEntryService;
    }

    @GetMapping("/projects")
    public String showProjects(HttpSession session, Model model) {
        if (!SecurityUtil.isLoggedIn(session)) {
            return "redirect:/login";
        }
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("isAdmin", SecurityUtil.isAdmin(session));
        return "projects";
    }

    @PostMapping("/projects")
    public String createProject(@ModelAttribute Project project, HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        projectService.createProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable int id, HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @PostMapping("/projects/update-status/{id}")
    public String updateStatus(@PathVariable int id, @RequestParam String status, HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        projectService.updateProjectStatus(id, status);
        return "redirect:/projects";
    }

    @GetMapping("/projects/{id}/tasks")
    public String showTasks(@PathVariable int id, HttpSession session, Model model) {
        if (!SecurityUtil.isLoggedIn(session)) {
            return "redirect:/login";
        }
        Project project = projectService.getProject(id);
        List<Task> tasks = taskService.getTasksByProject(id);

        List<Integer> taskIds = tasks.stream().map(Task::getId).toList();
        double totalUsedHours = taskService.calculateTotalHoursForProject(taskIds);
        double totalEstimatedHours = taskService.calculateTotalEstimatedHours(tasks);
        double remainingHours = Math.max(0, totalEstimatedHours - totalUsedHours);
        double hoursPerWorkday = taskService.calculateHoursPerWorkday(remainingHours, project.getEndDate());

        Map<String, Double> hoursByCompetence = timeEntryService.calculateHoursByCompetence(taskIds);

        if (project.getStartDate() != null && project.getEndDate() != null) {
            LocalDate projectStart = LocalDate.parse(project.getStartDate());
            LocalDate projectEnd = LocalDate.parse(project.getEndDate());
            long projectDays = ChronoUnit.DAYS.between(projectStart, projectEnd);

            for (Task task : tasks) {
                if (task.getStartDate() != null && task.getEndDate() != null) {
                    LocalDate taskStart = LocalDate.parse(task.getStartDate());
                    LocalDate taskEnd = LocalDate.parse(task.getEndDate());
                    long daysFromStart = ChronoUnit.DAYS.between(projectStart, taskStart);
                    long taskDuration = ChronoUnit.DAYS.between(taskStart, taskEnd);
                    double left = projectDays > 0 ? (daysFromStart * 100.0 / projectDays) : 0;
                    double width = projectDays > 0 ? (taskDuration * 100.0 / projectDays) : 0;
                    task.setGanttLeft(Math.max(0, Math.min(left, 100)));
                    task.setGanttWidth(Math.max(1, Math.min(width, 100 - left)));
                }
            }
        }

        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        model.addAttribute("totalUsedHours", totalUsedHours);
        model.addAttribute("totalEstimatedHours", totalEstimatedHours);
        model.addAttribute("remainingHours", remainingHours);
        model.addAttribute("hoursPerWorkday", hoursPerWorkday);
        model.addAttribute("hoursByCompetence", hoursByCompetence);
        model.addAttribute("isAdmin", SecurityUtil.isAdmin(session));
        return "tasks";
    }

    @PostMapping("/projects/{id}/tasks")
    public String createTask(@PathVariable int id, @ModelAttribute Task task, HttpSession session) {
        if (!SecurityUtil.isLoggedIn(session)) {
            return "redirect:/login";
        }
        task.setProjectId(id);
        taskService.createTask(task);
        return "redirect:/projects/" + id + "/tasks";
    }

    @GetMapping("/projects/{projectId}/tasks/delete/{taskId}")
    public String deleteTask(@PathVariable int projectId, @PathVariable int taskId, HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects/" + projectId + "/tasks";
        }
        taskService.deleteTask(taskId);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/time")
    public String addTimeEntry(@PathVariable int projectId, @PathVariable int taskId,
                               @RequestParam double hours, @RequestParam String date,
                               @RequestParam String comment,
                               HttpSession session) {
        if (!SecurityUtil.isLoggedIn(session)) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        taskService.addTimeEntry(taskId, userId, hours, date, comment);
        return "redirect:/projects/" + projectId + "/tasks";
    }
}