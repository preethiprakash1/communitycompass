package com.example.javadb;

import com.example.javadb.model.CommunityGroup;
import com.example.javadb.model.User;
import com.example.javadb.model.UserCommunity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

  private User user;
  private Timestamp now;
  private CommunityGroup group1;
  private CommunityGroup group2;

  @BeforeEach
  void setUp() {
    now = new Timestamp(System.currentTimeMillis());

    // Initialize user
    user = new User("Adam Sandler", "adam@example.com", 58, User.Sex.MALE,
            37.7749, -122.4194, now, now);

    // Initialize community groups
    group1 = new CommunityGroup(
            "Group 1", CommunityGroup.CommunityType.EMPLOYMENT_ASSISTANCE,
            37.7749, -122.4194, 100,
            "First community group", now, now);

    group2 = new CommunityGroup(
            "Group 2", CommunityGroup.CommunityType.MENTAL_HEALTH,
            40.7128, -74.0060, 50,
            "Second community group", now, now);

    // Create UserCommunity relationships
    List<UserCommunity> communities = new ArrayList<>();
    communities.add(new UserCommunity(user, group1));
    communities.add(new UserCommunity(user, group2));

    // Set community membership
    user.setCommunityMembership(communities);
  }

  @Test
  void testConstructor() {
    assertEquals("Adam Sandler", user.getName());
    assertEquals("adam@example.com", user.getEmail());
    assertEquals(58, user.getAge());
    assertEquals(User.Sex.MALE, user.getSex());
    assertEquals(37.7749, user.getLatitude());
    assertEquals(-122.4194, user.getLongitude());
    assertEquals(now, user.getCreatedAt());
    assertEquals(now, user.getUpdatedAt());
  }

  @Test
  void testDefaultConstructorAndSetters() {
    User emptyUser = new User();
    emptyUser.setName("Oprah Winfrey");
    emptyUser.setEmail("oprah@example.com");
    emptyUser.setAge(70);
    emptyUser.setSex(User.Sex.FEMALE);
    emptyUser.setLatitude(40.7128);
    emptyUser.setLongitude(-74.0060);
    emptyUser.setCreatedAt(now);
    emptyUser.setUpdatedAt(now);

    assertEquals("Oprah Winfrey", emptyUser.getName());
    assertEquals("oprah@example.com", emptyUser.getEmail());
    assertEquals(70, emptyUser.getAge());
    assertEquals(User.Sex.FEMALE, emptyUser.getSex());
    assertEquals(40.7128, emptyUser.getLatitude());
    assertEquals(-74.0060, emptyUser.getLongitude());
    assertEquals(now, emptyUser.getCreatedAt());
    assertEquals(now, emptyUser.getUpdatedAt());
  }

  @Test
  void testSetNameWithEmptyString() {
    assertThrows(IllegalArgumentException.class, () -> user.setName(""));
  }

  @Test
  void testSetNameWithNull() {
    assertThrows(IllegalArgumentException.class, () -> user.setName(null));
  }

  @Test
  void testSetEmailWithInvalidEmail() {
    assertThrows(IllegalArgumentException.class, () -> user.setEmail("invalid-email"));
  }

  @Test
  void testSetAgeWithNegativeValue() {
    assertThrows(IllegalArgumentException.class, () -> user.setAge(-1));
  }

  @Test
  void testSetSexWithNull() {
    assertThrows(IllegalArgumentException.class, () -> user.setSex(null));
  }

  @Test
  void testGetNumberOfCommunitiesWithEmptyList() {
    user.setCommunityMembership(new ArrayList<>());
    assertEquals(0, user.getNumberOfCommunities());
  }

  @Test
  void testCommunityMembership() {
    assertEquals(2, user.getCommunityMembership().size());
    assertTrue(user.getCommunityMembership().stream()
            .anyMatch(uc -> uc.getCommunity().equals(group1)));
    assertTrue(user.getCommunityMembership().stream()
            .anyMatch(uc -> uc.getCommunity().equals(group2)));
  }

  @Test
  void testToString() {
    String expected = "User{" +
            "user_id=0" +
            ", name='Adam Sandler'" +
            ", email='adam@example.com'" +
            ", age=58" +
            ", sex=MALE" +
            ", latitude=37.7749" +
            ", longitude=-122.4194" +
            ", createdAt=" + now +
            ", updatedAt=" + now +
            ", communityMembership=" + user.getCommunityMembership() +
            '}';
    assertEquals(expected, user.toString());
  }
}
