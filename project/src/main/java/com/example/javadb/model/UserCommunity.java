package com.example.javadb.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_community")
@IdClass(UserCommunityId.class)
public class UserCommunity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    private CommunityGroup community;

    // No-args constructor for JPA Entity
    public UserCommunity() {}

    public UserCommunity(User user, CommunityGroup community) {
        this.user = user;
        this.community = community;
    }

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommunityGroup getCommunity() {
        return community;
    }

    public void setCommunity(CommunityGroup community) {
        this.community = community;
    }
}
