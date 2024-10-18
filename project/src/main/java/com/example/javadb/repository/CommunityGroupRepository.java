package com.example.javadb.repository;

import com.example.javadb.model.CommunityGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityGroupRepository
        extends JpaRepository<CommunityGroup, Long> {

}
