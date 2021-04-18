package org.com.drSnehalAyuCareClinic.repository;

import java.util.Collection;

import org.com.drSnehalAyuCareClinic.model.Observation;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

@Profile("spring-data-jpa")
public interface ObservationsRepository extends Repository<Observation, Integer> {
	
	Collection<Observation> findAll() throws DataAccessException;

	void save(Observation observation);


}
