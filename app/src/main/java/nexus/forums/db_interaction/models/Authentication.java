package nexus.forums.db_interaction.models;

import jakarta.persistence.*;

@Entity
@Table(name = "authentication", schema = "main_schema")
public class Authentication {
    @Id
    @Column(name = "userId")
    private Long userId;

    @Column(name = "passwordHash", nullable = false, length = 60)
    private String passwordHash;

    @MapsId
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
