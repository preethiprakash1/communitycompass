package com.example.javadb;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.javadb.model.CommunityGroup;
import com.example.javadb.model.CommunityGroup.CommunityType;
import com.example.javadb.model.Resource;
import com.example.javadb.model.Resource.ResourceType;
import com.example.javadb.model.User;
import com.example.javadb.model.UserCommunity;
import com.example.javadb.repository.CommunityGroupRepository;
import com.example.javadb.repository.ResourceRepository;
import com.example.javadb.repository.UserCommunityRepository;
import com.example.javadb.repository.UserRepository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceRepository resourceRepository;

    @MockBean
    private CommunityGroupRepository communityGroupRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserCommunityRepository userCommunityRepository;

    private User testUser;
    private CommunityGroup testCommunityGroup;
    private Resource testResource;

    @BeforeEach
    public void setUp() {

        // Test User
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");
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

        // Test Resource
        testResource = new Resource();
        testResource.setResourceName("Food Bank");
        testResource.setResourceType(Resource.ResourceType.FOOD_BANK);
        testResource.setLatitude(40.7128);
        testResource.setLongitude(-74.0060);
        testResource.setResourceHours("9 AM - 5 PM");
        testResource.setDescription("Provides food assistance");
        testResource.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        testResource.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

    }

     @Test
    public void testGetUsers() throws Exception {
        List<User> users = Arrays.asList(testUser);

        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(get("/getUsers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{" +
                        "\"userId\":" + testUser.getUserId() + "," +
                        "\"name\":\"" + testUser.getName() + "\"," +
                        "\"email\":\"" + testUser.getEmail() + "\"" +
                        "}]"));
    }

    @Test
    public void testGetUserByIdGivenExistingId() throws Exception {
        // Mocking the repository to return the test user when queried by ID
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));

        mockMvc.perform(get("/getUser?id=" + testUser.getUserId()) 
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"userId\":" + testUser.getUserId() + ",\"name\":\"" + testUser.getName() + "\",\"email\":\"" + testUser.getEmail() + "\"}"));
    }

    @Test
    public void testGetUserByIdGivenNonExistingId() throws Exception {
        mockMvc.perform(get("/getUser?id=1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User Not Found"));
    }

    @Test
    public void testCreateUser() throws Exception {
        // Mocking the repository to return the test user when saved
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/createUser")
                .param("name", testUser.getName())
                .param("email", testUser.getEmail())
                .param("age", String.valueOf(testUser.getAge()))
                .param("sex", testUser.getSex().name()) 
                .param("latitude", String.valueOf(testUser.getLatitude())) 
                .param("longitude", String.valueOf(testUser.getLongitude()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)) 
            .andExpect(status().isCreated())
            .andExpect(content().json(String.format("{\"userId\":%d,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}", testUser.getUserId())));
    }

    @Test
    public void testUpdateUserAttributeGivenExistingId() throws Exception {
        // Mocking the repository to return the test user when queried by ID
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));

        mockMvc.perform(patch("/updateUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "name")
                .param("value", "Jane Doe"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"userId\":" + testUser.getUserId() + ",\"name\":\"Jane Doe\",\"email\":\"" + testUser.getEmail() + "\"}"));
    }

    @Test
    public void testUpdateUserAttributeGivenNonExistingId() throws Exception {
        mockMvc.perform(patch("/updateUser")
                .param("id", "99") 
                .param("attribute", "name")
                .param("value", "New Name"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User Not Found"));
    }
    @Test
    public void testGetAllCommunityGroups() throws Exception {
        List<CommunityGroup> communityGroups = Arrays.asList(testCommunityGroup);
        when(communityGroupRepository.findAll()).thenReturn(communityGroups);

        mockMvc.perform(get("/getCommunityGroups")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("[{\"communityName\":\"Local Food Bank\"}]")); // Adjust as necessary for JSON response
    }

    @Test
    public void testGetCommunityGroupByIdGivenExistingId() throws Exception {
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId())).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(get("/getCommunityGroup?id=" + testCommunityGroup.getCommunityId())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"communityName\":\"Local Food Bank\"}"));
    }

    @Test
    public void testGetCommunityGroupByIdGivenNonExistingId() throws Exception {
        when(communityGroupRepository.findById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/getCommunityGroup?id=99")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Community Group Not Found"));
    }

    @Test
    public void testGetCommunityGroupsByType_ReturnsOnlyMentalHealth() throws Exception {
        CommunityGroup mentalhealth = new CommunityGroup();
        mentalhealth.setCommunityType(CommunityType.MENTAL_HEALTH);
        mentalhealth.setCommunityName("Mental Health Group");
        mentalhealth.setDescription("Mental health support group.");
        mentalhealth.setLatitude(40);
        mentalhealth.setLongitude(-75);

        CommunityGroup other = new CommunityGroup();
        other.setCommunityType(CommunityType.OTHER);
        other.setCommunityName("Other");
        other.setDescription("Extra support group.");
        other.setLatitude(40);
        other.setLongitude(-74);

        when(communityGroupRepository.findByCommunityType(CommunityType.MENTAL_HEALTH)).thenReturn(Arrays.asList(mentalhealth));

        mockMvc.perform(get("/getCommunityGroupsByType?type=MENTAL_HEALTH")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();

                    assertTrue(responseContent.contains("\"communityType\":\"MENTAL_HEALTH\""));
                    assertFalse(responseContent.contains("\"communityType\":\"OTHER\""));
                });
    }

    @Test
    void testGetCommunityGroupByType_NotFound() throws Exception {
        CommunityType communityType = CommunityType.MENTAL_HEALTH;
        when(communityGroupRepository.findByCommunityType(communityType)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/getCommunityGroupsByType")
                        .param("type", communityType.name())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No community groups were found for type: " + communityType));
    }

    @Test
    public void testGetCommunityGroupByIdWithAttributeCommunityName() throws Exception {
        // Mocking the repository to return the test community group when queried by ID
        when(communityGroupRepository.findById(1)).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(get("/getCommunityGroup?id=1&attribute=communityName")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(testCommunityGroup.getCommunityName()));
    }

    @Test
    public void testGetCommunityGroupByIdWithAttributeCommunityType() throws Exception {
        when(communityGroupRepository.findById(1)).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(get("/getCommunityGroup?id=1&attribute=communityType")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(testCommunityGroup.getCommunityType().toString()));
    }

    @Test
    public void testGetCommunityGroupByIdWithAttributeLatitude() throws Exception {
        when(communityGroupRepository.findById(1)).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(get("/getCommunityGroup?id=1&attribute=latitude")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(testCommunityGroup.getLatitude())));
    }

    @Test
    public void testGetCommunityGroupByIdWithInvalidAttribute() throws Exception {
        when(communityGroupRepository.findById(1)).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(get("/getCommunityGroup?id=1&attribute=invalidAttribute")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Attribute Not Found"));
    }

    @Test
    public void testCreateCommunityGroup() throws Exception {
        when(communityGroupRepository.save(any(CommunityGroup.class))).thenReturn(testCommunityGroup);

        // Perform the request with parameters from the testCommunityGroup
        mockMvc.perform(post("/createCommunityGroup")
                .param("communityName", testCommunityGroup.getCommunityName())
                .param("communityType", testCommunityGroup.getCommunityType().name())
                .param("latitude", String.valueOf(testCommunityGroup.getLatitude()))
                .param("longitude", String.valueOf(testCommunityGroup.getLongitude()))
                .param("capacity", String.valueOf(testCommunityGroup.getCapacity()))
                .param("description", testCommunityGroup.getDescription())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isCreated()) 
            .andExpect(content().json("{\"communityName\":\"" + testCommunityGroup.getCommunityName() + "\"}"));
    }

    @Test
    public void testUpdateCommunityGroupAttributesGivenExistingId() throws Exception {
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId())).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(patch("/updateCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "communityName")
                .param("value", "Updated Food Bank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Updated Food Bank")));
    }

    @Test
    public void testUpdateCommunityGroupAttributesGivenNonExistingId() throws Exception {
        when(communityGroupRepository.findById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(patch("/updateCommunityGroup")
                .param("id", "99")
                .param("attribute", "communityName")
                .param("value", "Updated Food Bank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Community Group Not Found"));
    }

    @Test
    public void testGetAllResources() throws Exception {
        List<Resource> resources = Arrays.asList(testResource);
        when(resourceRepository.findAll()).thenReturn(resources);

        mockMvc.perform(get("/getAllResources")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Food Bank")));
    }

    @Test
    public void testGetResourcesByType_ReturnsOnlyShelter() throws Exception {
        Resource shelter = new Resource();
        shelter.setResourceType(ResourceType.SHELTER);
        shelter.setResourceName("Local Shelter");
        shelter.setDescription("A local shelter for the homeless.");
        shelter.setLatitude(40);
        shelter.setLongitude(-75);

        Resource foodBank = new Resource();
        foodBank.setResourceType(ResourceType.FOOD_BANK);
        foodBank.setResourceName("City Food Bank");
        foodBank.setDescription("Food bank providing meals.");
        foodBank.setLatitude(40);
        foodBank.setLongitude(-74);

        when(resourceRepository.findByResourceType(ResourceType.SHELTER)).thenReturn(Arrays.asList(shelter));

        mockMvc.perform(get("/getResourcesByType?type=SHELTER")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();

                    assertTrue(responseContent.contains("\"resourceType\":\"SHELTER\""));
                    assertFalse(responseContent.contains("\"resourceType\":\"FOOD_BANK\""));
                });
    }

    @Test
    void testGetResourcesByType_NotFound() throws Exception {
        ResourceType resourceType = ResourceType.SHELTER;
        when(resourceRepository.findByResourceType(resourceType)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/getResourcesByType")
                        .param("type", resourceType.name())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No resources were found for type: " + resourceType));
    }

    @Test
    public void testGetResourceByIdGivenExistingId() throws Exception {
        when(resourceRepository.findById(testResource.getResourceId())).thenReturn(java.util.Optional.of(testResource));

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Food Bank")));
    }

    @Test
    public void testGetResourceByIdGivenNonExistingId() throws Exception {
        when(resourceRepository.findById(2)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/getResource")
                .param("id", "2")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Resource Not Found"));
    }

    @Test
    public void testCreateResource() throws Exception {
        when(resourceRepository.save(any(Resource.class))).thenReturn(testResource);

        mockMvc.perform(post("/createResource")
                .param("resourceName", testResource.getResourceName())
                .param("resourceType", testResource.getResourceType().name())
                .param("latitude", String.valueOf(testResource.getLatitude()))
                .param("longitude", String.valueOf(testResource.getLongitude()))
                .param("resourceHours", testResource.getResourceHours())
                .param("description", testResource.getDescription())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isCreated())
            .andExpect(content().string(containsString("Food Bank")));
    }

    @Test
    public void testUpdateResourceAttributesGivenExistingId() throws Exception {
        when(resourceRepository.findById(testResource.getResourceId())).thenReturn(java.util.Optional.of(testResource));

        mockMvc.perform(patch("/updateResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "resourcename")
                .param("value", "Updated Food Bank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Updated Food Bank")));
    }

    @Test
    public void testUpdateResourceAttributesGivenNonExistingId() throws Exception {
        when(resourceRepository.findById(2)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(patch("/updateResource")
                .param("id", "2")
                .param("attribute", "resourcename")
                .param("value", "Updated Food Bank")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Resource Not Found"));
    }

    @Test
    public void testDeleteUserGivenExistingId() throws Exception {
        when(userRepository.existsById(testUser.getUserId())).thenReturn(true);
        doNothing().when(userRepository).deleteById(testUser.getUserId());

        mockMvc.perform(delete("/deleteUser")
                .param("id", String.valueOf(testUser.getUserId())))
            .andExpect(status().isOk())
            .andExpect(content().string("User Deleted Successfully"));
    }

    @Test
    public void testDeleteUserGivenNonExistingId() throws Exception {
        when(userRepository.findById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(delete("/deleteUser")
                .param("id", "99"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User Not Found"));
    }

    @Test
    public void testDeleteResourceGivenExistingId() throws Exception {
        when(resourceRepository.existsById(testResource.getResourceId())).thenReturn(true);
        // Mock the deletion of the resource
        doNothing().when(resourceRepository).deleteById(testResource.getResourceId());

        mockMvc.perform(delete("/deleteResource")
                .param("id", String.valueOf(testResource.getResourceId())))
            .andExpect(status().isOk())
            .andExpect(content().string("Resource Deleted Successfully"));

        // Verify that the deleteById method was called
        verify(resourceRepository, times(1)).deleteById(testResource.getResourceId());
    }

    @Test
    public void testDeleteResourceGivenNonExistingId() throws Exception {
        when(resourceRepository.findById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(delete("/deleteResource")
                .param("id", "99"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Resource Not Found"));
    }

    @Test
    public void testDeleteCommunityGroupGivenExistingId() throws Exception {
        when(communityGroupRepository.existsById(testCommunityGroup.getCommunityId())).thenReturn(true);
        doNothing().when(communityGroupRepository).deleteById(testCommunityGroup.getCommunityId());

        mockMvc.perform(delete("/deleteCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId())))
            .andExpect(status().isOk())
            .andExpect(content().string("Community Group Deleted Successfully"));
    }

    @Test
    public void testDeleteCommunityGroupGivenNonExistingId() throws Exception {
        when(communityGroupRepository.findById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(delete("/deleteCommunityGroup")
                .param("id", "99"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Community Group Not Found"));
    }

    @Test
    public void testAddUserToCommunityGroupGivenExistingId() throws Exception {
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId())).thenReturn(java.util.Optional.of(testCommunityGroup));

        when(userCommunityRepository.existsByUserAndCommunity(testUser, testCommunityGroup)).thenReturn(false);

        mockMvc.perform(post("/addUserToCommunity")
                .param("userId", String.valueOf(testUser.getUserId()))
                .param("communityId", String.valueOf(testCommunityGroup.getCommunityId())))
            .andExpect(status().isCreated())
            .andExpect(content().string("User added to community group successfully"));
    }

    @Test
    public void testAddUserToCommunityGroupGivenNonExistingUserId() throws Exception {
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId())).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(post("/addUserToCommunity")
                .param("userId", "99")
                .param("communityId", String.valueOf(testCommunityGroup.getCommunityId())))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User Not Found"));
    }

    @Test
    public void testAddUserToCommunityGroupGivenNonExistingCommunityGroupId() throws Exception {
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));

        mockMvc.perform(post("/addUserToCommunity")
                .param("userId", String.valueOf(testUser.getUserId()))
                .param("communityId", "99"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Community Group Not Found"));
    }

   @Test
    public void testRemoveUserFromCommunityGivenExistingIds() throws Exception {
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId())).thenReturn(java.util.Optional.of(testCommunityGroup));
        when(userCommunityRepository.existsByUserAndCommunity(testUser, testCommunityGroup)).thenReturn(true);
        when(userCommunityRepository.findByUserAndCommunity(testUser, testCommunityGroup)).thenReturn(new UserCommunity(testUser, testCommunityGroup));

        mockMvc.perform(delete("/removeUserFromCommunity")
                .param("userId", String.valueOf(testUser.getUserId()))
                .param("communityId", String.valueOf(testCommunityGroup.getCommunityId())))
            .andExpect(status().isOk())
            .andExpect(content().string("User removed from community group successfully"));

        // Verify that the delete operation was called with the correct UserCommunity
        verify(userCommunityRepository, times(1)).delete(any(UserCommunity.class));
    }

    @Test
    public void testRemoveUserFromCommunityNotAMember() throws Exception {
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId())).thenReturn(java.util.Optional.of(testCommunityGroup));
        when(userCommunityRepository.existsByUserAndCommunity(testUser, testCommunityGroup)).thenReturn(false);

        mockMvc.perform(delete("/removeUserFromCommunity")
                .param("userId", String.valueOf(testUser.getUserId()))
                .param("communityId", String.valueOf(testCommunityGroup.getCommunityId())))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User is not a member of the community group"));
    }

    @Test
    public void testRemoveUserFromCommunityGivenNonExistingUserId() throws Exception {
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId())).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(delete("/removeUserFromCommunity")
                .param("userId", "99")
                .param("communityId", String.valueOf(testCommunityGroup.getCommunityId())))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User not found"));
    }

    @Test
    public void testRemoveUserFromCommunityGivenNonExistingCommunityGroupId() throws Exception {
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));

        mockMvc.perform(delete("/removeUserFromCommunity")
                .param("userId", String.valueOf(testUser.getUserId()))
                .param("communityId", "99"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Community Group not found"));
    }

}
