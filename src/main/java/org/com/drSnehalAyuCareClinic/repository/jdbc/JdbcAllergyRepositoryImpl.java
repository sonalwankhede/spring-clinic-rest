package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;
import org.com.drSnehalAyuCareClinic.model.DrugAllergy;
import org.com.drSnehalAyuCareClinic.repository.DrugAllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcAllergyRepositoryImpl implements DrugAllergyRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SuppressWarnings("unused")
	private SimpleJdbcInsert insertAllergy;

    @Autowired
    public JdbcAllergyRepositoryImpl(DataSource dataSource) {

        this.insertAllergy = new SimpleJdbcInsert(dataSource)
            .withTableName("DRUG_Allergies");
        
//            .usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

	@Override
	public Collection<DrugAllergy> findAll() throws DataAccessException {
		List<DrugAllergy> allergies = this.namedParameterJdbcTemplate.query(
	            "SELECT allergy FROM DRUG_Allergies",
	            new HashMap<String, Object>(),
	            BeanPropertyRowMapper.newInstance(DrugAllergy.class));
	    return allergies;
	}


}
