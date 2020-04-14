package com.sejong.eatnow.service;

import com.sejong.eatnow.domain.user.User;
import com.sejong.eatnow.domain.user.UserRepository;
import com.sejong.eatnow.web.dto.UserRequestDto;
import com.sejong.eatnow.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repo;

    @Transactional
    public void insert(UserRequestDto dto) {
        try {
            repo.save(dto.toEntity());
        } catch (DataIntegrityViolationException e)  //email 값이 유일성을 가지므로 중복되면 예외처리.
        {
            log.warning("insert user failed...." + e.getMessage());
            throw new DataIntegrityViolationException("다른 유저와 email이 중복됩니다.");
        }
    }

    @Transactional
    public void update(Long id, UserRequestDto dto) throws DataAccessException {

        User target = repo.findById(id).orElseThrow(
                () -> new NullPointerException("찾는 유저가 없습니다."));
        target.update(dto.getEmail(), dto.getName());
    }


    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User target = repo.findById(id).orElseThrow(
                () -> new NullPointerException("찾는 유저가 없습니다."));

        return UserResponseDto.builder()
                .id(target.getId())
                .email(target.getEmail())
                .name(target.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllDesc() {
        return repo.findAllDesc().stream()
                .map(UserResponseDto::new)  //UserResponseDto 내부에 User가 파라미터인 생성자 필요
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warning("delete user failied...." + e.getMessage());
            throw e;
        }
    }
}
