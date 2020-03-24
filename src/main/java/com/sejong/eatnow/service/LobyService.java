package com.sejong.eatnow.service;

import com.sejong.eatnow.domain.loby.Loby;
import com.sejong.eatnow.domain.loby.LobyRepository;
import com.sejong.eatnow.domain.location.Location;
import com.sejong.eatnow.domain.location.LocationRepository;
import com.sejong.eatnow.web.dto.LobyRequestDto;
import com.sejong.eatnow.web.dto.LobyResponseDto;
import com.sejong.eatnow.web.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@Service
@RequiredArgsConstructor
public class LobyService {

    private final LobyRepository repo;
    private final LocationRepository locationRepository;

    @Transactional
    public void insert(LobyRequestDto dto) throws Exception {
        Loby target = dto.toEntity();
        LocationDto locationDto = dto.getLocationDto();
        Long longitude = locationDto.getLongitude();
        Long latitude = locationDto.getLatitude();

        Optional<Location> location = locationRepository.findByLatitudeAndLongitude(latitude,longitude); //latit,longit순
        if (location.isPresent()) {
            log.info("Yes! location is already present.");
            target.setLocation(location.get());
        } else {
            log.info("No! location isn't present yet. it'll be inserted too.... ");
            target.setLocation(locationDto.toEntity());
        }
        repo.save(target);
    }

    @Transactional
    public void update(Long id, LobyRequestDto dto) throws NullPointerException {
        Loby target = repo.findById(id).orElseThrow(
                () -> new NullPointerException("id를 찾을 수 없습니다."));

        target.update(dto);
    }

    @Transactional(readOnly = true)
    public LobyResponseDto findById(Long id) {
        Loby target = repo.findById(id).orElseThrow(
                () -> new NullPointerException("id를 찾을 수 없습니다."));

        return target.toResponseDto();
    }

    @Transactional(readOnly = true)
    public List<LobyResponseDto> findAllByDesc() {
        return repo.findAllByDesc().stream()
                .map(Loby::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) throws NullPointerException {
        repo.deleteById(id);
    }
}
