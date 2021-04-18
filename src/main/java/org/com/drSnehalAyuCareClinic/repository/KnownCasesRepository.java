package org.com.drSnehalAyuCareClinic.repository;

import java.util.Collection;

import org.com.drSnehalAyuCareClinic.model.KnownCase;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

@Profile("spring-data-jpa")
public interface KnownCasesRepository extends Repository<KnownCase, Integer> {
	
	Collection<KnownCase> findAll() throws DataAccessException;

	void save(KnownCase knownCase);
}
