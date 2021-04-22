package org.com.drSnehalAyuCareClinic.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.Drug;
import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommonComponent {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public CommonComponent(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public void saveAllDrugs(List<Drug> drugs) {
		String sql = "INSERT INTO "
				+ "drugs "
				+ "(content,brand_name, strength, form_of_drugs) "
				+ "VALUES " + "(?,?,?,?)";

		this.namedParameterJdbcTemplate.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				Drug drug = drugs.get(i);
				ps.setString(1, drug.getContent());
				ps.setString(2, drug.getBrandName());
				ps.setString(3, drug.getStrength());
				ps.setString(4, drug.getFormOfDrugs());
			}
			@Override
			public int getBatchSize() {
				return drugs.size();
			}
		});	
	}

	@Transactional
	public void deleteAllPrescriptions(List<Prescription> prescriptions) {
		// cascade delete prescriptions
		for (Prescription prescription : prescriptions) {
			Map<String, Object> prescription_params = new HashMap<>();
			prescription_params.put("id", prescription.getId());
			this.namedParameterJdbcTemplate.update("DELETE FROM prescriptions WHERE id=:id", prescription_params);
		}		
	}

	@Transactional
	public void deleteDrugs(Collection<Integer> drugIds) {
		Map<String, Object> drug_params = new HashMap<>();
		for (int index: drugIds) {
			drug_params.put("id", index);
			this.namedParameterJdbcTemplate.update("DELETE FROM Drugs WHERE id=:id", drug_params);
		}
	}
	
	@Transactional
	public void updateDrugs(Collection<Integer> drugIds, String fieldName, String fieldValue) {
		Map<String, Object> drug_params = new HashMap<>();
		for (int index: drugIds) {
			drug_params.put("id", index);
			this.namedParameterJdbcTemplate.update("update Drugs set " + fieldName + " = '" + fieldValue + "' WHERE id=:id", drug_params);
		}
	}
}
