package com.sejong.eatnow.domain.loby;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LobyRepository extends JpaRepository<Loby, Long> {

    @Query("SELECT r FROM Loby r ORDER BY r.id desc")
    List<Loby> findAllByDesc();
}
