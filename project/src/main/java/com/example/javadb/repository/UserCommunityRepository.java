/**
 * This package contains repositories for managing User-Community associations
 * in the application.
 */
package com.example.javadb.repository;

import com.example.javadb.model.UserCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommunityRepository
    extends JpaRepository<UserCommunity, Integer> {
  boolean existsByUserIdAndCommunityId(int userId, int communityId);
  UserCommunity findByUserIdAndCommunityId(int userId, int communityId);
}
