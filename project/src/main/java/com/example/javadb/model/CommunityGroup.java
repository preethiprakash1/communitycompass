package com.example.javadb.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "community_groups")
public class CommunityGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int community_id;

    @Column(name = "community_name", nullable = false, length = 100)
    private String communityName;

    @Enumerated(EnumType.STRING)
    @Column(name = "community_type", nullable = false)
    private CommunityType communityType;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCommunity> userCommunities;

    public enum CommunityType {
        MENTAL_HEALTH, EMPLOYMENT_ASSISTANCE, OTHER
    }

    // Constructors
    public CommunityGroup(String communityName, CommunityType communityType,
                          double latitude, double longitude, int capacity,
                          String description, Timestamp createdAt, Timestamp updatedAt) {
        this.communityName = communityName;
        this.communityType = communityType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public int getCommunityId() {
        return community_id;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        if (communityName == null || communityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Community name cannot be null or empty");
        }
        this.communityName = communityName;
    }

    public CommunityType getCommunityType() {
        return communityType;
    }

    public void setCommunityType(CommunityType communityType) {
        if (communityType == null) {
            throw new IllegalArgumentException("Community type cannot be null");
        }
        this.communityType = communityType;
    }

    public List<UserCommunity> getUserCommunities() {
        return userCommunities;
    }

    public void setUserCommunities(List<UserCommunity> userCommunities) {
        this.userCommunities = userCommunities;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.capacity = capacity;
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

    public int getNumberOfUsers() {
        if (userCommunities == null) {
            return 0;
        } else {
            return userCommunities.size();
        }
    }
    @Override
    public String toString() {
        return "CommunityGroup{" +
                "community_id=" + community_id +
                ", communityName='" + communityName + '\'' +
                ", communityType=" + communityType +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userCommunities=" + userCommunities +
                '}';
    }
}
