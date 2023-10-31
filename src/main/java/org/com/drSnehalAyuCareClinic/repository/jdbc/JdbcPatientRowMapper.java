package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class JdbcPatientRowMapper implements RowMapper<JdbcPatient> {

	@Override
	public JdbcPatient mapRow(ResultSet rs, int rowNum) throws SQLException {
		JdbcPatient patient = new JdbcPatient();
        patient.setId(rs.getInt("id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setMiddleName(rs.getString("middle_name"));
        patient.setGender(rs.getString("gender"));
        patient.setAge(rs.getString("age"));
        patient.setCity(rs.getString("city"));
        patient.setAddress(rs.getString("address"));
        patient.setTelephone(rs.getString("telephone"));
        patient.setHistory(rs.getString("history"));
        patient.setDrugAllergies(rs.getString("drug_allergies"));
        patient.setOtherAllergies(rs.getString("other_allergies"));
        return patient;
	}

}
