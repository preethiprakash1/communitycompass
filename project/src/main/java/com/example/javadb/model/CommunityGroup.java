package com.example.javadb.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table (name = "community_groups")
public class CommunityGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int community_id;

    @Column(name = "community_name", nullable = false, length = 100)
    private String communityName;

    @Column(name = "community_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommunityType communityType;

    @Column(name = "latitude", precision = 9, scale = 6)
    private double latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private double longitude;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @ElementCollection  // list of user IDs associated with the group
    @CollectionTable(name = "user_community",
            joinColumns = @JoinColumn(name = "community_id"))
    @Column(name = "user_id")
    private List<Integer> users;

    public enum CommunityType {
        MENTAL_HEALTH, EMPLOYMENT_ASSISTANCE, OTHER
    }

    // Constructors
    public CommunityGroup() {}
    
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

    // Getters and Setters
    public int getCommunityId() {
        return community_id;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public CommunityType getCommunityType() { return communityType; }

    public void setCommunityType(CommunityType communityType) { this.communityType = communityType; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getDescription() { return description; }

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

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }

    public int getNumberOfUsers() {
        if (users == null) {
            return 0;
        } else {
            return users.size();
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
                ", users=" + users +
                '}';
    }
}
