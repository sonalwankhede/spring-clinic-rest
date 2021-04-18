package org.com.drSnehalAyuCareClinic.repository;

import org.com.drSnehalAyuCareClinic.model.User;
import org.springframework.dao.DataAccessException;

public interface UserRepository {

    void save(User user) throws DataAccessException;
}
