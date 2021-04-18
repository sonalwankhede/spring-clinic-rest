package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.KnownCase;
import org.com.drSnehalAyuCareClinic.repository.KnownCasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcKnownCasesRepositoryImpl implements KnownCasesRepository{
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings("unused")
	private SimpleJdbcInsert insertKnownCase;

	@Autowired
	public JdbcKnownCasesRepositoryImpl(DataSource dataSource) {

		this.insertKnownCase = new SimpleJdbcInsert(dataSource)
				.withTableName("Issues");

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}

	@Override
	public Collection<KnownCase> findAll() throws DataAccessException {
		List<KnownCase> knownCases = this.namedParameterJdbcTemplate.query(
				"SELECT issues FROM Issues",
				new HashMap<String, Object>(),
				BeanPropertyRowMapper.newInstance(KnownCase.class));
		return knownCases;
	}

	@Override
	public void save(KnownCase knownCase) {
		// TODO Auto-generated method stub
		
	}

}
