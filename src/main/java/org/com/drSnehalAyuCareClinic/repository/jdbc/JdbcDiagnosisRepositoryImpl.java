package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.com.drSnehalAyuCareClinic.model.Diagnosis;
import org.com.drSnehalAyuCareClinic.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcDiagnosisRepositoryImpl implements DiagnosisRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SuppressWarnings("unused")
	private SimpleJdbcInsert insertDiagnosis;

    @Autowired
    public JdbcDiagnosisRepositoryImpl(DataSource dataSource) {

        this.insertDiagnosis = new SimpleJdbcInsert(dataSource)
            .withTableName("diagnosis")
            .usingColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

	@Override
	public Collection<Diagnosis> findAll() throws DataAccessException {
		List<Diagnosis> allergies = this.namedParameterJdbcTemplate.query(
	            "SELECT diagnosis FROM diagnosis",
	            new HashMap<String, Object>(),
	            BeanPropertyRowMapper.newInstance(Diagnosis.class));
	    return allergies;
	}

	@Override
	public void save(@Valid Diagnosis newlyAddedDiagnosis) {
		// TODO Auto-generated method stub
		
	}


}
