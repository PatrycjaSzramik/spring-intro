package pl.sda.projects.adverts.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.sda.projects.adverts.model.domain.Advert;
import pl.sda.projects.adverts.model.domain.User;
import pl.sda.projects.adverts.model.repository.AdvertRepository;
import pl.sda.projects.adverts.model.repository.UserRepository;

@Component @Slf4j
@RequiredArgsConstructor // konstruktor dla wszystkich pol typu final
public class StartupDataLoader {
    private final UserRepository userRepository;  //mamy wygenerowany konstruktor dla wszystkich pol typu final przez lomboka wiec nie tworzymy wlasnego,
                                                    //a jak mamy jeden to Spring automatycznie uzywa go do wstrzykniecia zaleznosci
    private final PasswordEncoder passwordEncoder;
    private AdvertRepository advertRepository;

    @EventListener
    public void onStartupPrepareData(ContextRefreshedEvent event) {
        log.info("Loading startup data...");
        userRepository.save(User.builder()
                .firstName("Dupa")
                .lastName("Dupa")
                .username("andrzej")
                .password(passwordEncoder.encode("andrzej"))
                .active(true)
                .build()
        );
//        advertRepository.save(Advert.builder()
//                .description("Oddam rower")
//                .title("Oddam")
//                .build()
//        );
        log.info("Startup data loaded");
    }
}
