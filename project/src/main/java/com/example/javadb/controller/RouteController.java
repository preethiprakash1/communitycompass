package com.example.javadb.controller;

import com.example.javadb.model.CommunityGroup;
import com.example.javadb.model.Resource;
import com.example.javadb.model.User;
import com.example.javadb.model.UserCommunity;
import com.example.javadb.repository.CommunityGroupRepository;
import com.example.javadb.repository.ResourceRepository;
import com.example.javadb.repository.UserCommunityRepository;
import com.example.javadb.repository.UserRepository;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all the API routes for the system.
 */
@RestController
public class RouteController {
  /** Repository for user-related operations. */
  private final UserRepository userRepository;

  /** Repository for community group-related operations. */
  private final CommunityGroupRepository communityGroupRepository;

  /** Repository for resource-related operations. */
  private final ResourceRepository resourceRepository;

  /** Repository for user-community group associations. */
  private final UserCommunityRepository userCommunityRepository;

  /**
   * Constructor for RouteController.
   *
   * @param communityGroupRepository The repository for community group-related
   *     operations.
   */
  @Autowired
  public RouteController(
      final CommunityGroupRepository communityGroupRepository,
      final UserRepository userRepository,
      final ResourceRepository resourceRepository,
      final UserCommunityRepository userCommunityRepository) {
    this.userRepository = userRepository;
    this.communityGroupRepository = communityGroupRepository;
    this.resourceRepository = resourceRepository;
    this.userCommunityRepository = userCommunityRepository;
  }

  /**
   * Redirects to the homepage.
   *
   * @return A String containing a welcome message.
   */
  @GetMapping({"/", "/index", "/home", "/welcome"})
  public String welcome() {
    return "Hi, welcome to Community Compass";
  }

  // Community Groups

