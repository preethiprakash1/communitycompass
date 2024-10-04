package com.example.javadb.repository;

import com.example.javadb.model.CommunityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// can add new funcs like finding quotes with particular word
public interface CommunityGroupRepository extends JpaRepository<CommunityGroup, Long> {

    @Query("SELECT cg FROM CommunityGroup cg WHERE cg.communitygroup LIKE %?1%")
    List<CommunityGroup> getContainingCommunityGroup(String word);
}
