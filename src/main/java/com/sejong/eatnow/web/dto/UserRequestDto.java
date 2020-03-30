package com.sejong.eatnow.web.dto;

import com.sejong.eatnow.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String email;
    private String name;

    public User toEntity(){
        return User.builder()
                .email(this.email)
                .name(this.name)
                .build();
    }

}
