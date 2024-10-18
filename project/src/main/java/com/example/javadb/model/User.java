package com.example.javadb.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int user_id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "email", nullable = false, unique = true, length = 100)
  private String email;

  @Column(name = "age")
  private int age;

  @Enumerated(EnumType.STRING)
  @Column(name = "sex", nullable = false)
  private Sex sex;

  @Column(name = "latitude")
  private double latitude;

  @Column(name = "longitude")
  private double longitude;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;

  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;

  // One to Many relationship with UserCommunity
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserCommunity> communityMembership;

  // Enum for sex
  public enum Sex {
    MALE, FEMALE, OTHER
  }

  // Constructors
  public User() {}

  public User(String name, String email, int age, Sex sex, double latitude,
              double longitude, Timestamp createdAt, Timestamp updatedAt) {
    this.name = name;
    this.email = email;
    this.age = age;
    this.sex = sex;
    this.latitude = latitude;
    this.longitude = longitude;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Getters and setters
  public int getUserId() {
    return user_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    if (email == null || !email.contains("@")) {
      throw new IllegalArgumentException("Invalid email address");
    }
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    if (age < 0) {
      throw new IllegalArgumentException("Age cannot be negative");
    }
    this.age = age;
  }

  public Sex getSex() {
    return sex;
  }

  public void setSex(Sex sex) {
    if (sex == null) {
      throw new IllegalArgumentException("Sex cannot be null");
    }
    this.sex = sex;
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

  public List<UserCommunity> getCommunityMembership() {
    return communityMembership;
  }

  public void setCommunityMembership(List<UserCommunity> communityMembership) {
    this.communityMembership = communityMembership;
  }

  public int getNumberOfCommunities() {
    if (communityMembership == null) {
      return 0;
    } else {
      return communityMembership.size();
    }
  }

  @Override
  public String toString() {
    return "User{" +
            "user_id=" + user_id
            + ", name='" + name + '\''
            + ", email='" + email + '\''
            + ", age=" + age
            + ", sex=" + sex
            + ", latitude=" + latitude
            + ", longitude=" + longitude
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + ", communityMembership="
            + communityMembership + '}';
  }
}
