package com.fantasi.xxd.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fantasi.xxd.config.DBTypeEnum;
import com.fantasi.xxd.config.DataSource;
import com.fantasi.xxd.entity.UserInfo;

/**
 * @author xxd
 * @date 2019/12/27 13:12
 */
public interface UserInfoService extends IService<UserInfo> {

    IPage<UserInfo> pageTest(Page page, QueryWrapper queryWrapper);
}
