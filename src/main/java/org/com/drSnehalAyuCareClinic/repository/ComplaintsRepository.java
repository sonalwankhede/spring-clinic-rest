package org.com.drSnehalAyuCareClinic.repository;

import java.util.Collection;

import org.com.drSnehalAyuCareClinic.model.Complaints;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

@Profile("spring-data-jpa")
public interface ComplaintsRepository extends Repository<Complaints, Integer> {
	
	Collection<Complaints> findAll() throws DataAccessException;

	void save(Complaints complaints);
}
