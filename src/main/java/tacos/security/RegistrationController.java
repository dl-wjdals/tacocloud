package tacos.security;

import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tacos.data.UserRepository;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserRepository userRepository,
            org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        if (!model.containsAttribute("registrationForm")) {
            model.addAttribute("registrationForm", new RegistrationForm());
        }
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute RegistrationForm form, Model model) {
        if (userRepository.findByUsername(form.getUsername()) != null) {
            model.addAttribute("registrationErrorMessage", "Username already exists.");
            model.addAttribute("registrationForm", form);
            return "registration";
        }

        if (!Objects.equals(form.getPassword(), form.getConfirmPassword())) {
            model.addAttribute("registrationErrorMessage", "Passwords do not match.");
            model.addAttribute("registrationForm", form);
            return "registration";
        }

        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
