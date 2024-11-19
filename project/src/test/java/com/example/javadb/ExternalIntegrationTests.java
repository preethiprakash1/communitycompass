package com.example.javadb;

import com.example.javadb.model.CommunityGroup;
import com.example.javadb.model.CommunityGroup.CommunityType;
import com.example.javadb.model.Resource;
import com.example.javadb.model.User;
import com.example.javadb.model.UserCommunity;
import com.example.javadb.repository.CommunityGroupRepository;
import com.example.javadb.repository.ResourceRepository;
import com.example.javadb.repository.UserCommunityRepository;
import com.example.javadb.repository.UserRepository;
import com.example.javadb.controller.RouteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/**
 * External integration tests for Community Compass.
 *
 * This file contains external integration tests which verify that the service layer is properly
 * integrated with the persistent MySQL database through the REST API. Various creation, updates,
 * and deletion operations on Resources, Community Groups, and Users are tested and it is ensured
 * that the persistent database is properly updated.
 *
 * All of these tests use the real database hosted on Google Cloud. The @Transactional annotation
 * ensures that all changes are rolled back after each test.
 */
public class ExternalIntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ResourceRepository resourceRepository;

  @Autowired
  private CommunityGroupRepository communityGroupRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserCommunityRepository userCommunityRepository;

  @Autowired
  private RouteController routeController;

