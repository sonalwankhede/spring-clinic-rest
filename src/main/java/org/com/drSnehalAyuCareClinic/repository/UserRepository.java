package org.com.drSnehalAyuCareClinic.repository;

import org.com.drSnehalAyuCareClinic.model.UserModel;
import org.springframework.dao.DataAccessException;

public interface UserRepository {

    void save(UserModel user) throws DataAccessException;

    UserModel getByUsername(String username);

}
