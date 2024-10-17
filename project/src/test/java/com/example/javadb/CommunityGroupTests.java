package com.example.javadb;

import com.example.javadb.model.CommunityGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
  void testSetLatitudeOutOfRange() {
    assertThrows(IllegalArgumentException.class,
            () -> group.setLatitude(100.0));
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
}
