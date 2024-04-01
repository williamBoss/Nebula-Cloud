package com.nebula.auth.service;

import com.nebula.auth.domain.LoginUser;
import java.util.Map;

public interface TokenService {

    Map<String, Object> createToken(LoginUser userInfo);

}
