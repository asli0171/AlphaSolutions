package dk.alphasolutions.controller;

import dk.alphasolutions.model.Project;
import dk.alphasolutions.service.ProjectService;
import dk.alphasolutions.security.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/projects")
    public String showProjects(HttpSession session, Model model) {
        if (!SecurityUtil.isLoggedIn(session)) {
            return "redirect:/login";
        }
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/projects/new")
    public String createForm(HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        return "projects";
    }

    @PostMapping("/projects")
    public String createProject(@ModelAttribute Project project,
                                HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        projectService.createProject(project);
        return "redirect:/projects";
    }

    @PostMapping("/projects/update-status/{id}")
    public String updateStatus(@PathVariable int id,
                               @RequestParam String status,
                               HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        projectService.updateProjectStatus(id, status);
        return "redirect:/projects";
    }

    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable int id,
                                HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}