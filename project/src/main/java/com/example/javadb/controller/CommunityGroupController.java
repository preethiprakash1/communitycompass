package com.example.javadb.controller;

import com.example.javadb.model.CommunityGroup;
import com.example.javadb.repository.CommunityGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommunityGroupController {
    @Autowired
    // constructor
    public CommunityGroupController(CommunityGroupRepository communityGroupRepository) {
        this.communityGroupRepository = communityGroupRepository;
    }
    private final CommunityGroupRepository communityGroupRepository;

    @GetMapping("/communitygroups")
    public List<CommunityGroup> getCommunityGroups(@RequestParam("search") Optional<String> searchParam){
        return searchParam
                .map(communityGroupRepository::getContainingCommunityGroup)
                .orElse(communityGroupRepository.findAll());
    }

}
