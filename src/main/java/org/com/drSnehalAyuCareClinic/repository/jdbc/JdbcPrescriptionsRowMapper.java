package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.springframework.jdbc.core.RowMapper;

public class JdbcPrescriptionsRowMapper  implements RowMapper<Prescription> {

	@Override
	public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
		Prescription prescription = new Prescription();
		prescription.setId(rs.getInt("id"));
		prescription.setSerialNumber(rs.getInt("serial_number"));
		prescription.setDrug(rs.getString("drug"));
		prescription.setDuration(rs.getString("duration"));
		prescription.setDose(rs.getString("dose"));
		prescription.setInstructions(rs.getString("instructions"));

	
		return null;
	}

}
