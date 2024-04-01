package com.nebula.auth.service;

import com.nebula.auth.domain.LoginUser;

public interface UserService {

    LoginUser login(String username, String password);

}
