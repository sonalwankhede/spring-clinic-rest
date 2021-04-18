package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.springframework.jdbc.core.RowMapper;

public class JdbcPrescriptionRowMapper implements RowMapper<Prescription> {

	@Override
	public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
		Prescription prescription = new Prescription();
        prescription.setId(rs.getInt("id"));
        prescription.setDrug(rs.getString("drug"));
        prescription.setDuration(rs.getString("duration"));
        prescription.setInstructions(rs.getString("instructions"));
        prescription.setDose(rs.getString("dose"));

        return prescription;
	}
}
