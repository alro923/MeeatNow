package com.sejong.eatnow.web;

import com.sejong.eatnow.domain.loby.Loby;
import com.sejong.eatnow.domain.loby.LobyRepository;
import com.sejong.eatnow.domain.location.Location;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log
@Commit
@NoArgsConstructor
public class LobyTest {

    @Autowired
    private LobyRepository repo;

    @Test
    @Transactional
    public void insert_Loby() {
        Loby loby = Loby.builder()
                .title("toona party")
                .hostName("dongwon")
                .openLink("http://open-55")
                .location(Location.builder()
//                        .id(1L)  --> tx rollback-error도 이것때문에 생긴다.
                        .longitude(1111L)
                        .latitude(2222L)
                        .build())
                .meetingDate(LocalDate.of(2020, 3, 02))
                .build();
        try {
            repo.saveAndFlush(loby);
            log.info("saved loby successfully.......");
        } catch (Exception e) {
            log.info("saving loby failed......"+ e.getMessage());
        }

    }
}
