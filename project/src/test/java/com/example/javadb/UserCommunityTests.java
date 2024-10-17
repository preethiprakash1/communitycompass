package com.example.javadb;
import com.example.javadb.model.CommunityGroup;
import com.example.javadb.model.User;
import com.example.javadb.model.UserCommunity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCommunityTests {

  private User user;
  private CommunityGroup communityGroup;
  private UserCommunity userCommunity;

  @BeforeEach
  void setUp() {
    // Initialize User instance
    user = new User("Gail", "gail@example.com", 25, User.Sex.FEMALE,
            -37.7749, -122.4194,
            new java.sql.Timestamp(System.currentTimeMillis()),
            new java.sql.Timestamp(System.currentTimeMillis()));

    // Initialize CommunityGroup instance
    communityGroup = new CommunityGroup("Mental Health",
            CommunityGroup.CommunityType.MENTAL_HEALTH,
            37.7749, -122.4194,
            50, "Support group for mental health",
            new java.sql.Timestamp(System.currentTimeMillis()),
            new java.sql.Timestamp(System.currentTimeMillis()));

    // Initialize UserCommunity instance
    userCommunity = new UserCommunity(user, communityGroup);
  }

  @Test
  void testUserCommunityConstructor() {
    assertEquals(user, userCommunity.getUser(),
            "User in UserCommunity is not set correctly");
    assertEquals(communityGroup, userCommunity.getCommunity(),
            "Community in UserCommunity is not set correctly");
  }

  @Test
  void testSetUser() {
    User newUser = new User("Bob", "bob@example.com", 30, User.Sex.MALE,
            40.7128, -74.0060,
            new java.sql.Timestamp(System.currentTimeMillis()),
            new java.sql.Timestamp(System.currentTimeMillis()));
    userCommunity.setUser(newUser);

    assertEquals(newUser, userCommunity.getUser(),
            "User was not updated correctly");
  }

  @Test
  void testSetCommunity() {
    CommunityGroup newCommunity = new CommunityGroup("Employment Assistance",
            CommunityGroup.CommunityType.EMPLOYMENT_ASSISTANCE,
            40.7128, -74.0060,
            100, "Employment support group",
            new java.sql.Timestamp(System.currentTimeMillis()),
            new java.sql.Timestamp(System.currentTimeMillis()));
    userCommunity.setCommunity(newCommunity);

    assertEquals(newCommunity, userCommunity.getCommunity(),
            "Community was not updated correctly");
  }

  @Test
  void testGetUser() {
    assertEquals(user, userCommunity.getUser(),
            "getUser() did not return the correct user");
  }

  @Test
  void testGetCommunity() {
    assertEquals(communityGroup, userCommunity.getCommunity(),
            "getCommunity() did not return the correct community");
  }
}
