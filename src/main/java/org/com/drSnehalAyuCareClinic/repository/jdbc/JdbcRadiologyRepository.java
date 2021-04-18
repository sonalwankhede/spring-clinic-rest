package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.Radiology;
import org.com.drSnehalAyuCareClinic.repository.RadiologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcRadiologyRepository implements RadiologyRepository{
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings("unused")
	private SimpleJdbcInsert insertAllergy;

	@Autowired
	public JdbcRadiologyRepository(DataSource dataSource) {

		this.insertAllergy = new SimpleJdbcInsert(dataSource)
				.withTableName("radiology");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Collection<Radiology> findAll() throws DataAccessException {
		List<Radiology> radiologies = this.namedParameterJdbcTemplate.query(
				"SELECT radiology FROM radiology ",
				new HashMap<String, Object>(),
				BeanPropertyRowMapper.newInstance(Radiology.class));
		return radiologies;
	}

}
