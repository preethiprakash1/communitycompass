package com.example.javadb.controller;

import com.example.javadb.model.CommunityGroup;
import com.example.javadb.repository.CommunityGroupRepository;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class RouteController {
    @Autowired
    // constructor
    public RouteController(CommunityGroupRepository communityGroupRepository) {
        this.communityGroupRepository = communityGroupRepository;
    }
    private final CommunityGroupRepository communityGroupRepository;

    @GetMapping("/welcome")
	public String welcome() {
		return "Hi, welcome to Community Compass";
	}

    @GetMapping("/getCommunityGroups")
	public ResponseEntity<?> getCommunityGroups(){
		try {
            List<CommunityGroup> items = communityGroupRepository.findAll();
            if (items.isEmpty()) {
                return new ResponseEntity<>("No Community Groups Found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
    
        } catch (Exception e) {
            return handleException(e);
        }
	}

    private ResponseEntity<?> handleException(Exception e) {
        System.out.println(e.toString());
        return new ResponseEntity<>("An Error has occurred", HttpStatus.OK);
    }

}
