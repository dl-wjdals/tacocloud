package tacos.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        Object loginErrorMessage = session.getAttribute(LOGIN_ERROR_MESSAGE);

        if (loginErrorMessage != null) {
            model.addAttribute("loginErrorMessage", loginErrorMessage);
            session.removeAttribute(LOGIN_ERROR_MESSAGE);
        }

        return "login";
    }
}
