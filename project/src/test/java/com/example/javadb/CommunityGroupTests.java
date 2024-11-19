package com.example.javadb;

import com.example.javadb.model.CommunityGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class CommunityGroupTests {

  private CommunityGroup group;
  private Timestamp now;

  @BeforeEach
  void setUp() {
    now = new Timestamp(System.currentTimeMillis());
    group = new CommunityGroup("Mental Health", CommunityGroup.CommunityType.MENTAL_HEALTH,
            37.7749, -122.4194, 50, "Support group for mental health", now, now);
  }

  @Test
  void testSetCommunityNameWithValidName() {
    group.setCommunityName("Employment Assistance");
    assertEquals("Employment Assistance", group.getCommunityName());
  }

  @Test
  void testSetCommunityNameWithNull() {
    assertThrows(IllegalArgumentException.class,
            () -> group.setCommunityName(null));
  }

  @Test
  void testSetCapacityWithNegativeValue() {
    assertThrows(IllegalArgumentException.class,
            () -> group.setCapacity(-1));
  }

  @Test
  void testGetNumberOfUsersWithNoUsers() {
    assertEquals(0, group.getNumberOfUsers());
  }

  @Test
  void testSetCommunityTypeWithNull() {
    assertThrows(IllegalArgumentException.class,
            () -> group.setCommunityType(null));
  }

  @Test
  void testSetLongitudeBelowBound() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> group.setLongitude(-200.0));
    assertEquals("Longitude must be between -180 and 180", exception.getMessage());
  }

  @Test
  void testSetLongitudeAboveBound() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> group.setLongitude(200.0));
    assertEquals("Longitude must be between -180 and 180", exception.getMessage());
  }

  @Test
  void testSetLatitudeBelowBound() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> group.setLatitude(-100.0));
    assertEquals("Latitude must be between -90 and 90", exception.getMessage());
  }

  @Test
  void testSetLatitudeAboveBound() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> group.setLatitude(100.0));
    assertEquals("Latitude must be between -90 and 90", exception.getMessage());
  }

  @Test
  void testSetCommunityNameWithEmptyString() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> group.setCommunityName(""));
    assertEquals("Community name cannot be null or empty", exception.getMessage());
  }

  @Test
  void testSetCommunityNameWithWhitespace() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> group.setCommunityName("   "));
    assertEquals("Community name cannot be null or empty", exception.getMessage());
  }

  @Test
  void testToString() {
    group.setUserCommunities(Collections.emptyList());
    String expected = "CommunityGroup{communityId=" + group.getCommunityId()
            + ", communityName='Mental Health', communityType=MENTAL_HEALTH"
            + ", latitude=37.7749, longitude=-122.4194"
            + ", capacity=50, description='Support group for mental health'"
            + ", createdAt=" + now + ", updatedAt=" + now
            + ", userCommunities=[]}";
    assertEquals(expected, group.toString());
  }
}
