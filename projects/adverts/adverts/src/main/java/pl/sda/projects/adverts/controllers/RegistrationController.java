package pl.sda.projects.adverts.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.projects.adverts.model.domain.User;
import pl.sda.projects.adverts.model.repository.UserRepository;

@Controller @Slf4j //logger
@RequestMapping("/register")   //zadanie http , localhost:8081/register
public class RegistrationController {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired   //userRepository bedzie teraz wstrzykniety przez Springa
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @GetMapping   //met. zostala powolana do obslugi zadania http
    public String prepareRegistrationPage() {
        return "/registration/registartion-form";
    }

    @PostMapping   //csrf ataki
    public String processRegistrationData(@RequestParam String username,
                                          @RequestParam String password,
                                          @RequestParam String firstName,
                                          @RequestParam String lastName,
                                          ModelMap model) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .active(true)
                .build();
        log.debug("User to save: {}", user);
        userRepository.save(user);
        log.info("New usser saved: ",user);

        model.addAttribute("savedUser",user);
        return "registration/registration-summary";
    }
}
