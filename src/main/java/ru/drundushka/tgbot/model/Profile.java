package ru.drundushka.tgbot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
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

    public Profile(Integer id, int tgId, String name, String lastName, String userName, boolean enabled) {
        super(id);
        this.tgId = tgId;
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.enabled = enabled;
    }

    public Profile() {
    }

    public int getTgId() {
        return tgId;
    }

    public void setTgId(int tgId) {
        this.tgId = tgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
