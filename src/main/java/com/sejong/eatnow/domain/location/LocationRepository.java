package com.sejong.eatnow.domain.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    public Optional<Location> findByLatitudeAndLongitude(Long latitude, Long longitude);
}
