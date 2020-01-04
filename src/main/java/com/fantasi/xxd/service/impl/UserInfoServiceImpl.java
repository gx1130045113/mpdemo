package com.fantasi.xxd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fantasi.xxd.config.DBTypeEnum;
import com.fantasi.xxd.config.DataSource;
import com.fantasi.xxd.dao.UserInfoDao;
import com.fantasi.xxd.entity.UserInfo;
import com.fantasi.xxd.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author xxd
 * @date 2019/12/27 13:13
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {
    @Override
    @DataSource(DBTypeEnum.db1Source)
    public IPage<UserInfo> pageTest(Page page, QueryWrapper queryWrapper) {
        return baseMapper.selectPage(page,queryWrapper);
    }
}
