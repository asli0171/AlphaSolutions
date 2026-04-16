package dk.alphasolutions.dao;

import dk.alphasolutions.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    void findByUsername_existingUser_returnsUser() {
        User user = userDAO.findByUsername("admin");

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void findByUsername_unknownUser_returnsNull() {
        User user = userDAO.findByUsername("findesikke");

        assertNull(user);
    }

    @Test
    void getAll_returnsUsers() {
        List<User> users = userDAO.getAll();

        assertTrue(users.size() >= 3);
    }

    @Test
    void create_addsUser() {
        User nyBruger = new User(0, "nybruger", "nypassword", "USER", "Backend");

        userDAO.create(nyBruger);

        User found = userDAO.findByUsername("nybruger");

        assertNotNull(found);
        assertEquals("USER", found.getRole());
    }

    @Test
    void delete_removesUser() {
        User user = userDAO.findByUsername("user2");

        assertNotNull(user);

        userDAO.delete(user.getId());

        User deleted = userDAO.findByUsername("user2");

        assertNull(deleted);
    }

    @Test
    void updateCompetence_opdatererKompetence() {
        User user = userDAO.findByUsername("user1");

        assertNotNull(user);

        userDAO.updateCompetence(user.getId(), "Frontend");

        User updated = userDAO.findByUsername("user1");

        assertNotNull(updated);
        assertEquals("Frontend", updated.getCompetence());
    }
}