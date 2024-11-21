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

  @Test
  void testSetResourceHoursWithInvalidHours() {
    assertThrows(IllegalArgumentException.class,
            () -> resource.setResourceHours("13AM-2PM"));
  }

  @Test
  void testSetResourceHoursWithInvalidFormat() {
    assertThrows(IllegalArgumentException.class,
            () -> resource.setResourceHours("2PM@4PM"));
  }

  @Test
  void testSetResourceHoursWithValidFormat() {
    String hours = "2PM-4PM";
    resource.setResourceHours(hours);

    assertEquals(hours, resource.getResourceHours());
  }

  @Test
  void testToString() {
    String expected = "Resource{"
            + "resourceId=" + resource.getResourceId()
            + ", resourceName='Food Bank'"
            + ", resourceType=FOOD_BANK"
            + ", latitude=37.7749"
            + ", longitude=-122.4194"
            + ", resourceHours='9 AM - 5 PM'"
            + ", description='Provides food to the public'"
            + ", createdAt=" + now
            + ", updatedAt=" + now
            + '}';
    assertEquals(expected, resource.toString());
  }
}
