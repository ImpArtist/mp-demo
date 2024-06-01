package com.itheima.mp.control;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags="用户管理接口")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class Usercontroller {

    private final IUserService userService;
    @PostMapping
    @ApiOperation("新增用户接口")
    public void saveUser(@RequestBody UserFormDTO userDTO){
        User user = BeanUtil.copyProperties(userDTO, User.class);
        userService.save(user);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("删除用户接口")
    public void deleteUser(@ApiParam("用户id") @PathVariable("id") Long id){
        userService.removeById(id);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("根据id查询用户接口")
    public UserVO getUser(@ApiParam("用户id") @PathVariable("id") Long id){

        User user = userService.getById(id);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @GetMapping
    @ApiOperation("根据ids查询用户接口")
    public List<UserVO> queryUserByIds(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids){

        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @DeleteMapping(value = "/{id}/deduction/{money}")
    @ApiOperation("扣减用户接口")
    public void deductMoneyById(@ApiParam("用户id") @PathVariable("id") Long id,
                                @ApiParam(value = "扣减金额") @PathVariable("money") Integer money){

        userService.deductMoneyById(id, money);
    }

    @GetMapping("/list")
    @ApiOperation("根据复杂条件查询用户接口")
    public List<UserVO> queryUsers(UserQuery query){

        List<User> users = userService.queryUsers(query.getName(),query.getStatus(),query.getMinBalance(),query.getMaxBalance());
        return BeanUtil.copyToList(users, UserVO.class);
    }
}

