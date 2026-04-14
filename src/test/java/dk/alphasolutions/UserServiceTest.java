package dk.alphasolutions.service;

import dk.alphasolutions.dao.UserDAO;
import dk.alphasolutions.exception.ValidationException;
import dk.alphasolutions.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserDAO userDAO;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDAO = Mockito.mock(UserDAO.class);
        userService = new UserService(userDAO);
    }

    @Test
    void login_medKorrekteOplysninger_returnerBruger() {
        User mockUser = new User(1, "admin", "adminpassword", "ADMIN");
        when(userDAO.findByUsername("admin")).thenReturn(mockUser);

        User result = userService.login("admin", "adminpassword");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }

    @Test
    void login_medForkertAdgangskode_kasterValidationException() {
        User mockUser = new User(1, "admin", "adminpassword", "ADMIN");
        when(userDAO.findByUsername("admin")).thenReturn(mockUser);

        assertThrows(ValidationException.class, () ->
                userService.login("admin", "forkertpassword")
        );
    }

    @Test
    void login_medTomtBrugernavn_kasterValidationException() {
        assertThrows(ValidationException.class, () ->
                userService.login("", "password")
        );
    }

    @Test
    void login_medUkendtBruger_kasterValidationException() {
        when(userDAO.findByUsername("ukendt")).thenReturn(null);

        assertThrows(ValidationException.class, () ->
                userService.login("ukendt", "password")
        );
    }

    @Test
    void createUser_medTomtBrugernavn_kasterValidationException() {
        User user = new User(0, "", "password", "USER");

        assertThrows(ValidationException.class, () ->
                userService.createUser(user)
        );
    }

    @Test
    void createUser_medTomAdgangskode_kasterValidationException() {
        User user = new User(0, "nybruger", "", "USER");

        assertThrows(ValidationException.class, () ->
                userService.createUser(user)
        );
    }

    @Test
    void deleteUser_medUgyldigId_kasterValidationException() {
        assertThrows(ValidationException.class, () ->
                userService.deleteUser(-1)
        );
    }

    @Test
    void getAllUsers_returnerListe() {
        when(userDAO.getAll()).thenReturn(List.of(
                new User(1, "admin", "adminpassword", "ADMIN"),
                new User(2, "user1", "password1", "USER")
        ));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
    }
}