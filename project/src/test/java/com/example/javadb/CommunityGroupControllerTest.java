package com.example.javadb;

import com.example.javadb.controller.CommunityGroupController;
import com.example.javadb.model.CommunityGroup;
import com.example.javadb.repository.CommunityGroupRepository;
import com.example.javadb.repository.CommunityGroupRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommunityGroupControllerTest {

    @Test
    // make test for @GetMapping("/todo/{id}")
    public void testThatInvalidIdProducesA404(){
        // SETUP
        CommunityGroupRepository mockRepository = Mockito.mock(CommunityGroupRepository.class);

        Mockito.when(mockRepository.findById(100000L)).thenReturn(Optional.empty());
        CommunityGroupController communityGroupController = new CommunityGroupController(mockRepository);

        // CALL
        ResponseEntity<String> responseEntity =
                communityGroupController.readCommunityGroup(100000L);

        // ASSERTIONS
        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNull( responseEntity.getBody() );
    }

    @Test
    public void testThatValidIdProducesA200(){

        // SETUP
        CommunityGroupRepository mockRepository = Mockito.mock(CommunityGroupRepository.class);

        CommunityGroup idCommunityGroup = new CommunityGroup();
        idCommunityGroup.setCommunityGroup("Community group here");

        Mockito
                .when(mockRepository.findById(1L))
                .thenReturn(Optional.of(idCommunityGroup));
        CommunityGroupController communityGroupController =
                new CommunityGroupController(mockRepository);

        // CALL
        ResponseEntity<String> responseEntity = communityGroupController.readCommunityGroup(1L);

        // ASSERTIONS
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Quote phrase here", responseEntity.getBody() );
    }

}