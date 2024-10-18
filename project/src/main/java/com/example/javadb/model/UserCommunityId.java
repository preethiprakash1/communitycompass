package com.example.javadb.model;

import java.io.Serializable;
import java.util.Objects;

public class UserCommunityId implements Serializable {
    private Integer user;  // Assuming User has a Long id
    private Integer community;  // Assuming CommunityGroup has a Long id

    public UserCommunityId() {}

    public UserCommunityId(Integer user, Integer community) {
        this.user = user;
        this.community = community;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCommunityId)) return false;
        UserCommunityId that = (UserCommunityId) o;
        return Objects.equals(user, that.user) && Objects.equals(community, that.community);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, community);
    }
}