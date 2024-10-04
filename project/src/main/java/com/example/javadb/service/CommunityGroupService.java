package com.example.javadb.service;

// service is used for the following:
// if i had a StoryRepo i can interact both Quote and Storyrepo in this service file
// if i had 2 tables that refer to each other. organizing employees + offices relationships


import com.example.javadb.model.CommunityGroup;
import com.example.javadb.repository.CommunityGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityGroupService {
    @Autowired
    CommunityGroupRepository communityGroupRepository;
    public CommunityGroup createCommunityGroup(CommunityGroup communityGroupString){
        return communityGroupRepository.save(communityGroupString);
    }
    public List<CommunityGroup> getQuotes() {
        return communityGroupRepository.findAll();
    }
    public void deleteCommunityGroup(Long ID) {
        communityGroupRepository.deleteById(ID);
    }

    public CommunityGroup updateCommunityGroup(Long ID, CommunityGroup newCommunityGroupString) {
        CommunityGroup communityGroup = communityGroupRepository.findById(ID).get();
        communityGroup.setCommunityGroup(newCommunityGroupString.getCommunityGroup());
        return communityGroupRepository.save(newCommunityGroupString);
    }
}