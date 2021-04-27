package org.com.drSnehalAyuCareClinic.service;

import org.com.drSnehalAyuCareClinic.model.UserModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    void saveUser(UserModel user) throws Exception;

	UserModel loadUserByUsername(String username) throws UsernameNotFoundException;
}
