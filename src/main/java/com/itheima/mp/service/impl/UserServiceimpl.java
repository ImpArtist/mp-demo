package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceimpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    @Transactional
    public void deductMoneyById(Long id, Integer money){
        User user = getById(id);
        if(user == null || user.getStatus() == 2){
            throw new RuntimeException("异常！");
        }
        if(user.getBalance() < money){
            throw new RuntimeException("没钱！");
        }
        baseMapper.deductBalance(id,money);
        int remain = user.getBalance()-money;
        lambdaUpdate()
                .set(User::getBalance,remain)
                .set(remain==0,User::getStatus,2)
                .eq(User::getId,id)
                .eq(User::getBalance,user.getBalance())
                .update();
    }

    public List<User> queryUsers(String name, Integer status, Integer minBalance, Integer maxBalance){
        return lambdaQuery()
                .like(name!=null,User::getUsername,name)
                .eq(status!=null,User::getStatus,status)
                .ge(minBalance!=null,User::getBalance,minBalance)
                .le(maxBalance!=null,User::getBalance,maxBalance)
                .list();
    }
}
