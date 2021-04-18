package org.com.drSnehalAyuCareClinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.com.drSnehalAyuCareClinic.model.Visit;
import org.springframework.jdbc.core.RowMapper;

public class JdbcVisitRowMapper implements RowMapper<Visit> {

	@Override
	public Visit mapRow(ResultSet rs, int rowNum) throws SQLException {
		Visit visit = new Visit();
        visit.setId(rs.getInt("id"));
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
        visit.setPathology(rs.getString("pathology"));
        visit.setRadiology(rs.getString("radiology"));

        return visit;
	}

}
