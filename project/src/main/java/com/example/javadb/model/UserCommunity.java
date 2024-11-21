package com.example.javadb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the relationship between a User and a CommunityGroup.
 * Corresponds to the "user_community" table.
 */
@Entity
@Table(name = "user_community")
@IdClass(UserCommunityId.class)
public class UserCommunity implements Serializable {

    /** The user associated with the community. */
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    /** The community group associated with the user. */
    @Id
    @ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    @JsonIgnore
    private CommunityGroup community;

    /**
     * Default no-args constructor.
     */
    public UserCommunity() {
        // Empty constructor
    }

    /**
     * Constructs relationship with the specified user and community.
     *
     * @param userParam user associated with the community
     * @param communityParam community group associated with the user
     */
    public UserCommunity(final User userParam,
                         final CommunityGroup communityParam) {
        this.user = userParam;
        this.community = communityParam;
    }

    /**
     * Gets user associated with this relationship.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user associated with this relationship.
     *
     * @param newUser the user to set
     */
    public void setUser(final User newUser) {
        this.user = newUser;
    }

    /**
     * Gets community group associated with this relationship.
     *
     * @return the community group
     */
    public CommunityGroup getCommunity() {
        return community;
    }

    /**
     * Sets community group associated with this relationship.
     *
     * @param newCommunity the community group to set
     */
    public void setCommunity(final CommunityGroup newCommunity) {
        this.community = newCommunity;
    }
}
