package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.drSnehalAyuCareClinic.model.Visit;
import org.springframework.data.jdbc.core.OneToManyResultSetExtractor;

public abstract class JdbcPatientVisitExtractor extends
OneToManyResultSetExtractor<JdbcPatient, Visit, Integer> {

	public JdbcPatientVisitExtractor() {
		super(new JdbcPatientRowMapper(), new JdbcVisitRowMapper());
	}

	@Override
	protected Integer mapPrimaryKey(ResultSet rs) throws SQLException {
		return rs.getInt("patients_id");
	}

	@Override
	protected Integer mapForeignKey(ResultSet rs) throws SQLException {
		if (rs.getObject("visits_patient_id") == null) {
			return null;
		} else {
			return rs.getInt("visits_patient_id");
		}
	}

	@Override
	protected void addChild(JdbcPatient root, Visit child) {
		root.addVisit(child);
	}

}