//  @Autowired
//  private JdbcTemplate jdbcTemplate;

  private User testUser;
  private CommunityGroup testCommunityGroup;
  private CommunityGroup testCommunityGroup1;
  private Resource testResource;
  private Resource testResource1;

  @BeforeEach
  public void setUp() {
    // Test User
    testUser = new User();
    testUser.setName("John Doe");
    testUser.setEmail("john.doe.integrationtest@example.com");
    testUser.setAge(30);
    testUser.setSex(User.Sex.MALE);
    testUser.setLatitude(40.7128);
    testUser.setLongitude(-74.0060);
    testUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    testUser.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

    // Test Community Group
    testCommunityGroup = new CommunityGroup();
    testCommunityGroup.setCommunityName("Local Food Bank");
    testCommunityGroup.setCommunityType(CommunityGroup.CommunityType.OTHER);
    testCommunityGroup.setLatitude(40.7128);
    testCommunityGroup.setLongitude(-74.0060);
    testCommunityGroup.setCapacity(100);
    testCommunityGroup.setDescription("Provides food assistance to the community");
    testCommunityGroup.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    testCommunityGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

    // Test Community Group 1 (closer)
    testCommunityGroup1 = new CommunityGroup();
    testCommunityGroup1.setCommunityName("NYC Employment Assistance Group");
    testCommunityGroup1.setCommunityType(CommunityGroup.CommunityType.EMPLOYMENT_ASSISTANCE);
    testCommunityGroup1.setLatitude(40.730610); // NYC
    testCommunityGroup1.setLongitude(-73.935242); // NYC
    testCommunityGroup1.setCapacity(50);
    testCommunityGroup1.setDescription("Provides employment assistance");
    testCommunityGroup1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    testCommunityGroup1.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

    // Test Resource
    testResource = new Resource();
    testResource.setResourceName("Food Bank");
    testResource.setResourceType(Resource.ResourceType.FOOD_BANK);
    testResource.setLatitude(40.7128);
    testResource.setLongitude(-74.0060);
    testResource.setResourceHours("9AM-5PM");
    testResource.setDescription("Provides food assistance");
    testResource.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    testResource.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

    // Test Resource 1 (closer)
    testResource1 = new Resource();
    testResource1.setResourceName("NYC Local Shelter");
    testResource1.setResourceType(Resource.ResourceType.SHELTER);
    testResource1.setLatitude(40.7128); // NYC
    testResource1.setLongitude(-74.0060); // NYC
    testResource1.setResourceHours("9AM-5PM");
    testResource1.setDescription("Provides temporary shelter");
    testResource1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    testResource1.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
  }

  /**
   * This tests that we can successfully retrieve a user from the persisten database using the
   * REST API.
   */
  @Transactional
  @Test
  void testGetUser() throws Exception {
    User savedUser = userRepository.save(testUser);

    mockMvc.perform(get("/getUser?id=" + savedUser.getUserId())
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"userId\":" + savedUser.getUserId() + ",\"name\":\""
                    + savedUser.getName() + "\",\"email\":\"" + savedUser.getEmail() + "\"}"));

    java.util.Optional<User> retrievedUser = userRepository.findById(savedUser.getUserId());
    assertTrue(retrievedUser.isPresent());
    assertEquals(savedUser.getEmail(), retrievedUser.get().getEmail());
  }

  /**
   * This tests that we can update a user's information through the REST API and the changes are
   * propagated to the persistent database.
   */
  @Transactional
  @Test
  void testUpdateUserAge() throws Exception {
    User savedUser = userRepository.save(testUser);

    mockMvc.perform(get("/getUser?id=" + savedUser.getUserId())
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"userId\":" + savedUser.getUserId() + ",\"name\":\""
                    + savedUser.getName() + "\",\"email\":\"" + savedUser.getEmail() + "\"}"));

    java.util.Optional<User> retrievedUser = userRepository.findById(savedUser.getUserId());
    assertEquals(30, retrievedUser.get().getAge());

    mockMvc.perform(patch("/updateUser")
                    .param("id", String.valueOf(testUser.getUserId()))
                    .param("attribute", "age")
                    .param("value", "45"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"userId\":" + savedUser.getUserId() + ",\"name\":\""
                    + savedUser.getName() + "\",\"email\":\"" + savedUser.getEmail() + "\"}"));

    retrievedUser = userRepository.findById(savedUser.getUserId());
    assertEquals(45, retrievedUser.get().getAge());
  }

  /**
   * This tests that we receive the proper error response if we attempt to update a user with an
   * invalid attribute. It checks that the persistent database is not affected by this call to
   * the REST API.
   */
  @Transactional
  @Test
  void testUpdateUser_InvalidAttribute() throws Exception {
    User savedUser = userRepository.save(testUser);

    mockMvc.perform(get("/getUser?id=" + savedUser.getUserId())
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"userId\":" + savedUser.getUserId() + ",\"name\":\""
                    + savedUser.getName() + "\",\"email\":\"" + savedUser.getEmail() + "\"}"));

    java.util.Optional<User> retrievedUser = userRepository.findById(savedUser.getUserId());
    assertEquals(30, retrievedUser.get().getAge());

    mockMvc.perform(patch("/updateUser")
                    .param("id", String.valueOf(savedUser.getUserId()))
                    .param("attribute", "nonexistent attribute")
                    .param("value", "45"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Attribute Not Found"));
  }

  /**
   * This tests that we can successfully retrieve community groups of a particular type using the
   * REST API from the persistent database.
   */
  @Transactional
  @Test
  void testGetCommunityGroupByType() throws Exception {
    communityGroupRepository.save(testCommunityGroup);
    communityGroupRepository.save(testCommunityGroup1);

    mockMvc.perform(get("/getCommunityGroupsByType?type=EMPLOYMENT_ASSISTANCE")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
              String responseContent = result.getResponse().getContentAsString();

              assertTrue(responseContent.contains("\"communityType\":\"EMPLOYMENT_ASSISTANCE\""));
              assertFalse(responseContent.contains("\"communityType\":\"OTHER\""));
            });
  }

  /**
   * This tests that if we use the REST API to attempt retrieving community groups of an invalid
   * type from the persistent database, we receive the proper error response.
   */
  @Transactional
  @Test
  void testGetCommunityGroupByType_InvalidType() throws Exception {
    mockMvc.perform(get("/getCommunityGroupsByType")
                    .param("type", "ABC")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  /**
   * This tests that we can successfully add and remove a user from a community group using the
   * REST API. It checks that the persistent database is correctly updated after each operation.
   */
  @Transactional
  @Test
  void addAndRemoveUserFromCommunityGroup() throws Exception {
    User savedUser = userRepository.save(testUser);
    CommunityGroup savedCommunity = communityGroupRepository.save(testCommunityGroup);

    mockMvc.perform(post("/addUserToCommunity?userId=" + savedUser.getUserId() + "&communityId=" + savedCommunity.getCommunityId())
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().string("User added to community group successfully"));

    assertTrue(userCommunityRepository.existsByUserAndCommunity(savedUser, savedCommunity));

    mockMvc.perform(delete("/removeUserFromCommunity")
                    .param("userId", String.valueOf(savedUser.getUserId()))
                    .param("communityId", String.valueOf(savedCommunity.getCommunityId())))
            .andExpect(status().isOk())
            .andExpect(content().string("User removed from community group successfully"));

    assertFalse(userCommunityRepository.existsByUserAndCommunity(savedUser, savedCommunity));
  }

  /**
   * This tests that we can successfully retrieve resources of a given type using the REST API
   * from the persistent database.
   */
  @Transactional
  @Test
  void getResourceByType() throws Exception {
    Resource savedResource = resourceRepository.save(testResource);
    Resource savedResource1 = resourceRepository.save(testResource1);

    mockMvc.perform(get("/getResourcesByType?type=FOOD_BANK")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
              String responseContent = result.getResponse().getContentAsString();

              assertTrue(responseContent.contains("\"resourceType\":\"FOOD_BANK\""));
              assertFalse(responseContent.contains("\"resourceType\":\"SHELTER\""));
            });
  }

  /**
   * This tests that we receive the proper error code if we try to retrieve resources of an
   * invalid type using the REST API from the persistent database.
   */
  @Transactional
  @Test
  void getResourceByType_InvalidType() throws Exception {
    mockMvc.perform(get("/getResourcesByType")
                    .param("type", "XYZ")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
  }

  /**
   * This tests that we can successfully create and delete a resource using the REST API. It
   * checks that the persistent database is updated correctly after each operation.
   */
  @Transactional
  @Test
  void createAndDeleteResource() throws Exception {
    MvcResult result = mockMvc.perform(post("/createResource")
                    .param("resourceName", testResource.getResourceName())
                    .param("resourceType", testResource.getResourceType().name())
                    .param("latitude", String.valueOf(testResource.getLatitude()))
                    .param("longitude", String.valueOf(testResource.getLongitude()))
                    .param("resourceHours", testResource.getResourceHours())
                    .param("description", testResource.getDescription())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isCreated())
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    JSONObject json = new JSONObject(responseBody);
    int resourceId = json.getInt("resourceId");

    java.util.Optional<Resource> createdResource = resourceRepository.findById(resourceId);
    assertTrue(createdResource.isPresent());

    mockMvc.perform(delete("/deleteResource")
                    .param("id", String.valueOf(resourceId)))
            .andExpect(status().isOk())
            .andExpect(content().string("Resource Deleted Successfully"));

    createdResource = resourceRepository.findById(resourceId);
    assertFalse(createdResource.isPresent());
    }

  /**
   * This tests that if we try to delete a resource that does not exist in the persistent
   * database using the REST API, we receive the proper error code.
   */
    @Transactional
    @Test
    void deleteResource_invalidId() throws Exception {
      mockMvc.perform(delete("/deleteResource")
                      .param("id", "-5"))
              .andExpect(status().isNotFound())
              .andExpect(content().string("Resource Not Found"));

      java.util.Optional<Resource> createdResource = resourceRepository.findById(-5);
      assertFalse(createdResource.isPresent());
    }

}