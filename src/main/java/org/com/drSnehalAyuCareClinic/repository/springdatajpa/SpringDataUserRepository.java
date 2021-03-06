package org.com.drSnehalAyuCareClinic.repository.springdatajpa;

import org.com.drSnehalAyuCareClinic.model.User;
import org.com.drSnehalAyuCareClinic.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.Repository;

@Profile("spring-data-jpa")
public interface SpringDataUserRepository extends UserRepository, Repository<User, Integer>  {

}
