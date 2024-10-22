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
 * Represents a community group in the system.
 * A community group can have multiple users.
 */
@Entity
@Table(name = "community_groups")
public final class CommunityGroup {

    /** Maximum community name length allowed. */
    private static final int MAX_LENGTH = 100;
    /** Maximum latitude allowed. */
    private static final double MAX_LATITUDE = 90;
    /** Minimum latitude allowed. */
    private static final double MIN_LATITUDE = -90;
    /** Maximum longitude allowed. */
    private static final double MAX_LONGITUDE = 180;
    /** Minimum longitude allowed. */
    private static final double MIN_LONGITUDE = -180;

    /** The community group ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "community_id")
    private int communityId;

    /** The community group name. */
    @Column(name = "community_name", nullable = false, length = MAX_LENGTH)
    private String communityName;

    /** The community group type. */
    @Enumerated(EnumType.STRING)
    @Column(name = "community_type", nullable = false)
    private CommunityType communityType;

    /** The community group's location latitude. */
    @Column(name = "latitude")
    private double latitude;

    /** The community group location longitude. */
    @Column(name = "longitude")
    private double longitude;

    /** The community group capacity. */
    @Column(name = "capacity")
    private int capacity;

    /** A community group description. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Timestamp of when the community group was created. */
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    /** Timestamp of when the community group was last updated. */
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    /** List of user communities associated with this community group. */
    @OneToMany(mappedBy = "community", cascade =
            CascadeType.ALL, orphanRemoval = true)
    private List<UserCommunity> userCommunities;

    /**
     * Enum representing the types of community groups.
     */
    public enum CommunityType {
        /** Mental health community type. */
        MENTAL_HEALTH,
        /** Employment assistance community type. */
        EMPLOYMENT_ASSISTANCE,
        /** Other community type. */
        OTHER
    }

    /**
     * Default non-args constructor.
     */
    public CommunityGroup() { }

    /**
     * Constructs a new CommunityGroup with the below attributes.
     *
     * @param commName the community group name
     * @param commType the community group type
     * @param lat the location latitude
     * @param longi the location longitude
     * @param cap the group capacity
     * @param desc the group description
     * @param createAt when the group was created timestamp
     * @param updateAt when the group was last updated timestamp
     */

    public CommunityGroup(final String commName,
                          final CommunityType commType,
                          final double lat,
                          final double longi, final int cap,
                          final String desc, final Timestamp createAt,
                          final Timestamp updateAt) {
        this.communityName = commName;
        this.communityType = commType;
        this.latitude = lat;
        this.longitude = longi;
        this.capacity = cap;
        this.description = desc;
        this.createdAt = createAt;
        this.updatedAt = updateAt;
    }

    /**
     * Gets community group ID.
     *
     * @return the community group ID
     */
    public int getCommunityId() {
        return communityId;
    }

    /**
     * Gets name of the community group.
     *
     * @return the name of the community group
     */
    public String getCommunityName() {
        return communityName;
    }

    /**
     * Sets name of the community group.
     *
     * @param commName the name to set
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setCommunityName(final String commName) {
        if (commName == null || commName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Community name cannot be null or empty");
        }
        this.communityName = commName;
    }

    /**
     * Gets type of the community group.
     *
     * @return the type of the community group
     */
    public CommunityType getCommunityType() {
        return communityType;
    }

    /**
     * Sets type of the community group.
     *
     * @param commType the type to set
     * @throws IllegalArgumentException if the type is null
     */
    public void setCommunityType(final CommunityType commType) {
        if (commType == null) {
            throw new IllegalArgumentException("Community type cannot be null");
        }
        this.communityType = commType;
    }

    /**
     * Gets list of user communities associated with this group.
     *
     * @return the list of user communities
     */
    public List<UserCommunity> getUserCommunities() {
        return userCommunities;
    }

    /**
     * Sets list of user communities.
     *
     * @param userComm the list to set
     */
    public void setUserCommunities(final List<UserCommunity> userComm) {
        this.userCommunities = userComm;
    }

    /**
     * Gets latitude of the community group's location.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude of the community group's location.
     *
     * @param lat the latitude to set
     * @throws IllegalArgumentException if latitude is out of range.
     */
    public void setLatitude(final double lat) {
        if (lat < MIN_LATITUDE || lat > MAX_LATITUDE) {
            throw new IllegalArgumentException(
                    "Latitude must be between -90 and 90");
        }
        this.latitude = lat;
    }

    /**
     * Gets longitude of the community group's location.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude of the community group's location.
     *
     * @param longi the longitude to set
     * @throws IllegalArgumentException if longitude is out of range.
     */
    public void setLongitude(final double longi) {
        if (longi < MIN_LONGITUDE || longi > MAX_LONGITUDE) {
            throw new IllegalArgumentException(
                    "Longitude must be between -180 and 180");
        }
        this.longitude = longi;
    }

    /**
     * Gets capacity of the community group.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity of the community group.
     *
     * @param cap the capacity to set
     * @throws IllegalArgumentException if capacity is negative
     */
    public void setCapacity(final int cap) {
        if (cap < 0) {
            throw new IllegalArgumentException(
                    "Capacity cannot be negative");
        }
        this.capacity = cap;
    }

    /**
     * Gets description of the community group.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description of the community group.
     *
     * @param desc the description to set
     */
    public void setDescription(final String desc) {
        this.description = desc;
    }

    /**
     * Gets creation timestamp of the community group.
     *
     * @return the creation timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets creation timestamp.
     *
     * @param createAt the timestamp to set
     */
    public void setCreatedAt(final Timestamp createAt) {
        this.createdAt = createAt;
    }

    /**
     * Gets last updated timestamp of the community group.
     *
     * @return the last updated timestamp
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets last updated timestamp.
     *
     * @param updateAt the timestamp to set
     */
    public void setUpdatedAt(final Timestamp updateAt) {
        this.updatedAt = updateAt;
    }

    /**
     * Gets number of users in this community group.
     *
     * @return the number of users
     */
    public int getNumberOfUsers() {
        return userCommunities == null ? 0 : userCommunities.size();
    }

    /**
     * Returns string representation of the community group.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "CommunityGroup{"
                + "communityId=" + communityId
                + ", communityName='" + communityName + '\''
                + ", communityType=" + communityType
                + ", latitude=" + latitude
                + ", longitude=" + longitude
                + ", capacity=" + capacity
                + ", description='" + description + '\''
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", userCommunities=" + userCommunities
                + '}';
    }
}
