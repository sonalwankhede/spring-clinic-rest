package org.com.drSnehalAyuCareClinic.repository.jdbc;

import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Visit;
import org.com.drSnehalAyuCareClinic.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple JDBC-based implementation of the {@link VisitRepository} interface.
 *
 * @author Sonal Wankhede
 */
@Repository
@Profile("jdbc")
public class JdbcVisitRepositoryImpl implements VisitRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected SimpleJdbcInsert insertVisit;

    @Autowired
    public JdbcVisitRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.insertVisit = new SimpleJdbcInsert(dataSource)
            .withTableName("visits")
            .usingGeneratedKeyColumns("id");
    }


    /**
     * Creates a {@link MapSqlParameterSource} based on data values from the supplied {@link Visit} instance.
     */
    protected MapSqlParameterSource createVisitParameterSource(Visit visit) {
        return new MapSqlParameterSource()
            .addValue("id", visit.getId())
            .addValue("visit_date", visit.getDate())
            .addValue("temperature", visit.getTemperature())
            .addValue("pulse", visit.getPulse())
            .addValue("spo2", visit.getSpo2())
            .addValue("respiration_rate", visit.getRespirationRate())
            .addValue("blood_pressure", visit.getBloodPressure())
            .addValue("height", visit.getHeight())
            .addValue("weight", visit.getWeight())
            .addValue("bmi", visit.getBmi())
            .addValue("diagnosis", visit.getDiagnosis())
            .addValue("complaints", visit.getComplaints())
            .addValue("observations", visit.getObservations())
        	.addValue("pathology",visit.getPathology())
        	.addValue("radiology", visit.getRadiology())
        	.addValue("next_follow_up", visit.getNextFollowUp());
            }

    @Override
    public List<Visit> findByPatientId(Integer patientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", patientId);
        JdbcPatient patient = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT id as patients_id, first_name, last_name, address, city, telephone FROM patients WHERE id=:id",
                params,
                new JdbcPatientRowMapper());

        List<Visit> visits = this.namedParameterJdbcTemplate.query(
            "SELECT id as visit_id, visit_date, temperature, pulse, spo2, respiration_rate, blood_pressure, height, weight, bmi, diagnosis, complaints, observations, "
            + " pathology, radiology, next_follow_up FROM visits WHERE patient_id=:id order by visit_date desc",
            params, new JdbcVisitRowMapper());

        for (Visit visit: visits) {
            visit.setPatient(patient);
        }

        return visits;
    }
    
	@Override
	public Visit findById(int id) throws DataAccessException {
		Visit visit;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			visit = this.namedParameterJdbcTemplate.queryForObject(
					"SELECT id as visit_id, visits.patient_id as patients_id, visit_date, "
					+ "temperature, pulse, spo2, respiration_rate, blood_pressure, height, "
					+ "weight, bmi, diagnosis, complaints, observations, "
					+ "pathology, radiology, next_follow_up FROM visits WHERE id= :id",
					params,
					new JdbcVisitRowMapperExt());
			List<Prescription> prescriptions = this.namedParameterJdbcTemplate.query(
		            "SELECT id , serial_number, drug, duration, dose, instructions, "
		            + "WHERE visit_id=:id ",
		            params, new JdbcPrescriptionRowMapper());
			for(Prescription prescription: prescriptions) {
				visit.addPrescription(prescription);
			}
			
		} catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Visit.class, id);
		}
		return visit;
	}

	@Override
	public Collection<Visit> findAll() throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		return this.namedParameterJdbcTemplate.query(
				"SELECT id as visit_id, patients.id as patients_id, visit_date, temperature, pulse, spo2, respiration_rate, blood_pressure, height, weight, bmi, diagnosis, observations, "
				+ "complaints, pathology, radiology, next_follow_up FROM visits LEFT JOIN patients ON visits.patient_id = patients.id",
				params, new JdbcVisitRowMapperExt());
	}

	@Override
	public void save(Visit visit) throws DataAccessException {
		if (visit.isNew()) {
			Number newKey = this.insertVisit.executeAndReturnKey(createVisitParameterSource(visit));
			visit.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE visits SET visit_date=:visit_date, temperature=: temperature, pulse=: pulse, spo2=: spo2, respiration_rate=: respiration_rate, "
					+ "blood_pressure=: blood_pressure, height=: height, weight=: weight, bmi=: bmi, "
					+ "diagnosis=:diagnosis, patient_id=:patientId,"
					+ "complaints=:complaints, observations=:observations, pathology=:pathology, "
					+ "radiology=:radiology, next_follow_up=:next_follow_up WHERE id=:id ",
					createVisitParameterSource(visit));
		}
	}

	@Override
	@Transactional
	public void delete(Visit visit) throws DataAccessException {
		Map<String, Object> visit_params = new HashMap<>();
		visit_params.put("id", visit.getId());
		List<Prescription> prescriptions = visit.getPrescriptions();
		// cascade delete prescriptions
		for (Prescription prescription : prescriptions) {
			Map<String, Object> prescription_params = new HashMap<>();
			prescription_params.put("id", prescription.getId());
			this.namedParameterJdbcTemplate.update("DELETE FROM prescriptions WHERE id=:id", prescription_params);
		}
		this.namedParameterJdbcTemplate.update("DELETE FROM visits WHERE id=:id", visit_params);
	}

	protected class JdbcVisitRowMapperExt implements RowMapper<Visit> {

		@Override
		public Visit mapRow(ResultSet rs, int rowNum) throws SQLException {
			Visit visit = new Visit();
			JdbcPatient patient = new JdbcPatient();
			
			visit.setId(rs.getInt("visit_id"));
			Date visitDate = rs.getDate("visit_date");
			visit.setDate(new Date(visitDate.getTime()));
			visit.setTemperature(rs.getInt("temperature"));
			visit.setPulse(rs.getInt("pulse"));
			visit.setSpo2(rs.getInt("spo2"));
			visit.setRespirationRate(rs.getString("respiration_rate"));
			visit.setBloodPressure(rs.getString("blood_pressure"));
			visit.setHeight(rs.getString("height"));
			visit.setWeight(rs.getString("weight"));
			visit.setBmi(rs.getString("bmi"));			
			visit.setDiagnosis(rs.getString("diagnosis"));
			visit.setComplaints(rs.getString("complaints"));
			visit.setObservations(rs.getString("observations"));
			visit.setNextFollowUp(rs.getString("next_follow_up"));
			Map<String, Object> params = new HashMap<>();
			params.put("id", rs.getInt("patients_id"));
			patient = JdbcVisitRepositoryImpl.this.namedParameterJdbcTemplate.queryForObject(
					"SELECT patients.id as patients_id, first_name, last_name, address, city, telephone, gender, age, history, other_allergies, drug_allergies FROM patients WHERE patients.id=:id",
					params,
					new JdbcPatientRowMapper());
			
			visit.setPatient(patient);
			return visit;
		}
	}

}
