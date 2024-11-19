package com.example.javadb.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.List;

/**
 * Represents a user entity in the system.
 * A user can have multiple community memberships.
 */
@Entity
@Table(name = "users")
public final class User {

  /** Maximum allowed latitude. */
  private static final double MAX_LATITUDE = 90;
  /** Minimum allowed latitude. */
  private static final double MIN_LATITUDE = -90;
  /** Maximum allowed longitude. */
  private static final double MAX_LONGITUDE = 180;
  /** Minimum allowed longitude. */
  private static final double MIN_LONGITUDE = -180;
  /** Maximum length for name or email fields. */
  private static final int MAX_LENGTH = 100;

  /** The unique ID of the user. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private int userId;

  /** The name of the user. */
  @Column(name = "name", nullable = false, length = MAX_LENGTH)
  private String name;

  /** The email address of the user. */
  @Column(name = "email", nullable = false, unique = true, length = MAX_LENGTH)
  private String email;

  /** The age of the user. */
  @Column(name = "age")
  private int age;

  /** The sex of the user. */
  @Enumerated(EnumType.STRING)
  @Column(name = "sex", nullable = false)
  private Sex sex;

  /** The latitude of the user's location. */
  @Column(name = "latitude")
  private double latitude;

  /** The longitude of the user's location. */
  @Column(name = "longitude")
  private double longitude;

  /** Timestamp indicating when the user was created. */
  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  /** Timestamp indicating when the user was last updated. */
  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  /** List of community memberships associated with the user. */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserCommunity> communityMembership;

  /**
   * Enum representing the sex of the user.
   */
  public enum Sex {
    /** Male enum. */
    MALE,
    /** Female enum. */
    FEMALE,
    /** Other enum. */
    OTHER
  }

  /**
   * Default constructor for JPA.
   */
  public User() {
  }

  /**
   * Constructs a new User with the specified attributes.
   *
   * @param newName the name of the user
   * @param newEmail the email address of the user
   * @param newAge the age of the user
   * @param newSex the sex of the user
   * @param newLatitude the latitude of the user's location
   * @param newLongitude the longitude of the user's location
   * @param newCreatedAt the creation timestamp
   * @param newUpdatedAt the last updated timestamp
   */
  public User(final String newName, final String newEmail, final int newAge,
              final Sex newSex, final double newLatitude,
              final double newLongitude,
              final Timestamp newCreatedAt, final Timestamp newUpdatedAt) {
    this.name = newName;
    this.email = newEmail;
    this.age = newAge;
    this.sex = newSex;
    this.latitude = newLatitude;
    this.longitude = newLongitude;
    this.createdAt = newCreatedAt;
    this.updatedAt = newUpdatedAt;
  }

  /**
   * Gets the user ID.
   *
   * @return the user ID
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Gets the user's name.
   *
   * @return the user's name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the user's name.
   *
   * @param newName the name to set
   * @throws IllegalArgumentException if the name is null or empty
   */
  public void setName(final String newName) {
    if (newName == null || newName.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    this.name = newName;
  }

  /**
   * Gets the user's email address.
   *
   * @return the user's email address
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the user's email address.
   *
   * @param newEmail the email to set
   * @throws IllegalArgumentException if the email is invalid
   */
  public void setEmail(final String newEmail) {
    if (newEmail == null || !newEmail.contains("@")) {
      throw new IllegalArgumentException("Invalid email address");
    }
    this.email = newEmail;
  }

  /**
   * Gets the user's age.
   *
   * @return the user's age
   */
  public int getAge() {
    return age;
  }

  /**
   * Sets the user's age.
   *
   * @param newAge the age to set
   * @throws IllegalArgumentException if the age is negative
   */
  public void setAge(final int newAge) {
    if (newAge < 0) {
      throw new IllegalArgumentException("Age cannot be negative");
    }
    this.age = newAge;
  }

  /**
   * Gets the user's sex.
   *
   * @return the user's sex
   */
  public Sex getSex() {
    return sex;
  }

  /**
   * Sets the user's sex.
   *
   * @param newSex the sex to set
   * @throws IllegalArgumentException if the sex is null
   */
  public void setSex(final Sex newSex) {
    if (newSex == null) {
      throw new IllegalArgumentException("Sex cannot be null");
    }
    this.sex = newSex;
  }

  /**
   * Gets the latitude of the user's location.
   *
   * @return the latitude
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Sets the latitude of the user's location.
   *
   * @param newLatitude the latitude to set
   */
  public void setLatitude(final double newLatitude) {
    if (newLatitude < MIN_LATITUDE || newLatitude > MAX_LATITUDE) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90");
    }
    this.latitude = newLatitude;
  }

  /**
   * Gets the longitude of the user's location.
   *
   * @return the longitude
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Sets the longitude of the user's location.
   *
   * @param newLongitude the longitude to set
   */
  public void setLongitude(final double newLongitude) {
    if (newLongitude < MIN_LONGITUDE || newLongitude > MAX_LONGITUDE) {
      throw new IllegalArgumentException("Longitude "
              + "must be between -180 and 180");
    }
    this.longitude = newLongitude;
  }

  /**
   * Gets the timestamp of when the user was created.
   *
   * @return the creation timestamp
   */
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation timestamp.
   *
   * @param newCreatedAt the timestamp to set
   */
  public void setCreatedAt(final Timestamp newCreatedAt) {
    this.createdAt = newCreatedAt;
  }

  /**
   * Gets the timestamp of when the user was last updated.
   *
   * @return the last updated timestamp
   */
  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the last updated timestamp.
   *
   * @param newUpdatedAt the timestamp to set
   */
  public void setUpdatedAt(final Timestamp newUpdatedAt) {
    this.updatedAt = newUpdatedAt;
  }

  /**
   * Gets the user's community memberships.
   *
   * @return the list of community memberships
   */
  public List<UserCommunity> getCommunityMembership() {
    return communityMembership;
  }

  /**
   * Sets the user's community memberships.
   *
   * @param newCommunityMembership the community memberships to set
   */
  public void setCommunityMembership(final List<UserCommunity>
                                             newCommunityMembership) {
    this.communityMembership = newCommunityMembership;
  }

  /**
   * Gets the number of communities the user belongs to.
   *
   * @return the number of communities
   */
  public int getNumberOfCommunities() {
    return communityMembership == null ? 0 : communityMembership.size();
  }

  /**
   * Returns a string representation of the user.
   *
   * @return the string representation of the user
   */
  @Override
  public String toString() {
    return "User{"
            + "userId=" + userId
            + ", name='" + name + '\''
            + ", email='" + email + '\''
            + ", age=" + age
            + ", sex=" + sex
            + ", latitude=" + latitude
            + ", longitude=" + longitude
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + ", communityMembership=" + communityMembership
            + '}';
  }
}

