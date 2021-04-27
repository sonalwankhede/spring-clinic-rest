package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.Complaints;
import org.com.drSnehalAyuCareClinic.repository.ComplaintsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcComplaintsRepository implements ComplaintsRepository{

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings("unused")
	private SimpleJdbcInsert insertComplaints;

	@Autowired
	public JdbcComplaintsRepository(DataSource dataSource) {
		this.insertComplaints = new SimpleJdbcInsert(dataSource)
				.withTableName("complaints");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}

	@Override
	public Collection<Complaints> findAll() throws DataAccessException {
		List<Complaints> complaints = this.namedParameterJdbcTemplate.query(
				"SELECT complaints FROM complaints",
				new HashMap<String, Object>(),
				BeanPropertyRowMapper.newInstance(Complaints.class));
		return complaints;
	}

	@Override
	public void save(Complaints complaint) {

	}
}
