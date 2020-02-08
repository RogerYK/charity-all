package com.github.rogeryk.charity.server.core.exception;

import com.github.rogeryk.charity.server.core.util.ErrorCodes;

public class ServiceExceptions {
    public final static ServiceException PARAMS_ERROR = new ServiceException(ErrorCodes.PARAMS_ERROR, "params error");
    public final static ServiceException OPTIMISTIC_CONFLICT = new ServiceException(ErrorCodes.OPTIMISTIC_CONFLICT, "optimistic conflictict");
    public final static ServiceException USER_EXIST = new ServiceException(ErrorCodes.USER_EXIST, "user exist");
    public final static ServiceException USER_NOT_EXIST = new ServiceException(ErrorCodes.USER_NOT_EXIST, "user not exist");
    public final static ServiceException USER_NO_MONEY = new ServiceException(ErrorCodes.USER_NO_MONEY, "user not money");
    public final static ServiceException CATEGORY_NOT_EXIST = new ServiceException(ErrorCodes.CATEGORY_NOT_EXIST, "category not exist");
    public final static ServiceException PROJECT_NOT_EXIST = new ServiceException(ErrorCodes.PROJECT_NOT_EXIST, "project not exist");
    public final static ServiceException NEWS_NOT_EXIST = new ServiceException(ErrorCodes.NEWS_NOT_EXIST, "news not exist");
    public final static ServiceException COMMENT_NOT_EXIST = new ServiceException(ErrorCodes.COMMENT_NOT_EXIST, "comment not exist");
    public final static ServiceException UNKNOWN_ERROR = new ServiceException(ErrorCodes.UNKNOWN_ERROR, "unknown error");
}
