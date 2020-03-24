package com.sejong.eatnow.web.dto;

import com.sejong.eatnow.domain.loby.Loby;
import com.sejong.eatnow.domain.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class LobyRequestDto {

    private String title;
    private String openLink;
    private String hostName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;
    private LocationDto locationDto; //locationDto로 수정

    public Loby toEntity() {
        Location location = new Location(locationDto.getLatitude(),
                locationDto.getLongitude());
        return Loby.builder()
                .title(title)
                .hostName(hostName)
                .openLink(openLink)
                .location(location)
                .meetingDate(meetingDate)
                .build();
    }
}