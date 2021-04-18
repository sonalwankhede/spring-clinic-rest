package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.OtherAllergy;
import org.com.drSnehalAyuCareClinic.repository.OtherAllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcOtherAllergyRepository implements OtherAllergyRepository{

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings("unused")
	private SimpleJdbcInsert insertAllergy;

	@Autowired
	public JdbcOtherAllergyRepository(DataSource dataSource) {

		this.insertAllergy = new SimpleJdbcInsert(dataSource)
				.withTableName("OTHER_Allergies");
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Collection<OtherAllergy> findAll() throws DataAccessException {
		List<OtherAllergy> allergies = this.namedParameterJdbcTemplate.query(
				"SELECT allergy FROM OTHER_Allergies",
				new HashMap<String, Object>(),
				BeanPropertyRowMapper.newInstance(OtherAllergy.class));
		return allergies;
	}

	@Override
	public void save(OtherAllergy otherAllergy) {
		// TODO Auto-generated method stub
		
	}

}
