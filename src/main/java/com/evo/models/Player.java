package com.evo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer Health;

    private Integer Mana;

    private Integer Stamina;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHealth() {
        return Health;
    }

    public void setHealth(Integer health) {
        Health = health;
    }

    public Integer getMana() {
        return Mana;
    }

    public void setMana(Integer mana) {
        Mana = mana;
    }

    public Integer getStamina() {
        return Stamina;
    }

    public void setStamina(Integer stamina) {
        Stamina = stamina;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((Health == null) ? 0 : Health.hashCode());
        result = prime * result + ((Mana == null) ? 0 : Mana.hashCode());
        result = prime * result + ((Stamina == null) ? 0 : Stamina.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (Health == null) {
            if (other.Health != null)
                return false;
        } else if (!Health.equals(other.Health))
            return false;
        if (Mana == null) {
            if (other.Mana != null)
                return false;
        } else if (!Mana.equals(other.Mana))
            return false;
        if (Stamina == null) {
            if (other.Stamina != null)
                return false;
        } else if (!Stamina.equals(other.Stamina))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
}
