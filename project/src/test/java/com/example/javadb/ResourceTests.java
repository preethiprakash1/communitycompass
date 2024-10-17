package com.example.javadb;

import com.example.javadb.model.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTests {

  private Resource resource;
  private Timestamp now;

  @BeforeEach
  void setUp() {
    now = new Timestamp(System.currentTimeMillis());
    resource = new Resource("Food Bank", Resource.ResourceType.FOOD_BANK,
            37.7749, -122.4194, "9 AM - 5 PM", "Provides food to the public", now, now);
  }

  @Test
  void testConstructor() {
    assertEquals("Food Bank", resource.getResourceName());
    assertEquals(Resource.ResourceType.FOOD_BANK, resource.getResourceType());
    assertEquals(37.7749, resource.getLatitude());
    assertEquals(-122.4194, resource.getLongitude());
    assertEquals("9 AM - 5 PM", resource.getResourceHours());
    assertEquals("Provides food to the public", resource.getDescription());
    assertEquals(now, resource.getCreatedAt());
    assertEquals(now, resource.getUpdatedAt());
  }

  @Test
  void testSetResourceNameWithNull() {
    assertThrows(IllegalArgumentException.class,
            () -> resource.setResourceName(null));
  }

  @Test
  void testSetResourceNameWithEmptyString() {
    assertThrows(IllegalArgumentException.class,
            () -> resource.setResourceName(""));
  }

  @Test
  void testSetResourceTypeWithNull() {
    assertThrows(IllegalArgumentException.class,
            () -> resource.setResourceType(null));
  }

  @Test
  void testSetLatitudeOutOfRange() {
    assertThrows(IllegalArgumentException.class,
            () -> resource.setLatitude(100.0));
  }

  @Test
  void testSetLongitudeOutOfRange() {
    assertThrows(IllegalArgumentException.class,
            () -> resource.setLongitude(-200.0));
  }

  @Test
  void testSetResourceHoursWithNull() {
    assertThrows(IllegalArgumentException.class,
            () -> resource.setResourceHours(null));
  }
}
