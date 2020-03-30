package com.sejong.eatnow;

import com.sejong.eatnow.domain.user.User;
import com.sejong.eatnow.domain.user.UserRepository;
import com.sejong.eatnow.web.dto.UserRequestDto;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log
@Commit
public class UserTest {
    @Autowired
    private UserRepository repo;

    @Test
    public void isQuery_findByEmail() {
        UserRequestDto test = new UserRequestDto("k@n", null);


            User target = repo.findByEmail(test.getEmail())
                    .orElseThrow(()-> new NullPointerException("이메일로 찾을 수 없습니다."));
            log.info("target:"+target.getEmail()+target.getName());
    }

    @Test
    public void isQuery_findAllDesc() {
        try {
            List<User> target = repo.findAllDesc();
            log.info("target"+target.toString());
            for(User tmp :target){
                log.info("target:"+ tmp.getId()+","+tmp.getName()+","+tmp.getEmail());
            }
        } catch (Exception e){      //-> data.sql에 user 아무것도 안 집어넣고 실행
            log.info("exception: "+e.getMessage());
        }
    }

    @Test
    public void isError_insert(){
        UserRequestDto dto = new UserRequestDto("ss4@d","lee");
        try {
            repo.saveAndFlush(dto.toEntity());
        } catch (Exception e)  //email 값이 유일성을 가지므로 중복되면 예외처리.
        {
            log.warning("Exception:"+e.getMessage());
        }
    }
    @Test
    public void isError_saveUniqueEmails(){
        User user = User.builder().email("k@n").build();

        try{
            repo.save(user);
        }
        catch (DataIntegrityViolationException e)
        {
            log.info("Exception : "+e.getMessage());
        }
    }

    @Test
    public void isError_update(){
        Long id = Integer.toUnsignedLong(1);
        UserRequestDto dto = new UserRequestDto("jj@g","ja");

        User target = repo.findById(id).orElseThrow(
                () -> new NullPointerException("찾는 유저가 없습니다."));
        try {
            target.update(dto.getEmail(), dto.getName());
            repo.saveAndFlush(target);
        } catch (DataIntegrityViolationException e)  //email 값이 유일성을 가지므로 중복되면 예외처리.
        {
            e.printStackTrace();
        }

    }
    @Test
    public void isError_deleteById(){
        Long testId = Integer.toUnsignedLong(1000);

        try{
            repo.deleteById(testId);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
        }
    }
}

