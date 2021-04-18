package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.Observation;
import org.com.drSnehalAyuCareClinic.repository.ObservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcObservations implements ObservationsRepository{
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@SuppressWarnings("unused")
	private SimpleJdbcInsert insertAllergy;

	@Autowired
	public JdbcObservations(DataSource dataSource) {

		this.insertAllergy = new SimpleJdbcInsert(dataSource)
				.withTableName("observations");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Collection<Observation> findAll() throws DataAccessException {
		List<Observation> observations = this.namedParameterJdbcTemplate.query(
				"SELECT observations FROM observations",
				new HashMap<String, Object>(),
				BeanPropertyRowMapper.newInstance(Observation.class));
		return observations;
	}
}
