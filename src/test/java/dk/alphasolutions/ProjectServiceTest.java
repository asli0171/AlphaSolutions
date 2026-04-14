package dk.alphasolutions.service;

import dk.alphasolutions.dao.ProjectDAO;
import dk.alphasolutions.exception.ValidationException;
import dk.alphasolutions.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private ProjectDAO projectDAO;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectDAO = Mockito.mock(ProjectDAO.class);
        projectService = new ProjectService(projectDAO);
    }

    @Test
    void createProject_medGyldigData_kalderDAO() {
        Project project = new Project(0, "Projekt 1", "Kunde 1", "2026-04-01", "2026-05-01", "Aktiv");

        projectService.createProject(project);

        verify(projectDAO, times(1)).create(project);
    }

    @Test
    void createProject_medTomtNavn_kasterValidationException() {
        Project project = new Project(0, "", "Kunde 1", "2026-04-01", "2026-05-01", "Aktiv");

        assertThrows(ValidationException.class, () ->
                projectService.createProject(project)
        );
    }

    @Test
    void createProject_medTomKunde_kasterValidationException() {
        Project project = new Project(0, "Projekt 1", "", "2026-04-01", "2026-05-01", "Aktiv");

        assertThrows(ValidationException.class, () ->
                projectService.createProject(project)
        );
    }

    @Test
    void getAllProjects_returnerListe() {
        when(projectDAO.getAll()).thenReturn(List.of(
                new Project(1, "Projekt 1", "Kunde 1", "2026-04-01", "2026-05-01", "Aktiv"),
                new Project(2, "Projekt 2", "Kunde 2", "2026-04-10", "2026-06-01", "Aktiv")
        ));

        List<Project> projects = projectService.getAllProjects();

        assertEquals(2, projects.size());
    }

    @Test
    void deleteProject_medUgyldigId_kasterValidationException() {
        assertThrows(ValidationException.class, () ->
                projectService.deleteProject(0)
        );
    }

    @Test
    void getProject_medUgyldigId_kasterValidationException() {
        assertThrows(ValidationException.class, () ->
                projectService.getProject(-1)
        );
    }
}