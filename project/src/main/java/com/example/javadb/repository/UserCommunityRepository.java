/**
 * This package contains repositories for managing User-Community associations
 * in the application.
 */
package com.example.javadb.repository;

import com.example.javadb.model.CommunityGroup;
import com.example.javadb.model.User;
import com.example.javadb.model.UserCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing UserCommunity entities.
 */
public interface UserCommunityRepository
    extends JpaRepository<UserCommunity, Integer> {
    /**
     * Checks if a UserCommunity relationship
     * exists for the given user and community.
     *
     * @param user the user entity
     * @param community the community group entity
     * @return true if the relationship exists, false otherwise
     */
    boolean existsByUserAndCommunity(User user, CommunityGroup community);
    /**
     * Gets the UserCommunity relationship for the given user and community.
     *
     * @param user the user entity
     * @param community the community group entity
     * @return the UserCommunity entity
     */
    UserCommunity findByUserAndCommunity(User user, CommunityGroup community);
}
