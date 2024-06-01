package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
//        user.setId(6L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

//    @Test
//    void testDeleteUser() {
//        userMapper.deleteUser(5L);
//    }

    @Test
    void testQueryWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id","username","info", "balance")
                        .like("username","o")
                                .ge("balance", 1000);
        userMapper.selectList(wrapper).forEach(System.out::println);  // 查询
    }

    @Test
    void testUpdateByQuerWrapper() {
        User user = new User();
        user.setBalance(10000);
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username", "Lucy");
        userMapper.update(user, wrapper);
    }

    @Test
    void testUpdateWrapper(){
        List<Long> ids = List.of(1L,2L,4L);
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id" , ids);
        userMapper.update(null,wrapper);

    }

    @Test
    void testLambdaQueryWrapper() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId,User::getUsername,User::getInfo, User::getBalance)
                .like(User::getUsername,"o")
                .ge(User::getBalance, 1000);
        userMapper.selectList(wrapper).forEach(System.out::println);  // 查询
    }

    @Test
    void testCustomSqlUpdate() {
        List<Long> ids = List.of(1L,2L,4L);
        int amount = 200;
        QueryWrapper<User> wrapper = new QueryWrapper<User>().in("id", ids);
        userMapper.updateBalanceByIds(wrapper,amount);
    }
}