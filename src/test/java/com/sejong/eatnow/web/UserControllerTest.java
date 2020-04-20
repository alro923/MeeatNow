package com.sejong.eatnow.web;

import com.sejong.eatnow.service.UserService;
import lombok.Builder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @Test
    public void cors_문제없이_유저등록() throws Exception{
        //given
        UserRequestDto userDto = UserRequestDto.builder()
                .email("y@an")
                .name("김와이")
                .build();

        //when,then
        mvc.perform(
                post("/user/insert")
                        .contentType("application/json;charset=utf-8;")
                        .content(toJson(userDto)))
                .andExpect(status().isOk());

    }

    public static class UserRequestDto{
        private String email;
        private String name;

        @Builder
        public UserRequestDto(String email, String name){
            this.email = email;
            this.name = name;
        }
    }
}
