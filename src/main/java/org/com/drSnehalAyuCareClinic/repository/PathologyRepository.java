package org.com.drSnehalAyuCareClinic.repository;

import java.util.Collection;

import org.com.drSnehalAyuCareClinic.model.Pathology;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

@Profile("spring-data-jpa")
public interface PathologyRepository extends Repository<Pathology, Integer> {
	
	Collection<Pathology> findAll() throws DataAccessException;
}
