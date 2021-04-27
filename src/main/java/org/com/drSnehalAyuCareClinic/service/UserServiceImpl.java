package org.com.drSnehalAyuCareClinic.service;

import org.com.drSnehalAyuCareClinic.model.Role;
import org.com.drSnehalAyuCareClinic.model.UserModel;
import org.com.drSnehalAyuCareClinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void saveUser(UserModel user) throws Exception {

        if(user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new Exception("User must have at least a role set!");
        }

        for (Role role : user.getRoles()) {
            if(!role.getName().startsWith("ROLE_")) {
                role.setName("ROLE_" + role.getName());
            }

            if(role.getUser() == null) {
                role.setUser(user);
            }
        }

        userRepository.save(user);
    }
    @Override
    public UserModel loadUserByUsername(final String username) throws UsernameNotFoundException {
       UserModel userEntity = null;
       try {
          userEntity = userRepository.getByUsername(username);
          return userEntity;
       } catch (Exception e) {
          e.printStackTrace();
          throw new UsernameNotFoundException("User " + username + " was not found in the database");
       }
    }
}
