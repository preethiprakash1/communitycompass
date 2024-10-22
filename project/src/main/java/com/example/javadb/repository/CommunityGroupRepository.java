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

  /**
   * Returns a list of community groups of the specified type.
   * @param type The community type that is requested.
   * @return A list containing the community groups of the specified type.
   */
  List<CommunityGroup> findByCommunityType(CommunityType type);

}

