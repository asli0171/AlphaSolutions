package dk.alphasolutions.service;

import dk.alphasolutions.dao.ProjectDAO;
import dk.alphasolutions.exception.ValidationException;
import dk.alphasolutions.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectDAO projectDAO;

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public void createProject(Project project) {
        validateProject(project);
        projectDAO.create(project);
    }

    public List<Project> getAllProjects() {
        return projectDAO.getAll();
    }

    public Project getProject(int id) {
        if (id <= 0) {
            throw new ValidationException("Invalid project id");
        }
        return projectDAO.getById(id);
    }

    public void updateProject(Project project) {
        if (project.getId() <= 0) {
            throw new ValidationException("Invalid project id");
        }
        validateProject(project);
        projectDAO.update(project);
    }

    public void updateProjectStatus(int id, String status) {
        if (id <= 0) {
            throw new ValidationException("Invalid project id");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new ValidationException("Status is required");
        }
        projectDAO.updateStatus(id, status);
    }

    public void deleteProject(int id) {
        if (id <= 0) {
            throw new ValidationException("Invalid project id");
        }
        projectDAO.delete(id);
    }

    private void validateProject(Project project) {
        if (project == null) {
            throw new ValidationException("Project cannot be null");
        }
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new ValidationException("Project name is required");
        }
        if (project.getCustomer() == null || project.getCustomer().trim().isEmpty()) {
            throw new ValidationException("Customer is required");
        }
    }
}