  /**
   * Retrieves all community groups.
   *
   * @return A {@code ResponseEntity} containing the list of community groups or
   *     a message if none are found.
   */
  @GetMapping(value = "/getCommunityGroups",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  getCommunityGroups() {
    try {
      List<CommunityGroup> items = communityGroupRepository.findAll();
      if (items.isEmpty()) {
        return new ResponseEntity<>(
            "No Community Groups Found", HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(items, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves a community group by its ID.
   *
   * @param communityId The ID of the community group.
   * @return A {@code ResponseEntity} containing the community group or a
   *     message if not found.
   */
  @GetMapping(
      value = "/getCommunityGroup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  getCommunityGroupById(@RequestParam("id") int communityId,
      @RequestParam(
          value = "attribute", required = false) String attributeName) {
    try {
      return communityGroupRepository.findById(communityId)
          .map(communityGroup -> {
            if (attributeName != null) {
              switch (attributeName.toLowerCase()) {
                case "communityname":
                  return new ResponseEntity<>(
                      communityGroup.getCommunityName(), HttpStatus.OK);
                case "communitytype":
                  return new ResponseEntity<>(
                      communityGroup.getCommunityType().toString(), HttpStatus.OK);
                case "latitude":
                  return new ResponseEntity<>(
                      communityGroup.getLatitude(), HttpStatus.OK);
                case "longitude":
                  return new ResponseEntity<>(
                      communityGroup.getLongitude(), HttpStatus.OK);
                case "capacity":
                  return new ResponseEntity<>(
                      communityGroup.getCapacity(), HttpStatus.OK);
                case "description":
                  return new ResponseEntity<>(
                      communityGroup.getDescription(), HttpStatus.OK);
                case "usercount":
                  return new ResponseEntity<>(
                      communityGroup.getNumberOfUsers(), HttpStatus.OK);
                default:
                  return new ResponseEntity<>(
                      "Attribute Not Found", HttpStatus.NOT_FOUND);
              }
            }
            // If no attribute is specified, return the entire community group
            return new ResponseEntity<>(communityGroup, HttpStatus.OK);
          })
          .orElse(new ResponseEntity<>(
              "Community Group Not Found", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Creates a new community group.
   *
   * @param communityName   The name of the community group.
   * @param communityType   The type of the community group.
   * @param latitude        The latitude of the community group location.
   * @param longitude       The longitude of the community group location.
   * @param capacity        The capacity of the community group.
   * @param description     A description of the community group.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @PostMapping(value = "/createCommunityGroup",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  createCommunityGroup(
      @RequestParam("communityName") final String communityName,
      @RequestParam("communityType") final String communityType,
      @RequestParam("latitude") final double latitude,
      @RequestParam("longitude") final double longitude,
      @RequestParam("capacity") final int capacity,
      @RequestParam("description") final String description) {
    try {
      // Convert the communityType string to the CommunityType enum
      CommunityGroup.CommunityType type;
      try {
        type =
            CommunityGroup.CommunityType.valueOf(communityType.toUpperCase());
      } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(
            "Invalid community type provided", HttpStatus.BAD_REQUEST);
      }

      Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

      CommunityGroup newGroup =
          new CommunityGroup(communityName, type, latitude, longitude, capacity,
              description, currentTimestamp, currentTimestamp);

      // Save the new CommunityGroup
      CommunityGroup savedGroup = communityGroupRepository.save(newGroup);
      return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Updates a specific attribute of a community group by its ID.
   *
   * @param communityId    The ID of the community group.
   * @param attributeName  The name of the attribute to update.
   * @param value          The new value for the specified attribute.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @PatchMapping(value = "/updateCommunityGroup",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  updateCommunityGroup(@RequestParam("id") int communityId,
      @RequestParam("attribute") String attributeName,
      @RequestParam("value") String value) {
    try {
      return communityGroupRepository.findById(communityId)
          .map(group -> {
            switch (attributeName.toLowerCase()) {
              case "communityname":
                group.setCommunityName(value);
                break;
              case "communitytype":
                group.setCommunityType(CommunityGroup.CommunityType.valueOf(
                    value.toUpperCase())); // Adjust if necessary based on your
                                           // enum
                break;
              case "latitude":
                group.setLatitude(Double.parseDouble(value));
                break;
              case "longitude":
                group.setLongitude(Double.parseDouble(value));
                break;
              case "capacity":
                group.setCapacity(Integer.parseInt(value));
                break;
              case "description":
                group.setDescription(value);
                break;
              // Add cases for other attributes as needed
              default:
                return new ResponseEntity<>(
                    "Attribute Not Found", HttpStatus.NOT_FOUND);
            }
            communityGroupRepository.save(group);
            return new ResponseEntity<>(group, HttpStatus.OK);
          })
          .orElse(new ResponseEntity<>(
              "Community Group Not Found", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Deletes a community group by its ID.
   *
   * @param communityId The ID of the community group to delete.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @DeleteMapping(value = "/deleteCommunityGroup",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  deleteCommunityGroup(@RequestParam("id") int communityId) {
    try {
      if (communityGroupRepository.existsById(communityId)) {
        communityGroupRepository.deleteById(communityId);
        return new ResponseEntity<>(
            "Community Group Deleted Successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>(
            "Community Group Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  // Users

  /**
   * Retrieves all users.
   *
   * @return A {@code ResponseEntity} containing the list of users or a message
   *     if none are found.
   */
  @GetMapping(value = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getUsers() {
    try {
      List<User> users = userRepository.findAll();
      if (users.isEmpty()) {
        return new ResponseEntity<>("No Users Found", HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Retrieves a specific attribute of a user by their ID.
   *
   * @param userId        The ID of the user.
   * @param attributeName The name of the attribute to retrieve (optional).
   * @return A {@code ResponseEntity} containing the specified attribute's value
   *     or the whole user if no attribute is specified.
   */
  @GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getUserById(@RequestParam("id") int userId,
      @RequestParam(
          value = "attribute", required = false) String attributeName) {
    try {
      return userRepository.findById(userId)
          .map(user -> {
            if (attributeName != null) {
              switch (attributeName.toLowerCase()) {
                case "name":
                  return new ResponseEntity<>(user.getName(), HttpStatus.OK);
                case "email":
                  return new ResponseEntity<>(user.getEmail(), HttpStatus.OK);
                case "age":
                  return new ResponseEntity<>(user.getAge(), HttpStatus.OK);
                case "sex":
                  return new ResponseEntity<>(user.getSex(), HttpStatus.OK);
                case "latitude":
                  return new ResponseEntity<>(
                      user.getLatitude(), HttpStatus.OK);
                case "longitude":
                  return new ResponseEntity<>(
                      user.getLongitude(), HttpStatus.OK);
                case "communitycount":
                  return new ResponseEntity<>(
                      user.getNumberOfCommunities(), HttpStatus.OK);
                default:
                  return new ResponseEntity<>(
                      "Attribute Not Found", HttpStatus.NOT_FOUND);
              }
            }
            // If no attribute is specified, return the entire user
            return new ResponseEntity<>(user, HttpStatus.OK);
          })
          .orElse(new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Creates a new user.
   *
   * @param name      The name of the user.
   * @param email     The email of the user.
   * @param age       The age of the user.
   * @param sex       The sex of the user.
   * @param latitude  The latitude of the user's location.
   * @param longitude The longitude of the user's location.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @PostMapping(
      value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  createUser(@RequestParam("name") String name,
      @RequestParam("email") String email, @RequestParam("age") int age,
      @RequestParam("sex") String sex,
      @RequestParam("latitude") double latitude,
      @RequestParam("longitude") double longitude) {
    try {
      // Create a new User object
      Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

      User newUser =
          new User(name, email, age, User.Sex.valueOf(sex.toUpperCase()),
              latitude, longitude, currentTimestamp, currentTimestamp);

      // Save the new user
      User savedUser = userRepository.save(newUser);
      return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Updates a specific attribute of a user by their ID.
   *
   * @param userId        The ID of the user.
   * @param attributeName The name of the attribute to update.
   * @param value         The new value for the specified attribute.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @PatchMapping(
      value = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  updateUser(@RequestParam("id") int userId,
      @RequestParam("attribute") String attributeName,
      @RequestParam("value") String value) {
    try {
      return userRepository.findById(userId)
          .map(user -> {
            switch (attributeName.toLowerCase()) {
              case "name":
                user.setName(value);
                break;
              case "email":
                user.setEmail(value);
                break;
              case "age":
                user.setAge(Integer.parseInt(value));
                break;
              case "sex":
                user.setSex(User.Sex.valueOf(
                    value.toUpperCase())); // Assuming Sex is an enum
                break;
              case "latitude":
                user.setLatitude(Double.parseDouble(value));
                break;
              case "longitude":
                user.setLongitude(Double.parseDouble(value));
                break;
              default:
                return new ResponseEntity<>(
                    "Attribute Not Found", HttpStatus.NOT_FOUND);
            }
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
          })
          .orElse(new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Deletes a user by their ID.
   *
   * @param userId The ID of the user to delete.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @DeleteMapping(
      value = "/deleteUser", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  deleteUser(@RequestParam("id") int userId) {
    try {
      if (userRepository.existsById(userId)) {
        userRepository.deleteById(userId);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  // Resources

  /**
   * Retrieves all resources.
   *
   * @return A {@code ResponseEntity} containing a list of all resources.
   */
  @GetMapping(
      value = "/getAllResources", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  getAllResources() {
    try {
      List<Resource> resources = resourceRepository.findAll();
      return new ResponseEntity<>(resources, HttpStatus.OK);
    } catch (Exception e) {
      return handleException(e);
    }
  }

    /**
     * Retrieves a resource by its ID.
     *
     * @param resourceId The ID of the resource.
     * @return A {@code ResponseEntity} containing the resource or a message if
     *     not found.
     */
    @GetMapping(
        value = "/getResource", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getResourceById(@RequestParam("id") int resourceId,
                                            @RequestParam(value = "attribute", required = false) String attributeName) {
        try {
            return resourceRepository.findById(resourceId)
                .map(resource -> {
                    if (attributeName != null) {
                        switch (attributeName.toLowerCase()) {
                            case "resourcename":
                                return new ResponseEntity<>(resource.getResourceName(), HttpStatus.OK);
                            case "resourcetype":
                                return new ResponseEntity<>(resource.getResourceType(), HttpStatus.OK);
                            case "latitude":
                                return new ResponseEntity<>(resource.getLatitude(), HttpStatus.OK);
                            case "longitude":
                                return new ResponseEntity<>(resource.getLongitude(), HttpStatus.OK);
                            case "resourcehours":
                                return new ResponseEntity<>(resource.getResourceHours(), HttpStatus.OK);
                            case "description":
                                return new ResponseEntity<>(resource.getDescription(), HttpStatus.OK);
                            case "createdat":
                                return new ResponseEntity<>(resource.getCreatedAt(), HttpStatus.OK);
                            case "updatedat":
                                return new ResponseEntity<>(resource.getUpdatedAt(), HttpStatus.OK);
                            default:
                                return new ResponseEntity<>("Attribute Not Found", HttpStatus.NOT_FOUND);
                        }
                    }
                    // If no attribute is specified, return the entire resource
                    return new ResponseEntity<>(resource, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>("Resource Not Found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return handleException(e);
        }
    }


  /**
   * Creates a new resource.
   *
   * @param resourceName  The name of the resource.
   * @param resourceType  The type of the resource.
   * @param latitude      The latitude of the resource location.
   * @param longitude     The longitude of the resource location.
   * @param resourceHours The hours during which the resource is available.
   * @param description   A description of the resource.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @PostMapping(
      value = "/createResource", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  createResource(@RequestParam("resourceName") String resourceName,
      @RequestParam("resourceType") String resourceType,
      @RequestParam("latitude") double latitude,
      @RequestParam("longitude") double longitude,
      @RequestParam("resourceHours") String resourceHours,
      @RequestParam("description") String description) {
    try {
      // Convert the resourceType string to the ResourceType enum
      Resource.ResourceType type;
      try {
        type = Resource.ResourceType.valueOf(resourceType.toUpperCase());
      } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(
            "Invalid resource type provided", HttpStatus.BAD_REQUEST);
      }

      // Create the current timestamp
      Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

      // Create the Resource object
      Resource newResource =
          new Resource(resourceName, type, latitude, longitude, resourceHours,
              description, currentTimestamp, currentTimestamp);

      // Save the new Resource
      Resource savedResource = resourceRepository.save(newResource);
      return new ResponseEntity<>(savedResource, HttpStatus.CREATED);
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Updates a specific attribute of a resource by its ID.
   *
   * @param resourceId    The ID of the resource.
   * @param attributeName The name of the attribute to update.
   * @param value         The new value for the specified attribute.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @PatchMapping(
      value = "/updateResource", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  updateResource(@RequestParam("id") int resourceId,
      @RequestParam("attribute") String attributeName,
      @RequestParam("value") String value) {
    try {
      return resourceRepository.findById(resourceId)
          .map(resource -> {
            switch (attributeName.toLowerCase()) {
              case "resourcename":
                resource.setResourceName(value);
                break;
              case "resourcetype":
                resource.setResourceType(Resource.ResourceType.valueOf(
                    value.toUpperCase())); // Assuming ResourceType is an enum
                break;
              case "latitude":
                resource.setLatitude(Double.parseDouble(value));
                break;
              case "longitude":
                resource.setLongitude(Double.parseDouble(value));
                break;
              case "resourcehours":
                resource.setResourceHours(value);
                break;
              case "description":
                resource.setDescription(value);
                break;
              default:
                return new ResponseEntity<>(
                    "Attribute Not Found", HttpStatus.NOT_FOUND);
            }
            resourceRepository.save(resource);
            return new ResponseEntity<>(resource, HttpStatus.OK);
          })
          .orElse(
              new ResponseEntity<>("Resource Not Found", HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Deletes a resource by its ID.
   *
   * @param resourceId The ID of the resource.
   * @return A {@code ResponseEntity} indicating the result of the deletion.
   */
  @DeleteMapping(
      value = "/deleteResource", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  deleteResource(@RequestParam("id") final int resourceId) {
    try {
      if (resourceRepository.existsById(resourceId)) {
        resourceRepository.deleteById(resourceId);
        return new ResponseEntity<>(
            "Resource Deleted Successfully", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Resource Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Adds a user to a community group.
   *
   * @param userId       The ID of the user.
   * @param communityId  The ID of the community group.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @PostMapping(value = "/addUserToCommunity",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  addUserToCommunity(@RequestParam("userId") final int userId,
      @RequestParam("communityId") final int communityId) {
    try {
      // Check if the user and community group exist
      User user = userRepository.findById(userId).orElse(null);
      CommunityGroup community =
          communityGroupRepository.findById(communityId).orElse(null);

      if (user != null && community != null) {
        // Add the association if it doesn't already exist
        if (!userCommunityRepository.existsByUserAndCommunity(
                user, community)) {
          UserCommunity userCommunity = new UserCommunity(user, community);
          userCommunityRepository.save(userCommunity);
          return new ResponseEntity<>(
              "User added to community group successfully", HttpStatus.CREATED);
        } else {
          return new ResponseEntity<>(
              "User is already a member of the community group",
              HttpStatus.CONFLICT);
        }
      } else if (user != null) {
        return new ResponseEntity<>(
            "Community Group Not Found", HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return handleException(e);
    }
  }

  /**
   * Removes a user from a community group.
   *
   * @param userId       The ID of the user.
   * @param communityId  The ID of the community group.
   * @return A {@code ResponseEntity} indicating the result of the operation.
   */
  @DeleteMapping(value = "/removeUserFromCommunity",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?>
  removeUserFromCommunity(@RequestParam("userId") final int userId,
      @RequestParam("communityId") final int communityId) {
    try {
      // Check if the association exists
      User user = userRepository.findById(userId).orElse(null);
      CommunityGroup community =
          communityGroupRepository.findById(communityId).orElse(null);

      if (user != null && community != null) {
        if (userCommunityRepository.existsByUserAndCommunity(
                user, community)) {
            UserCommunity userCommunity =
                userCommunityRepository.findByUserAndCommunity(
                    user, community);
            userCommunityRepository.delete(userCommunity);
            return new ResponseEntity<>(
                "User removed from community group successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                "User is not a member of the community group",
                HttpStatus.NOT_FOUND);
        }
        } else if (user != null) {
            return new ResponseEntity<>(
                "Community Group not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    } catch (Exception e) {
    return handleException(e);
    }
  }

  private ResponseEntity<?> handleException(Exception e) {
    System.out.println(e.toString());
    return new ResponseEntity<>("An Error has occurred", HttpStatus.OK);
  }
}
