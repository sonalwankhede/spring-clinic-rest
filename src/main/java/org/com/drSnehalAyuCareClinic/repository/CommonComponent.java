package org.com.drSnehalAyuCareClinic.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.com.drSnehalAyuCareClinic.model.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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
}
