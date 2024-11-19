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
    private CommunityGroup testCommunityGroup1;
    private CommunityGroup testCommunityGroup2;
    private CommunityGroup testCommunityGroup3;
    private Resource testResource;
    private Resource testResource1;
    private Resource testResource2;
    private Resource testResource3;

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

        // Test Community Group 2 (farther)
        testCommunityGroup2 = new CommunityGroup();
        testCommunityGroup2.setCommunityName("Mental Health Support");
        testCommunityGroup2.setCommunityType(CommunityGroup.CommunityType.MENTAL_HEALTH);
        testCommunityGroup2.setLatitude(40.650002); // Brooklyn, NYC
        testCommunityGroup2.setLongitude(-73.949997); // Brooklyn, NYC
        testCommunityGroup2.setCapacity(30);
        testCommunityGroup2.setDescription("Provides mental health support");
        testCommunityGroup2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        testCommunityGroup2.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // Test Community Group 3 (farther)
        testCommunityGroup3 = new CommunityGroup();
        testCommunityGroup3.setCommunityName("Brooklyn Employment Assistance Group");
        testCommunityGroup3.setCommunityType(CommunityGroup.CommunityType.EMPLOYMENT_ASSISTANCE);
        testCommunityGroup3.setLatitude(40.650002); // Brooklyn, NYC
        testCommunityGroup3.setLongitude(-73.949997); // Brooklyn, NYC
        testCommunityGroup3.setCapacity(30);
        testCommunityGroup3.setDescription("Provides mental health support");
        testCommunityGroup3.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        testCommunityGroup3.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

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

        // Test Resource 2 (farther)
        testResource2 = new Resource();
        testResource2.setResourceName("Community Food Bank");
        testResource2.setResourceType(Resource.ResourceType.FOOD_BANK);
        testResource2.setLatitude(40.730610); // Brooklyn, NYC
        testResource2.setLongitude(-73.935242); // Brooklyn, NYC
        testResource2.setResourceHours("9AM-5PM");
        testResource2.setDescription("Provides food to the needy");
        testResource2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        testResource2.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // Test Resource 3 (farther)
        testResource3 = new Resource();
        testResource3.setResourceName("Brooklyn Local Shelter");
        testResource3.setResourceType(Resource.ResourceType.SHELTER);
        testResource3.setLatitude(40.730610); // Brooklyn, NYC
        testResource3.setLongitude(-73.935242); // Brooklyn, NYC
        testResource3.setResourceHours("9AM-5PM");
        testResource3.setDescription("Provides temporary shelter");
        testResource3.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        testResource3.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

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
            .andExpect(content().json("[{\"communityName\":\"Local Food Bank\"}]"));
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
    public void testGetCommunityGroupByIdWithValidAttributeName() throws Exception {
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId()))
            .thenReturn(java.util.Optional.of(testCommunityGroup));

        // Test each valid attribute
        mockMvc.perform(get("/getCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "communityname")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(testCommunityGroup.getCommunityName()));

        mockMvc.perform(get("/getCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "communitytype")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(testCommunityGroup.getCommunityType().toString()));

        mockMvc.perform(get("/getCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "latitude")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testCommunityGroup.getLatitude())));

        mockMvc.perform(get("/getCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "longitude")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testCommunityGroup.getLongitude())));

        mockMvc.perform(get("/getCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "capacity")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testCommunityGroup.getCapacity())));

        mockMvc.perform(get("/getCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "description")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(testCommunityGroup.getDescription()));

        mockMvc.perform(get("/getCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "usercount")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testCommunityGroup.getNumberOfUsers())));
    }

    @Test
    public void testGetCommunityGroupByIdWithInvalidAttributeName() throws Exception {
        when(communityGroupRepository.findById(testCommunityGroup.getCommunityId()))
            .thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(get("/getCommunityGroup")
                .param("id", String.valueOf(testCommunityGroup.getCommunityId()))
                .param("attribute", "invalidAttribute")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Attribute Not Found"));
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
    public void testGetClosestCommunityGroup() throws Exception {
        // Mock the repository to return both community groups
        List<CommunityGroup> communityGroups = Arrays.asList(testCommunityGroup1, testCommunityGroup2, testCommunityGroup3);
        when(communityGroupRepository.findByCommunityType(CommunityGroup.CommunityType.EMPLOYMENT_ASSISTANCE))
            .thenReturn(communityGroups);

        // Perform the request with a latitude and longitude close to testCommunityGroup1
        mockMvc.perform(get("/getClosestCommunityGroup")
                .param("type", "EMPLOYMENT_ASSISTANCE")
                .param("latitude", "40.730610") // User's location (NYC)
                .param("longitude", "-73.935242") // User's location (NYC)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"communityName\":\"NYC Employment Assistance Group\"}")); // Adjust to match response
    }

    @Test
    public void testGetClosestCommunityGroupWhenNoGroupsFound() throws Exception {
        // Mock the repository to return an empty list
        when(communityGroupRepository.findByCommunityType(CommunityGroup.CommunityType.EMPLOYMENT_ASSISTANCE))
            .thenReturn(Arrays.asList());

        // Perform the request and expect a 404 response
        mockMvc.perform(get("/getClosestCommunityGroup")
                .param("type", "EMPLOYMENT_ASSISTANCE")
                .param("latitude", "40.730610")
                .param("longitude", "-73.935242")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("No community groups were found for type: EMPLOYMENT_ASSISTANCE"));
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
    public void testGetClosestResource() throws Exception {
        // Mock the repository to return both resources
        List<Resource> resources = Arrays.asList(testResource1, testResource2, testResource3);
        when(resourceRepository.findByResourceType(Resource.ResourceType.SHELTER))
            .thenReturn(resources);

        // Perform the request with a latitude and longitude close to testResource1
        mockMvc.perform(get("/getClosestResource")
                .param("type", "SHELTER")
                .param("latitude", "40.7128") // User's location (NYC)
                .param("longitude", "-74.0060") // User's location (NYC)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"resourceName\":\"NYC Local Shelter\"}"));
    }

    @Test
    public void testGetClosestResourceWhenNoResourcesFound() throws Exception {
        // Mock the repository to return an empty list
        when(resourceRepository.findByResourceType(Resource.ResourceType.SHELTER))
            .thenReturn(Arrays.asList());

        // Perform the request and expect a 404 response
        mockMvc.perform(get("/getClosestResource")
                .param("type", "SHELTER")
                .param("latitude", "40.7128")
                .param("longitude", "-74.0060")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("No resources were found for type: SHELTER"));
    }

    @Test
    public void testGetResourceByIdWithValidIdAndAttributeName() throws Exception {
        when(resourceRepository.findById(testResource.getResourceId())).thenReturn(java.util.Optional.of(testResource));

        // Test each attribute in the switch statement
        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "resourcename")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(testResource.getResourceName()));

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "latitude")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testResource.getLatitude())));

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "longitude")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testResource.getLongitude())));

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "description")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(testResource.getDescription()));

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "resourcehours")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(testResource.getResourceHours()));

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "createdat")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "updatedat")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetResourceByIdWithInvalidAttribute() throws Exception {
        when(resourceRepository.findById(testResource.getResourceId())).thenReturn(java.util.Optional.of(testResource));

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .param("attribute", "invalidAttribute")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Attribute Not Found"));
    }

    @Test
    public void testGetResourceByIdWithValidIdButNoAttributeName() throws Exception {
        when(resourceRepository.findById(testResource.getResourceId())).thenReturn(java.util.Optional.of(testResource));

        mockMvc.perform(get("/getResource")
                .param("id", String.valueOf(testResource.getResourceId()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"resourceName\":\"Food Bank\"}")); 
    }

    @Test
    public void testGetResourceByIdWithNonExistingId() throws Exception {
        when(resourceRepository.findById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/getResource")
                .param("id", "99")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Resource Not Found"));
    }

    @Test
    public void testGetUserByIdWithValidIdAndAttributeName() throws Exception {
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));

        // Test each valid attribute
        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "name")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(testUser.getName()));

        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "email")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(testUser.getEmail()));

        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "age")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testUser.getAge())));

        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "sex")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("\"" + testUser.getSex() + "\""));

        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "latitude")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testUser.getLatitude())));

        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "longitude")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testUser.getLongitude())));

        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "communitycount")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(String.valueOf(testUser.getNumberOfCommunities())));
    }

    @Test
    public void testGetUserByIdWithValidIdAndNoAttributeName() throws Exception {
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));

        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"name\":\"" + testUser.getName() + "\"}")); // Adjust to match full JSON
    }

    @Test
    public void testGetUserByIdWithNonExistingId() throws Exception {
        when(userRepository.findById(99)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/getUser")
                .param("id", "99")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User Not Found"));
    }

    @Test
    public void testGetUserByIdWithInvalidAttribute() throws Exception {
        when(userRepository.findById(testUser.getUserId())).thenReturn(java.util.Optional.of(testUser));

        mockMvc.perform(get("/getUser")
                .param("id", String.valueOf(testUser.getUserId()))
                .param("attribute", "invalidAttribute")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Attribute Not Found"));
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
