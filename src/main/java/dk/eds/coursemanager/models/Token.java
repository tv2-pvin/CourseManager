package dk.eds.coursemanager.models;

import dk.eds.coursemanager.repositories.TokenRepository;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;
    @Column(nullable = false)
    private boolean isDeleted;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Token() {
    }

    public Token(String token, Date createdAt, Date updatedAt, boolean isDeleted, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Token generate(User user) {
        Token t = new Token();
        Date now = new Date();
        t.setCreatedAt(now);
        t.setDeleted(false);
        t.setUpdatedAt(now);
        t.setUser(user);
        t.setToken(UUID.randomUUID().toString());
        return t;
    }

    public static boolean validate(String token, TokenRepository tokenRepository, String username) {
        return !StringUtils.isEmpty(token) && !StringUtils.containsWhitespace(token) &&
                !StringUtils.isEmpty(username) && !StringUtils.containsWhitespace(username) &&
                tokenRepository.getByTokenAndIsDeletedFalse(token) != null &&
                tokenRepository.getByTokenAndIsDeletedFalse(token).getUser().getUsername().equals(username);
    }
}
