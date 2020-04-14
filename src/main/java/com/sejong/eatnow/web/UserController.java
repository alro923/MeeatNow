package com.sejong.eatnow.web;

import com.sejong.eatnow.service.UserService;
import com.sejong.eatnow.web.dto.UserRequestDto;
import com.sejong.eatnow.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:8080")
//옵션들
//origins
//
//methods
//
//allowedHeaders
//
//exposedHeaders
//
//allowCredentials
//
//maxAge.
@RequestMapping("/user/*")
public class UserController {

    private final UserService service;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody UserRequestDto dto) {
        log.info("insert (controller) 진입: "+dto.getEmail()+", "+dto.getName());
        ResponseEntity<String> entity = null;
        try {
            service.insert(dto);
            entity = new ResponseEntity<>("success",HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        log.info("update (controller) 진입");
        ResponseEntity<String> entity = null;
        try {
            service.update(id, dto);
            entity = new ResponseEntity<>("success",HttpStatus.OK);
        } catch (NullPointerException e) {
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserResponseDto> find(@PathVariable Long id) {
        log.info("find (controller) 진입");
        ResponseEntity<UserResponseDto> entity = null;

        try {
            UserResponseDto responseDto = service.findById(id);
            entity = new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (DataAccessException e) {
            log.warning("find user failed...." + e.getMessage());
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            log.warning("find user failed...." + e.getMessage());
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserResponseDto>> findAllDesc() {
        log.info("findAll 진입.....");
        return new ResponseEntity<>(service.findAllDesc(), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("delete (controller) 진입");
        ResponseEntity<String> entity = null;
        try {
            service.deleteById(id);
            entity = new ResponseEntity<>("success",HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
