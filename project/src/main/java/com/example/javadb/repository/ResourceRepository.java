/**
 * This package contains repositories for managing resources in the application.
 */
package com.example.javadb.repository;

import com.example.javadb.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
