package com.sejong.eatnow.web.dto;

import com.sejong.eatnow.domain.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private Long latitude;
    private Long longitude;

    public Location toEntity(){
        return Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}