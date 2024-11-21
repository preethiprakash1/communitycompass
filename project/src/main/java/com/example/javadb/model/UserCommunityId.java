package com.example.javadb.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite key for UserCommunity.
 */
public class UserCommunityId implements Serializable {
    /** User ID. */
    private Integer user;
    /** Community group ID. */
    private Integer community;

    /**
     * Default no-args constructor.
     */
    public UserCommunityId() {
        // Empty constructor
    }

    /**
     * Constructs a UserCommunityId with the given user and community IDs.
     *
     * @param userId the user ID
     * @param communityId the community ID
     */
    public UserCommunityId(final Integer userId, final Integer communityId) {
        this.user = userId;
        this.community = communityId;
    }


    /**
     * Checks if the given object is equal to the UserCommunityId.
     *
     * @param o the object to compare
     * @return true if equal, otherwise false
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
         return true;
        }
        if (!(o instanceof UserCommunityId)) {
            return false;
        }
        UserCommunityId that = (UserCommunityId) o;
        return Objects.equals(user, that.user)
                && Objects.equals(community, that.community);
    }

    /**
     * Computes the hash code for this UserCommunityId.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(user, community);
    }
}
