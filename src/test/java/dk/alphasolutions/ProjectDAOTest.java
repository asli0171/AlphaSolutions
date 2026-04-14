package dk.alphasolutions.dao;

import dk.alphasolutions.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ProjectDAOTest {

    @Autowired
    private ProjectDAO projectDAO;

    @Test
    void getAll_returnerTestdata() {
        List<Project> projects = projectDAO.getAll();

        assertFalse(projects.isEmpty());
        assertEquals(2, projects.size());
    }

    @Test
    void getById_medGyldigId_returnerProjekt() {
        Project project = projectDAO.getById(1);

        assertNotNull(project);
        assertEquals("Project 1", project.getName());
        assertEquals("Customer 1", project.getCustomer());
    }

    @Test
    void getById_medUgyldigId_returnerNull() {
        Project project = projectDAO.getById(9999);

        assertNull(project);
    }

    @Test
    void create_gemmerProjektIDatabasen() {
        Project nyt = new Project(0, "Nyt Projekt", "Ny Kunde", "2026-05-01", "2026-06-01", "Aktiv");
        projectDAO.create(nyt);

        List<Project> projects = projectDAO.getAll();
        assertTrue(projects.stream().anyMatch(p -> p.getName().equals("Nyt Projekt")));
    }

    @Test
    void delete_fjernerProjektFraDatabasen() {
        projectDAO.delete(2);

        Project deleted = projectDAO.getById(2);
        assertNull(deleted);
    }
}