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

  @Column(name = "latitude", precision = 9, scale = 6)
  private double latitude;

  @Column(name = "longitude", precision = 9, scale = 6)
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

  // Getters and Setters
  public int getResourceId() {
    return resource_id;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getResourceHours() {
    return resourceHours;
  }

  public void setResourceHours(String resourceHours) {
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
            "resource_id=" + resource_id +
            ", resourceName='" + resourceName + '\'' +
            ", resourceType=" + resourceType +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", resourceHours='" + resourceHours + '\'' +
            ", description='" + description + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
  }
}
