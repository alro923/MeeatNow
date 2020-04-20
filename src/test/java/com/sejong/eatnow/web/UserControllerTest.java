package com.sejong.eatnow.web;

import com.sejong.eatnow.service.UserService;
import com.sejong.eatnow.web.dto.UserRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @Test
    public void cors_문제없이_유저등록() throws Exception {
        //given
        UserRequestDto userDto = new UserRequestDto("y@an", "김와이");

        //when,then
        mvc.perform(
                post("/user/insert")
                        .contentType("application/json;charset=utf-8;")
                        .content("{\n" +
                                " \"email\":\"m@lov\",\n" +
                                " \"name\":\"김마라탕사랑해요\"\n" +
                                "}"))
                .andExpect(status().isOk());

    }
}
