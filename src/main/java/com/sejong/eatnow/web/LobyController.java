package com.sejong.eatnow.web;

import com.sejong.eatnow.service.LobyService;
import com.sejong.eatnow.web.dto.LobyRequestDto;
import com.sejong.eatnow.web.dto.LobyResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/loby/*")
public class LobyController {
    private final LobyService service;

    @PostMapping("/insert")
    public ResponseEntity<String> insert(@RequestBody LobyRequestDto dto) {

        ResponseEntity<String> entity = null;
        try {
            service.insert(dto);
            entity = new ResponseEntity<>("success",HttpStatus.OK);
            log.info("saving Loby successfully....");
        } catch (Exception e) {
            log.warning(e.getMessage());
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return entity;
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody LobyRequestDto dto) {
        ResponseEntity<String> entity = null;

        try {
            service.update(id, dto);
            entity = new ResponseEntity<>("success",HttpStatus.OK);
        } catch (NullPointerException e) {
            log.warning("loby update failed...." + e.getMessage());
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<LobyResponseDto> find(@PathVariable Long id) {
        ResponseEntity<LobyResponseDto> entity = null;
        try {
            LobyResponseDto dto = service.findById(id);
            log.info("find " + id + ":" + dto.getHostName() + "," + dto.getOpenLink() + "," + dto.getTitle() + "," + dto.getId() + "," + dto.getLocation() + "," + dto.getMeetingDate());
            entity = new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (NullPointerException e) {
            log.warning("loby find failed...." + e.getMessage());
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<LobyResponseDto>> findAllByDesc() {
        List<LobyResponseDto> list = service.findAllByDesc();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ResponseEntity<String> entity = null;
        try {
            service.delete(id);
            entity = new ResponseEntity<>("success",HttpStatus.OK);
        } catch (NullPointerException e) {
            log.warning("delete loby failed...." + e.getMessage());
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
