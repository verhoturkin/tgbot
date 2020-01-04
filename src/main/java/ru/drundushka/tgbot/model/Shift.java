package ru.drundushka.tgbot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.drundushka.tgbot.model.AbstractBaseEntity;
import ru.drundushka.tgbot.model.Profile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "shifts")
public class Shift extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Profile user;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "start", nullable = false)
    @NotNull
    private LocalTime start;

    @Column(name = "stop", nullable = false)
    @NotNull
    private LocalTime stop;

    public Shift(Integer id, Profile user, LocalDate date, LocalTime start, LocalTime stop) {
        super(id);
        this.user = user;
        this.date = date;
        this.start = start;
        this.stop = stop;
    }
}
