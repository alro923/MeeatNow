package com.sejong.eatnow.web;

import com.sejong.eatnow.service.UserService;
import com.sejong.eatnow.web.dto.UserRequestDto;
import com.sejong.eatnow.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequiredArgsConstructor
@RestController("/user")
public class UserController {

    private final UserService service;

    //insert
    @PostMapping("/insert")
    public Long insert(@RequestBody UserRequestDto dto) {
        log.info("insert (controller) 진입");
        return service.insert(dto);

    }

    //update
    @PostMapping("/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return service.update(id,dto);
    }

    //find
    @GetMapping("/find/{id}")
    public UserResponseDto find(@PathVariable Long id) {
        return service.findById(id);
    }

    //delete
    @PostMapping("/delete/{id}")
    public Long delete(@PathVariable Long id){
        return service.deleteById(id);
    }

    //findAll
    @GetMapping("/findAll")
    public List<UserResponseDto> findAllDesc(){
        return service.findAllDesc();
    }
}
