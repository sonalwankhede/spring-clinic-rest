package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Visit;
import org.springframework.data.jdbc.core.OneToManyResultSetExtractor;

public class JdbcVisitPrescriptionExtractor extends
OneToManyResultSetExtractor<Visit, Prescription, Integer> {

	public JdbcVisitPrescriptionExtractor() {
		super(new JdbcVisitRowMapper(), new JdbcPrescriptionRowMapper());
	}

	@Override
	protected Integer mapPrimaryKey(ResultSet rs) throws SQLException {
		return rs.getInt("visits_id");
	}

	@Override
	protected Integer mapForeignKey(ResultSet rs) throws SQLException {
		if (rs.getObject("prescriptions_visit_id") == null) {
			return null;
		} else {
			return rs.getInt("prescriptions_visit_id");
		}
	}

	@Override
	protected void addChild(Visit root, Prescription child) {
		// TODO Auto-generated method stub
		root.addPrescription(child);
}

}
