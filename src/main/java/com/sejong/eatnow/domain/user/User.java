package com.sejong.eatnow.domain.user;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Table(name = "USER")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "NAME", length = 20)
    private String name;

    @Builder
    public User(String email, String name){
        this.email = email;
        this.name = name;
    }

    public void update(String email, String name){
        this.email = email;
        this.name = name;
    }
}
