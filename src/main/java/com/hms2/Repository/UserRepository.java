package com.hms2.Repository;

import com.hms2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String Username);
    Optional<User> findByEmail(String Email);
    Optional<User> findByMobile(String Mobile);
}