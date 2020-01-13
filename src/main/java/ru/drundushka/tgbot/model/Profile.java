package ru.drundushka.tgbot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "tg_id", name = "users_tg_id_idx")})
public class Profile extends AbstractBaseEntity {

    @Column(name = "tg_id")
    private int tgId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String userName;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "state", nullable = false)
    private int state;

    public Profile(Integer id, int tgId, String name, String lastName, String userName, boolean enabled, int state) {
        super(id);
        this.tgId = tgId;
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.enabled = enabled;
        this.state = state;
    }

    public Profile(int tgId, String name, String lastName, String userName) {
        super(null);
        this.tgId = tgId;
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.enabled = true;
        this.state = 1;
    }
}