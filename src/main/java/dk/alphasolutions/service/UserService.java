package dk.alphasolutions.service;

import dk.alphasolutions.dao.UserDAO;
import dk.alphasolutions.exception.ValidationException;
import dk.alphasolutions.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new ValidationException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new ValidationException("Invalid password");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public void createUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }
        userDAO.create(user);
    }

    public void updateCompetence(int id, String competence) {
        userDAO.updateCompetence(id, competence);
    }

    public void deleteUser(int id) {
        if (id <= 0) {
            throw new ValidationException("Invalid user id");
        }
        userDAO.delete(id);
    }
}