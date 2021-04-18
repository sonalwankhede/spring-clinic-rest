package org.com.drSnehalAyuCareClinic.repository;

import java.util.Collection;

import org.com.drSnehalAyuCareClinic.model.DrugAllergy;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;

@Profile("spring-data-jpa")
public interface DrugAllergyRepository extends Repository<DrugAllergy, Integer> {
	
	Collection<DrugAllergy> findAll() throws DataAccessException;

}
