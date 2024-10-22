package com.example.javadb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

/**
 * Represents a resource entity in the system,
 * such as shelters, clinics, or food banks.
 */
@Entity
@Table(name = "resources")
public final class Resource {

  /** Maximum allowed latitude. */
  private static final double MAX_LATITUDE = 90;
  /** Minimum allowed latitude. */
  private static final double MIN_LATITUDE = -90;
  /** Maximum allowed longitude. */
  private static final double MAX_LONGITUDE = 180;
  /** Minimum allowed longitude. */
  private static final double MIN_LONGITUDE = -180;
  /** Defining max length of the resource name. */
  private static final int RESOURCE_NAME_MAX_LENGTH = 100;
  /** Defining max length of the resource hours. */
  private static final int RESOURCE_HOURS_MAX_LENGTH = 50;

  /** The unique ID of the resource. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "resource_id")
  private int resourceId;

  /** The resource name. */
  @Column(name = "resource_name", nullable = false,
          length = RESOURCE_NAME_MAX_LENGTH)
  private String resourceName;

  /** The resource type. */
  @Enumerated(EnumType.STRING)
  @Column(name = "resource_type", nullable = false)
  private ResourceType resourceType;

  /** Location latitude. */
  @Column(name = "latitude")
  private double latitude;

  /** Location longitude. */
  @Column(name = "longitude")
  private double longitude;

  /** The open hours of the resource. */
  @Column(name = "resource_hours", length = RESOURCE_HOURS_MAX_LENGTH)
  private String resourceHours;

  /** Description of the resource. */
  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  /** Timestamp of when the resource was created. */
  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  /** Timestamp of when the resource was last updated. */
  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  /** Enums of the resource types. */
  public enum ResourceType {
    /** Shelter type. */
    SHELTER,
    /** Food bank type. */
    FOOD_BANK,
    /** Clinic type. */
    CLINIC,
    /** Restroom type. */
    RESTROOM,
    /** Other. */
    OTHER
  }

  /**
   * No-args constructor for JPA.
   */
  public Resource() { }

  /**
   * Constructs a new Resource with the specified attributes.
   *
   * @param name the name of the resource
   * @param type the type of the resource
   * @param lat the latitude of the resource
   * @param lon the longitude of the resource
   * @param hours the open hours of the resource
   * @param desc a description of the resource
   * @param created the created timestamp
   * @param updated the timestamp of when the resource was last updated
   */
  public Resource(final String name, final ResourceType type, final double lat,
                  final double lon, final String hours, final String desc,
                  final Timestamp created, final Timestamp updated) {
    this.resourceName = name;
    this.resourceType = type;
    this.latitude = lat;
    this.longitude = lon;
    this.resourceHours = hours;
    this.description = desc;
    this.createdAt = created;
    this.updatedAt = updated;
  }

  /**
   * Gets the resource ID.
   *
   * @return the resource ID
   */
  public int getResourceId() {
    return resourceId;
  }

  /**
   * Gets the resource name.
   *
   * @return the resource name
   */
  public String getResourceName() {
    return resourceName;
  }

  /**
   * Sets the resource name.
   *
   * @param name the name to set
   * @throws IllegalArgumentException if the name is null or empty
   */
  public void setResourceName(final String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Resource name "
              + "cannot be null or empty");
    }
    this.resourceName = name;
  }

  /**
   * Gets the type of resource.
   *
   * @return the type of resource
   */
  public ResourceType getResourceType() {
    return resourceType;
  }

  /**
   * Sets the resource type.
   *
   * @param type the type to set
   * @throws IllegalArgumentException if the type is null
   */
  public void setResourceType(final ResourceType type) {
    if (type == null) {
      throw new IllegalArgumentException("Resource type cannot be null");
    }
    this.resourceType = type;
  }

  /**
   * Gets the latitude of the resource.
   *
   * @return the latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Sets the latitude of the resource.
   *
   * @param lat the latitude to set
   * @throws IllegalArgumentException if the latitude is out of range
   */
  public void setLatitude(final double lat) {
    if (lat < MIN_LATITUDE || lat > MAX_LATITUDE) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90");
    }
    this.latitude = lat;
  }

  /**
   * Gets the longitude of the resource.
   *
   * @return the longitude
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Sets the longitude of the resource.
   *
   * @param lon the longitude to set
   * @throws IllegalArgumentException if the longitude is out of range
   */
  public void setLongitude(final double lon) {
    if (lon < MIN_LONGITUDE || lon > MAX_LONGITUDE) {
      throw new IllegalArgumentException("Longitude "
              + "must be between -180 and 180");
    }
    this.longitude = lon;
  }

  /**
   * Gets the resource hours.
   *
   * @return the resource hours
   */
  public String getResourceHours() {
    return resourceHours;
  }

  /**
   * Sets the resource hours.
   *
   * @param hours the hours to set
   * @throws IllegalArgumentException if null
   */
  public void setResourceHours(final String hours) {
    if (hours == null) {
      throw new IllegalArgumentException("Resource hours cannot be null");
    }
    this.resourceHours = hours;
  }

  /**
   * Gets the resource description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the resource description.
   *
   * @param desc the description to set
   */
  public void setDescription(final String desc) {
    this.description = desc;
  }

  /**
   * Gets the creation timestamp.
   *
   * @return the timestamp
   */
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation timestamp.
   *
   * @param created the timestamp
   */
  public void setCreatedAt(final Timestamp created) {
    this.createdAt = created;
  }

  /**
   * Gets the last updated timestamp.
   *
   * @return the timestamp
   */
  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated timestamp.
   *
   * @param updated the timestamp
   */
  public void setUpdatedAt(final Timestamp updated) {
    this.updatedAt = updated;
  }

  @Override
  public String toString() {
    return "Resource{"
            + "resourceId=" + resourceId
            + ", resourceName='" + resourceName + '\''
            + ", resourceType=" + resourceType
            + ", latitude=" + latitude
            + ", longitude=" + longitude
            + ", resourceHours='" + resourceHours + '\''
            + ", description='" + description + '\''
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + '}';
  }
}
