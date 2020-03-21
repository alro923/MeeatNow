package com.sejong.eatnow.domain.loby;

import com.sejong.eatnow.domain.location.Location;
import com.sejong.eatnow.domain.user.User;
import com.sejong.eatnow.web.dto.LobyRequestDto;
import com.sejong.eatnow.web.dto.LobyResponseDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "LOBY")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Loby {

    @Id
    @Column(name = "LOBY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false, length = 20)
    private String title;

    @Column(name = "LINK")
    private String openLink;

    @Column(name = "HOST", nullable = false)
    private String hostName;

    @Column(name = "MEETING_DATE", nullable = false)
    private LocalDate meetingDate;


    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "LOC_ID")
    private Location location;

    @ManyToMany(mappedBy = "lobies", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public void update(LobyRequestDto dto) {
        this.title = dto.getTitle();
        this.hostName = dto.getHostName();
        this.openLink = dto.getOpenLink();
        this.location = dto.getLocationDto().toEntity();
        this.meetingDate = dto.getMeetingDate();
    }

    public LobyResponseDto toResponseDto() {
        return LobyResponseDto.builder()
                .id(this.id)
                .title(this.title)
                .hostName(this.hostName)
                .openLink(this.openLink)
                .location(this.location)
                .meetingDate(this.meetingDate)
                .build();
    }

    @Builder
    public Loby(String title, String hostName, String openLink, Location location,
                LocalDate meetingDate) {
        this.hostName = hostName;
        this.location = location;
        this.title = title;
        this.openLink = openLink;
        this.meetingDate = meetingDate;
    }
}
