/**
 * This package contains repositories for managing Community Groups in the
 * application.
 */
package com.example.javadb.repository;

import com.example.javadb.model.CommunityGroup;
import com.example.javadb.model.CommunityGroup.CommunityType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommunityGroupRepository
        extends JpaRepository<CommunityGroup, Integer> {

  List<CommunityGroup> findByCommunityType(CommunityType type);

}

