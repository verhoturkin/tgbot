package ru.drundushka.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.drundushka.tgbot.model.Shift;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
}
