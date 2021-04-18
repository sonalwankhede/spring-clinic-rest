package org.com.drSnehalAyuCareClinic.repository;

import java.util.Collection;

import org.com.drSnehalAyuCareClinic.model.OtherAllergy;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

@Profile("spring-data-jpa")
public interface OtherAllergyRepository extends Repository<OtherAllergy, Integer> {
	
	Collection<OtherAllergy> findAll() throws DataAccessException;

	void save(OtherAllergy otherAllergy);
}
