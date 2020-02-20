package com.sejong.eatnow.service;

import com.sejong.eatnow.domain.user.User;
import com.sejong.eatnow.domain.user.UserRepository;
import com.sejong.eatnow.web.dto.UserRequestDto;
import com.sejong.eatnow.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repo;

    @Transactional
    public Long insert(UserRequestDto dto){

        return repo.save(dto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, UserRequestDto dto){
        User target = repo.findById(id).orElseThrow(
                ()->new IllegalArgumentException("id를 찾을 수 없습니다."));

        target.update(target.getEmail(), target.getName());
        return target.getId();
    }

    @Transactional
    public Long deleteById(Long id) {
        repo.deleteById(id);
        return id;
    }
    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id){
        User target = repo.findById(id).orElseThrow(
                ()->new IllegalArgumentException("id를 찾을 수 없습니다."));

        return UserResponseDto.builder()
                .id(target.getId())
                .email(target.getEmail())
                .name(target.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllDesc(){
        return repo.findAllDesc().stream()
                .map(UserResponseDto::new)  //UserResponseDto 내부에 User가 파라미터인 생성자 필요
                .collect(Collectors.toList());
    }
}
