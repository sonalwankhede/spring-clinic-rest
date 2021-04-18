/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.com.drSnehalAyuCareClinic.model.Patient;
import org.com.drSnehalAyuCareClinic.model.Visit;
import org.com.drSnehalAyuCareClinic.repository.PatientRepository;
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

/**
 * A simple JDBC-based implementation of the {@link PatientRepository} interface.
 *
 * @author Sonal Wankhede
 */
@Repository
@Profile("jdbc")
public class JdbcPatientRepositoryImpl implements PatientRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SimpleJdbcInsert insertPatient;

	@Autowired
	public JdbcPatientRepositoryImpl(DataSource dataSource) {

		this.insertPatient = new SimpleJdbcInsert(dataSource)
				.withTableName("Patients")
				.usingGeneratedKeyColumns("id");

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}


	/**
	 * Loads {@link Patient Patients} from the data store by last name, returning all Patients whose last name <i>starts</i> with
	 * the given name; also loads the {@link Visit Visits} for the corresponding Patients, if not
	 * already loaded.
	 */
	@Override
	public Collection<Patient> findByLastName(String lastName) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("lastName", lastName + "%");
		List<Patient> Patients = this.namedParameterJdbcTemplate.query(
				"SELECT id, first_name, last_name, birth_date, gender, age, address, city, telephone, "
				+ "history, drug_allergies, other_allergies FROM Patients WHERE last_name like :lastName",
				params,
				BeanPropertyRowMapper.newInstance(Patient.class)
				);
		return Patients;
	}

	/**
	 * Loads the {@link Patient} with the supplied <code>id</code>; also loads the {@link Visit Visits}
	 * for the corresponding Patient, if not already loaded.
	 */
	@Override
	public Patient findById(int id) throws DataAccessException {
		Patient Patient;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			Patient = this.namedParameterJdbcTemplate.queryForObject(
					"SELECT patients.id, patients.first_name, patients.last_name, patients.birth_date, "
					+ "patients.age,  patients.address, patients.city, patients.telephone, "
					+ "patients.drug_allergies, patients.other_allergies "
					+ "FROM Patients patients WHERE patients.id= :id",
					params,
					BeanPropertyRowMapper.newInstance(Patient.class)
					);
		} catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Patient.class, id);
		}
		return Patient;
	}

	@Override
	public void save(Patient Patient) throws DataAccessException {
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(Patient);
		if (Patient.isNew()) {
			Number newKey = this.insertPatient.executeAndReturnKey(parameterSource);
			Patient.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE Patients SET first_name=:firstName, last_name=:lastName, address=:address, " +
							"middle_name=:middleName, age=:age, other_allergies=:otherAllergies, history=:history," +
							"city=:city, telephone=:telephone, drug_Allergies=:drugAllergies WHERE id=:id",
							parameterSource);
		}
	}

	@Override
	public Collection<Patient> findAll() throws DataAccessException {
		List<Patient> Patients = this.namedParameterJdbcTemplate.query(
				"SELECT id, first_name, last_name, birth_date, age, address, city, "
				+ "telephone, drug_allergies, other_allergies history FROM Patients",
				new HashMap<String, Object>(),
				BeanPropertyRowMapper.newInstance(Patient.class));
		return Patients;
	}

	@Override
	@Transactional
	public void delete(Patient patient) throws DataAccessException {
		Map<String, Object> patient_params = new HashMap<>();
		patient_params.put("id", patient.getId());
		List<Visit> visits = patient.getVisits();
		// cascade delete visits
		for (Visit visit : visits) {
			Map<String, Object> visit_params = new HashMap<>();
			visit_params.put("id", visit.getId());
			this.namedParameterJdbcTemplate.update("DELETE FROM visits WHERE id=:id", visit_params);
		}
		this.namedParameterJdbcTemplate.update("DELETE FROM Patients WHERE id=:id", patient_params);
	}


}
