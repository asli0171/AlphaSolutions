package dk.alphasolutions.security;

import dk.alphasolutions.model.User;
import jakarta.servlet.http.HttpSession;

public class SecurityUtil {

    public static User getUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public static boolean isLoggedIn(HttpSession session) {
        return getUser(session) != null;
    }

    public static boolean isAdmin(HttpSession session) {
        User user = getUser(session);
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
    }

    public static boolean isUser(HttpSession session) {
        User user = getUser(session);
        return user != null && "USER".equalsIgnoreCase(user.getRole());
    }
}