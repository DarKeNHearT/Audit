package com.varosa.audit.service;

import com.varosa.audit.model.User;
import com.varosa.audit.pojo.UserPojo;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserPojo insert(UserPojo userPojo) throws Exception;
    User checkit(Long id) throws Exception;
    List<Map<String,Object>> getAll();
    void delete(Long id) throws Exception;
    Map<String,Object> getById(Long id) throws Exception;
}
