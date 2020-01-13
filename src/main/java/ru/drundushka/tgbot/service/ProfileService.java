package ru.drundushka.tgbot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.drundushka.tgbot.model.Profile;
import ru.drundushka.tgbot.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public int getStateById(int id) {
        Profile profile = repository.findByTgId(id);

        return profile == null ? 0 : profile.getState();
    }

    public Profile register(User user) {
        Profile profile = new Profile(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName());
        return repository.save(profile);
    }

    @Transactional
    public void setState(int id, int state) {
        Profile profile = repository.findByTgId(id);
        profile.setState(state);
        repository.save(profile);
    }
}
