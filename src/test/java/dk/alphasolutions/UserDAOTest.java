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
    void findByUsername_medEksisterendeBruger_returnerBruger() {
        User user = userDAO.findByUsername("admin");

        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void findByUsername_medUkendtBruger_returnerNull() {
        User user = userDAO.findByUsername("findesikke");

        assertNull(user);
    }

    @Test
    void getAll_returnerAlleTestbrugere() {
        List<User> users = userDAO.getAll();

        assertEquals(3, users.size());
    }

    @Test
    void create_gemmerNyBrugerIDatabasen() {
        User nyBruger = new User(0, "nybruger", "nypassword", "USER");
        userDAO.create(nyBruger);

        User found = userDAO.findByUsername("nybruger");
        assertNotNull(found);
        assertEquals("USER", found.getRole());
    }

    @Test
    void delete_fjerneBrugerFraDatabasen() {
        userDAO.delete(3);

        User deleted = userDAO.findByUsername("user2");
        assertNull(deleted);
    }
}