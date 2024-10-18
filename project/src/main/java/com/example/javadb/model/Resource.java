package com.example.javadb.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "resources")
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int resource_id;

  @Column(name = "resource_name", nullable = false, length = 100)
  private String resourceName;

  // Storing enum values as strings
  @Enumerated(EnumType.STRING)
  @Column(name = "resource_type", nullable = false)
  private ResourceType resourceType;

  @Column(name = "latitude")
  private double latitude;

  @Column(name = "longitude")
  private double longitude;

  @Column(name = "resource_hours", length = 50)
  private String resourceHours;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  // Enum for resource type
  public enum ResourceType {
    SHELTER, FOOD_BANK, CLINIC, RESTROOM, OTHER
  }

  // Constructors
  public Resource() {}

  public Resource(String resourceName, ResourceType resourceType, double latitude,
                  double longitude, String resourceHours, String description,
                  Timestamp createdAt, Timestamp updatedAt) {
    this.resourceName = resourceName;
    this.resourceType = resourceType;
    this.latitude = latitude;
    this.longitude = longitude;
    this.resourceHours = resourceHours;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Getters and setters
  public int getResourceId() {
    return resource_id;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    if (resourceName == null || resourceName.trim().isEmpty()) {
      throw new IllegalArgumentException("Resource name cannot be null or empty");
    }
    this.resourceName = resourceName;
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public void setResourceType(ResourceType resourceType) {
    if (resourceType == null) {
      throw new IllegalArgumentException("Resource type cannot be null");
    }
    this.resourceType = resourceType;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    if (latitude < -90 || latitude > 90) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90");
    }
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    if (longitude < -180 || longitude > 180) {
      throw new IllegalArgumentException("Longitude must be between -180 and 180");
    }
    this.longitude = longitude;
  }

  public String getResourceHours() {
    return resourceHours;
  }

  public void setResourceHours(String resourceHours) {
    if (resourceHours == null) {
      throw new IllegalArgumentException("Resource hours cannot be null");
    }
    this.resourceHours = resourceHours;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return "Resource{" +
            "resource_id=" + resource_id
            + ", resourceName='" + resourceName
            + '\'' + ", resourceType=" + resourceType
            + ", latitude=" + latitude
            + ", longitude=" + longitude
            + ", resourceHours='" + resourceHours
            + '\'' + ", description='" + description
            + '\'' + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + '}';
  }
}
