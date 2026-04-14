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
            throw new ValidationException("Username required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password required");
        }
        User user = userDAO.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new ValidationException("Invalid login");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public void createUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password required");
        }
        userDAO.create(user);
    }

    public void deleteUser(int id) {
        if (id <= 0) {
            throw new ValidationException("Invalid user id");
        }
        userDAO.delete(id);
    }
}