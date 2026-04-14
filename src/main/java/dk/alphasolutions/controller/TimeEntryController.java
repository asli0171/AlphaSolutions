package dk.alphasolutions.controller;

import dk.alphasolutions.model.TimeEntry;
import dk.alphasolutions.service.TimeEntryService;
import dk.alphasolutions.security.SecurityUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @PostMapping("/time")
    public String addTime(@ModelAttribute TimeEntry entry,
                          HttpSession session) {
        if (!SecurityUtil.isLoggedIn(session)) {
            return "redirect:/login";
        }
        timeEntryService.addTimeEntry(entry);
        return "redirect:/tasks/" + entry.getTaskId();
    }
}