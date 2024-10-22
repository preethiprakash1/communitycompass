/**
 * This package contains repositories for managing resources in the application.
 */
package com.example.javadb.repository;

import com.example.javadb.model.Resource;
import com.example.javadb.model.Resource.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

  /**
   * Returns a list of resources of the specified type.
   * @param type The resource type that is requested.
   * @return A list containing the resources of the specified type.
   */
  List<Resource> findByResourceType(ResourceType type);

}
