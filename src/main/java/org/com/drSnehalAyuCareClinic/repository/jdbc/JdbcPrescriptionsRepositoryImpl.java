package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Visit;
import org.com.drSnehalAyuCareClinic.repository.PrescriptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcPrescriptionsRepositoryImpl implements PrescriptionsRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	protected SimpleJdbcInsert insertPrescriptions;

	@Autowired
	public JdbcPrescriptionsRepositoryImpl(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		this.insertPrescriptions = new SimpleJdbcInsert(dataSource)
				.withTableName("prescriptions")
				.usingGeneratedKeyColumns("id");
	}

	protected MapSqlParameterSource createPrescriptionsParameterSource(Prescription prescription) {
		return new MapSqlParameterSource()
				.addValue("id", prescription.getId())
				.addValue("serial_number", prescription.getSerialNumber())
				.addValue("visit_id", prescription.getVisit().getId())
				.addValue("drug", prescription.getDrug())
				.addValue("duration", prescription.getDuration())
				.addValue("dose", prescription.getDose())
				.addValue("instructions",prescription.getInstructions());
	}

	@Override
	public void save(Prescription prescription) throws DataAccessException {
		if (prescription.isNew()) {
			Number newKey = this.insertPrescriptions.executeAndReturnKey(createPrescriptionsParameterSource(prescription));
			prescription.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE prescriptions SET prescriptions_id=:prescriptionsDate, serial_number=:serialNumber, drug=:drug, duration=:duration,"
					+ "dose=:dose, instructions=:instructions WHERE id=:id ",
					createPrescriptionsParameterSource(prescription));
		}
	}

//	@Override
//	public List<Prescription> findByPrescriptionId(Integer visitId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Prescription findById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Prescription> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Prescription prescription) throws DataAccessException {
		Map<String, Object> prescription_params = new HashMap<>();
		prescription_params.put("id", prescription.getId());
		this.namedParameterJdbcTemplate.update("DELETE FROM prescriptions WHERE id=:id", prescription_params);
	}

	protected class JdbcPrescriptionsRowMapperExt implements RowMapper<Prescription> {

		@Override
		public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
			Prescription prescription = new Prescription();
			Visit visit = new Visit();
			
			prescription.setId(rs.getInt("id"));
			prescription.setSerialNumber(rs.getInt("seial_number"));
			prescription.setDrug(rs.getString("drug"));
			prescription.setDuration(rs.getString("duration"));
			prescription.setDose(rs.getString("dose"));
			Map<String, Object> params = new HashMap<>();
			params.put("id", rs.getInt("visits_id"));
			visit = JdbcPrescriptionsRepositoryImpl.this.namedParameterJdbcTemplate.queryForObject(
					"SELECT prescriptions.id as prescriptions_id, prescriptions.serial_number, prescriptions.drug, prescriptions.dose ,prescriptions.duration, prescriptions.instructions"
					+ " visits.id as  FROM visits WHERE visits.id=:id",
					params,
					new JdbcVisitRowMapper());
			
			prescription.setVisit(visit);
			return prescription;
		}
	}
}
