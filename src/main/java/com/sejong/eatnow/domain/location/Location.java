package com.sejong.eatnow.domain.location;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sejong.eatnow.domain.loby.Loby;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "LOC")
@Getter
@Entity
public class Location {

    @Id
    @Column(name = "LOC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LATIT", nullable = false)
    private Long latitude;

    @Column(name = "LONGIT", nullable = false)
    private Long longitude;

    @JsonBackReference
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Loby> lobies = new ArrayList<>();

    @Builder
    public Location(Long latitude, Long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
