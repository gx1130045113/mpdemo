package com.fantasi.xxd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fantasi.xxd.common.Result;
import com.fantasi.xxd.entity.UserInfo;
import com.fantasi.xxd.service.UserInfoService;
import com.fantasi.xxd.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxd
 * @date 2019/12/27 13:14
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("page")
    private IPage<UserInfo> page(Page page,UserInfo userInfo){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(userInfo);
        return userInfoService.pageTest(page,queryWrapper);
    }

    @PostMapping("redis/test")
    private Result<Map<Object,Object>> testRedis(Page page){
        IPage<UserInfo> iPage = userInfoService.page(page);
        Map map = new HashMap();
        map.put("size",iPage.getSize());
        map.put("current",iPage.getCurrent());
        map.put("pages",iPage.getPages());
        map.put("total",iPage.getTotal());
        map.put("data",iPage.getRecords());
        Boolean result = redisUtil.hmset("userInfo",map,60*2);
        if (result){
            return new Result<>(redisUtil.hmget("userInfo"));
        }
        return new Result<>(null);
    }
}
