/**
 * This package contains repositories for managing Community Groups in the
 * application.
 */
package com.example.javadb.repository;

import com.example.javadb.model.CommunityGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityGroupRepository
    extends JpaRepository<CommunityGroup, Integer> {

    }