package com.food.service;

import com.food.model.LogUser;

import java.util.List;

public interface UserService {

    List<LogUser> getLog();

    LogUser getOneLog(String id);

    LogUser createLog(LogUser dto);

    void deleteLog(String id);

}
