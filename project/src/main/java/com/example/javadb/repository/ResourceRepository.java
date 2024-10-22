/**
 * This package contains repositories for managing resources in the application.
 */
package com.example.javadb.repository;

import com.example.javadb.model.Resource;
import com.example.javadb.model.Resource.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

  List<Resource> findByResourceType(ResourceType type);

}
