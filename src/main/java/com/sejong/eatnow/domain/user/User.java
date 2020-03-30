package com.sejong.eatnow.domain.user;

import com.sejong.eatnow.domain.chat.Chat;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@ToString
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

    @ManyToMany
    @JoinTable(name = "USER_CHAT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHAT_ID"))
    private List<Chat> chats;

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
