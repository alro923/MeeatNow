package com.sejong.eatnow.service;

import com.sejong.eatnow.domain.location.LocationRepository;
import com.sejong.eatnow.web.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository repo;

    @Transactional
    public void insert(LocationDto dto) throws Exception{
        repo.save(dto.toEntity());
    }
}