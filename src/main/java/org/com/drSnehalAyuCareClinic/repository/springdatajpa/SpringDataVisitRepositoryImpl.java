package org.com.drSnehalAyuCareClinic.repository.springdatajpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.com.drSnehalAyuCareClinic.model.Visit;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;

@Profile("spring-data-jpa")
public class SpringDataVisitRepositoryImpl implements VisitRepositoryOverride {
	
	@PersistenceContext
    private EntityManager em;

	@Override
	public void delete(Visit visit) throws DataAccessException {
		String visitId = visit.getId().toString();
		this.em.createQuery("DELETE FROM Visit visit WHERE id=" + visitId).executeUpdate();
        if (em.contains(visit)) {
            em.remove(visit);
        }
	}
}
