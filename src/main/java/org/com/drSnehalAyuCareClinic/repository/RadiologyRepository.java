package org.com.drSnehalAyuCareClinic.repository;

import java.util.Collection;
import org.com.drSnehalAyuCareClinic.model.Radiology;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

@Profile("spring-data-jpa")
public interface RadiologyRepository extends Repository<Radiology, Integer> {
	
	Collection<Radiology> findAll() throws DataAccessException;

	void save(Radiology radiology);

}
