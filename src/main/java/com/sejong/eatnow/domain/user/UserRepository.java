package com.sejong.eatnow.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u ORDER BY u.id DESC")
    List<User> findAllDesc();

    @Query("SELECT u FROM User u where u.email=?1")
    Optional<User> findByEmail(String email);
}
