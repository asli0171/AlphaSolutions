package dk.alphasolutions.dao;

import dk.alphasolutions.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TaskDAOTest {

    @Autowired
    private TaskDAO taskDAO;

    @Test
    void getByProjectId_returnerOpgaverForProjekt() {
        List<Task> tasks = taskDAO.getByProjectId(1);

        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().anyMatch(t -> t.getName().equals("Task 1")));
        assertTrue(tasks.stream().anyMatch(t -> t.getName().equals("Task 2")));
    }

    @Test
    void getById_medGyldigId_returnerOpgave() {
        Task task = taskDAO.getById(1);

        assertNotNull(task);
        assertEquals("Task 1", task.getName());
        assertEquals(1, task.getProjectId());
    }

    @Test
    void getById_medUgyldigId_returnerNull() {
        Task task = taskDAO.getById(9999);

        assertNull(task);
    }

    @Test
    void create_gemmerNyOpgaveIDatabasen() {
        Task nyTask = new Task(0, 1, "Ny Opgave", "Ny beskrivelse", 0, null, null);
        taskDAO.create(nyTask);

        List<Task> tasks = taskDAO.getByProjectId(1);
        assertTrue(tasks.stream().anyMatch(t -> t.getName().equals("Ny Opgave")));
    }

    @Test
    void delete_fjernOpgaveFraDatabasen() {
        taskDAO.delete(3);

        Task deleted = taskDAO.getById(3);
        assertNull(deleted);
    }
}