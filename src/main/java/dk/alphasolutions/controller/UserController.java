package dk.alphasolutions.controller;

import dk.alphasolutions.model.User;
import dk.alphasolutions.service.UserService;
import dk.alphasolutions.security.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(HttpSession session, Model model) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


    @PostMapping("/users")
    public String createUser(@ModelAttribute User user, HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id, HttpSession session) {
        if (!SecurityUtil.isAdmin(session)) {
            return "redirect:/projects";
        }
        userService.deleteUser(id);
        return "redirect:/users";
    }
}