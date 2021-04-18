package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.com.drSnehalAyuCareClinic.model.Drug;
import org.com.drSnehalAyuCareClinic.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcDrugRepositoryImpl implements DrugRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SimpleJdbcInsert insertDrug;

	@Autowired
	public JdbcDrugRepositoryImpl(DataSource dataSource) {

		this.insertDrug = new SimpleJdbcInsert(dataSource)
				.withTableName("Drugs")
				.usingGeneratedKeyColumns("id");

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}


	/**
	 * Loads {@link Drug Drugs} from the data store by last name, returning all Drugs whose last name <i>starts</i> with
	 * the given name; also loads the {@link Visit Visits} for the corresponding Drugs, if not
	 * already loaded.
	 */
	@Override
	public Collection<Drug> findByContent(String drugName) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("content", drugName + "%");
		List<Drug> Drugs = this.namedParameterJdbcTemplate.query(
				"SELECT id, brand_name, strength, form_of_drugs, content FROM Drugs WHERE content like :content",
				params,
				BeanPropertyRowMapper.newInstance(Drug.class)
				);
		return Drugs;
	}

	/**
	 * Loads the {@link Drug} with the supplied <code>id</code>; also loads the {@link Visit Visits}
	 * for the corresponding Drug, if not already loaded.
	 */
	@Override
	public Drug findById(int id) throws DataAccessException {
		Drug drug;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			drug = this.namedParameterJdbcTemplate.queryForObject(
					"SELECT drugs.id, drugs.brand_name, drugs.strength, drugs.form_of_drugs, "
							+ "drugs.content FROM Drugs drugs WHERE id= :id",
							params,
							BeanPropertyRowMapper.newInstance(Drug.class)
					);
		} catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Drug.class, id);
		}
		return drug;
	}

	@Override
	public void save(Drug drug) throws DataAccessException {
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(drug);
		if (drug.isNew()) {
			Number newKey = this.insertDrug.executeAndReturnKey(parameterSource);
			drug.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE Drugs SET brand_name=:brandName, content=:content, " +
							"form_of_drugs=:formOfDrugs, strength=:strength WHERE id=:id",
							parameterSource);
		}
	}

	@Override
	public List<Drug> findAll() throws DataAccessException {
		List<Drug> drugs = this.namedParameterJdbcTemplate.query(
				"SELECT * FROM Drugs",
				new HashMap<String, Object>(),
				BeanPropertyRowMapper.newInstance(Drug.class));
		return drugs;
	}

	@Override
	@Transactional
	public void delete(Drug drug) throws DataAccessException {
		Map<String, Object> drug_params = new HashMap<>();
		drug_params.put("id", drug);
		this.namedParameterJdbcTemplate.update("DELETE FROM Drugs WHERE id=:id", drug_params);
	}

}
