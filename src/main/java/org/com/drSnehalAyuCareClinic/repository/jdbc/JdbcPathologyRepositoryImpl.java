package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.Pathology;
import org.com.drSnehalAyuCareClinic.repository.PathologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcPathologyRepositoryImpl implements PathologyRepository {	

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings("unused")
	private SimpleJdbcInsert insertAllergy;

	@Autowired
	public JdbcPathologyRepositoryImpl(DataSource dataSource) {

		this.insertAllergy = new SimpleJdbcInsert(dataSource)
				.withTableName("pathology");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Collection<Pathology> findAll() throws DataAccessException {
		List<Pathology> pathologies = this.namedParameterJdbcTemplate.query(
				"SELECT pathology FROM pathology ",
				new HashMap<String, Object>(),
				BeanPropertyRowMapper.newInstance(Pathology.class));
		return pathologies;
	}

	@Override
	public void save(Pathology pathology) {
		// TODO Auto-generated method stub
		
	}
}
