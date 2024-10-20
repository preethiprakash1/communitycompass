/**
 * This package contains repositories for managing Users in the application.
 */
package com.example.javadb.repository;

import com.example.javadb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
