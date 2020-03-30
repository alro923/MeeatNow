package com.sejong.eatnow.domain.user;

import com.sejong.eatnow.domain.loby.Loby;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "USER")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "NAME", length = 20)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_LOBY",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "LOBY_ID"))
    private List<Loby> lobies = new ArrayList<>();

    public void update(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Builder
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
