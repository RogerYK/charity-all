package com.github.rogeryk.charity.utils;

public interface ErrorCodes {
    int PARAMS_ERROR = 100; //参数错误

    int OPTIMISTIC_CONFLICT = 102; //乐观锁冲突

    int FILE_ERROR = 300; //文件保存失败

    int USER_EXIST = 350; // 用户已存在
    int USER_NOT_EXIST = 351; //用户不存在
    int USER_NO_MONEY = 352; //用户余额不足

    int CATEGORY_NOT_EXIST = 400; //分类不存在
    int PROJECT_NOT_EXIST = 401; //项目不存在
    int NEWS_NOT_EXIST = 402;


}
