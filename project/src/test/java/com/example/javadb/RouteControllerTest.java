package com.example.javadb;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RouteController.class)
public class RouteControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResourceRepository resourceRepository;

    @MockBean
    private CommunityGroupRepository communityGroupRepository;

    @MockBean
    private UserRepository userRepository;

    private User testUser;
    private CommunityGroup testCommunityGroup;
    private Resource testResource;

    @BeforeEach
    public void setUp() {

        testUser = new User();
        testUser.setUserId(1);
        testUser.setUserName("John Doe");
        testUser.setEmail("john.doe@example.com");

        testCommunityGroup = new CommunityGroup();
        testCommunityGroup.setCommunityId(1);
        testCommunityGroup.setCommunityName("Local Food Bank");
        testCommunityGroup.setCommunityType(CommunityType.FOOD);
        testCommunityGroup.setLatitude(40.7128);
        testCommunityGroup.setLongitude(-74.0060);
        testCommunityGroup.setCapacity(100);
        testCommunityGroup.setDescription("Provides food assistance to the community");

        testResource = new Resource();
        testResource.setResourceId(1);
        testResource.setResourceName("Food Bank");
        testResource.setResourceType(ResourceType.FOOD);
        testResource.setLatitude(40.7128);
        testResource.setLongitude(-74.0060);
        testResource.setResourceHours("9 AM - 5 PM");
        testResource.setDescription("Provides food assistance");

    }

    @Test
    public void testGetUserById_ExistingId() throws Exception {
        // Mocking the repository to return the test user when queried by ID
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser));

        // Performing the GET request and asserting the response
        mockMvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"userId\":1,\"userName\":\"John Doe\",\"email\":\"john.doe@example.com\"}"));
    }

    @Test
    public void testGetUserById_NonExistingId() throws Exception {
        // Mocking the repository to return an empty Optional for a non-existing ID
        when(userRepository.findById(99)).thenReturn(java.util.Optional.empty());

        // Performing the GET request and asserting the response
        mockMvc.perform(get("/users/99")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User Not Found"));
    }

    @Test
    public void testCreateUser() throws Exception {
        // Mocking the repository to return the test user when saved
        when(userRepository.save(testUser)).thenReturn(testUser);

        // Performing the POST request to create a user
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().json("{\"userId\":1,\"userName\":\"John Doe\",\"email\":\"john.doe@example.com\"}"));
    }

    @Test
    public void testUpdateUserAttribute_ExistingId() throws Exception {
        // Mocking the repository to return the test user when queried by ID
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser));

        // Performing the PATCH request to update the user name
        mockMvc.perform(patch("/users/update")
                .param("id", "1")
                .param("attribute", "userName")
                .param("value", "Jane Doe"))
            .andExpect(status().isOk())
            .andExpect(content().json("{\"userId\":1,\"userName\":\"Jane Doe\",\"email\":\"john.doe@example.com\"}"));
    }

    @Test
    public void testUpdateUserAttribute_NonExistingId() throws Exception {
        // Mocking the repository to return an empty Optional for a non-existing ID
        when(userRepository.findById(99)).thenReturn(java.util.Optional.empty());

        // Performing the PATCH request for a non-existing user
        mockMvc.perform(patch("/users/update")
                .param("id", "99")
                .param("attribute", "userName")
                .param("value", "New Name"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User Not Found"));
    }

    @Test
    public void testDeleteUser_ExistingId() throws Exception {
        // Mocking the repository to simulate the user deletion
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(testUser));

        // Performing the DELETE request to delete the user
        mockMvc.perform(delete("/users/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUser_NonExistingId() throws Exception {
        // Mocking the repository to return an empty Optional for a non-existing ID
        when(userRepository.findById(99)).thenReturn(java.util.Optional.empty());

        // Performing the DELETE request for a non-existing user
        mockMvc.perform(delete("/users/99"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User Not Found"));
    }
    
    @Test
    public void testGetAllCommunityGroups() throws Exception {
        List<CommunityGroup> communityGroups = Arrays.asList(testCommunityGroup);
        when(communityGroupRepository.findAll()).thenReturn(communityGroups);

        mockMvc.perform(get("/communityGroups")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Local Food Bank")));
    }

    @Test
    public void testGetCommunityGroupById_ExistingId() throws Exception {
        when(communityGroupRepository.findById(1)).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(get("/communityGroups/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Local Food Bank")));
    }

    @Test
    public void testGetCommunityGroupById_NonExistingId() throws Exception {
        when(communityGroupRepository.findById(2)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/communityGroups/2")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Community Group Not Found"));
    }

    @Test
    public void testCreateCommunityGroup() throws Exception {
        String newCommunityGroupJson = "{\"communityName\":\"Shelter\",\"communityType\":\"SHELTER\",\"latitude\":40.7128,\"longitude\":-74.0060,\"capacity\":50,\"description\":\"Provides shelter services\"}";

        when(communityGroupRepository.save(any(CommunityGroup.class))).thenReturn(testCommunityGroup);

        mockMvc.perform(post("/communityGroups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommunityGroupJson))
            .andExpect(status().isCreated())
            .andExpect(content().string(containsString("Shelter")));
    }

    @Test
    public void testUpdateCommunityGroupAttributes_ExistingId() throws Exception {
        String updateCommunityGroupJson = "{\"attribute\":\"communityName\",\"value\":\"Updated Food Bank\"}";

        when(communityGroupRepository.findById(1)).thenReturn(java.util.Optional.of(testCommunityGroup));

        mockMvc.perform(patch("/communityGroups/updateCommunityGroup")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateCommunityGroupJson))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Updated Food Bank")));
    }

    @Test
    public void testUpdateCommunityGroupAttributes_NonExistingId() throws Exception {
        String updateCommunityGroupJson = "{\"attribute\":\"communityName\",\"value\":\"Updated Food Bank\"}";

        when(communityGroupRepository.findById(2)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(patch("/communityGroups/updateCommunityGroup")
                .param("id", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateCommunityGroupJson))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Community Group Not Found"));
    }

    @Test
    public void testDeleteCommunityGroup_ExistingId() throws Exception {
        when(communityGroupRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/communityGroups/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Community Group deleted successfully"));
    }

    @Test
    public void testDeleteCommunityGroup_NonExistingId() throws Exception {
        when(communityGroupRepository.existsById(2)).thenReturn(false);

        mockMvc.perform(delete("/communityGroups/2"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Community Group Not Found"));
    }

    @Test
    public void testGetAllResources() throws Exception {
        List<Resource> resources = Arrays.asList(testResource);
        when(resourceRepository.findAll()).thenReturn(resources);

        mockMvc.perform(get("/resources")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Food Bank")));
    }

    @Test
    public void testGetResourceById_ExistingId() throws Exception {
        when(resourceRepository.findById(1)).thenReturn(java.util.Optional.of(testResource));

        mockMvc.perform(get("/resources/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Food Bank")));
    }

    @Test
    public void testGetResourceById_NonExistingId() throws Exception {
        when(resourceRepository.findById(2)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/resources/2")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Resource Not Found"));
    }

    @Test
    public void testCreateResource() throws Exception {
        String newResourceJson = "{\"resourceName\":\"Shelter\",\"resourceType\":\"SHELTER\",\"latitude\":40.7128,\"longitude\":-74.0060,\"resourceHours\":\"24/7\",\"description\":\"Provides shelter services\"}";

        when(resourceRepository.save(any(Resource.class))).thenReturn(testResource);

        mockMvc.perform(post("/resources")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newResourceJson))
            .andExpect(status().isCreated())
            .andExpect(content().string(containsString("Shelter")));
    }

    @Test
    public void testUpdateResourceAttributes_ExistingId() throws Exception {
        String updateResourceJson = "{\"attribute\":\"resourceName\",\"value\":\"Updated Food Bank\"}";

        when(resourceRepository.findById(1)).thenReturn(java.util.Optional.of(testResource));

        mockMvc.perform(patch("/resources/updateResource")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateResourceJson))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Updated Food Bank")));
    }

    @Test
    public void testUpdateResourceAttributes_NonExistingId() throws Exception {
        String updateResourceJson = "{\"attribute\":\"resourceName\",\"value\":\"Updated Food Bank\"}";

        when(resourceRepository.findById(2)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(patch("/resources/updateResource")
                .param("id", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateResourceJson))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Resource Not Found"));
    }

    @Test
    public void testDeleteResource_ExistingId() throws Exception {
        when(resourceRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/resources/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Resource deleted successfully"));
    }

    @Test
    public void testDeleteResource_NonExistingId() throws Exception {
        when(resourceRepository.existsById(2)).thenReturn(false);

        mockMvc.perform(delete("/resources/2"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Resource Not Found"));
    }
}
