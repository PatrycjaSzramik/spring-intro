package pl.sda.projects.adverts.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.projects.adverts.model.domain.Advert;
import pl.sda.projects.adverts.model.domain.User;
import pl.sda.projects.adverts.model.repository.AdvertRepository;
import pl.sda.projects.adverts.model.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j //logger
@RequestMapping("/")
@RequiredArgsConstructor
public class HomePageController {

    private AdvertRepository advertRepository;
    private UserRepository userRepository;

    @Autowired
    public HomePageController(AdvertRepository advertRepository, UserRepository userRepository) {
        this.advertRepository = advertRepository;
        this.userRepository=userRepository;
    }

    @GetMapping
    public String prepareHomePage(Model model) {
        List<Advert> allAdverts = advertRepository.findAllByOrderByPostedDesc();
        model.addAttribute("advertToAdd", new Advert());
        model.addAttribute("adverts", allAdverts);
        return "home-page";
    }

    @PostMapping("/add-advert")
    public String processAddAdvert(@RequestParam String title,
                                   @RequestParam String description) {

        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser=userRepository.getByUsername(username);

        Advert advertToSave=Advert.builder()
                .title(title)
                .description(description)
                .posted(LocalDateTime.now())
                .user_id(currentUser)
                .build();

        log.debug("Advert to save: {}",advertToSave);
        advertRepository.save(advertToSave);
        log.debug("Saved advert: {}",advertToSave);
        return "redirect:/";
    }


}
