package org.com.drSnehalAyuCareClinic.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.com.drSnehalAyuCareClinic.model.Complaints;
import org.com.drSnehalAyuCareClinic.model.Diagnosis;
import org.com.drSnehalAyuCareClinic.model.Drug;
import org.com.drSnehalAyuCareClinic.model.DrugAllergy;
import org.com.drSnehalAyuCareClinic.model.KnownCase;
import org.com.drSnehalAyuCareClinic.model.Observation;
import org.com.drSnehalAyuCareClinic.model.OtherAllergy;
import org.com.drSnehalAyuCareClinic.model.Pathology;
import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Radiology;
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

	@Transactional
	public void deletePathScans(@Valid Pathology[] removedPathScans) {
		Map<String, Object> params = new HashMap<>();
		for (Pathology path: removedPathScans) {
			params.put("pathology", path.getPathology());
			this.namedParameterJdbcTemplate.update("DELETE FROM pathology where pathology=:pathology", params);
		}
	}
	
	@Transactional
	public void deleteRadioScans(@Valid Radiology[] removedScans) {
		Map<String, Object> params = new HashMap<>();
		for (Radiology path: removedScans) {
			params.put("radiology", path.getRadiology());
			this.namedParameterJdbcTemplate.update("DELETE FROM radiology where radiology=:radiology", params);
		}
	}

	public void deleteDrugAllergies(@Valid DrugAllergy[] removedDrugAllergies) {
		Map<String, Object> params = new HashMap<>();
		for (DrugAllergy allergy: removedDrugAllergies) {
			params.put("allergy", allergy.getAllergy());
			this.namedParameterJdbcTemplate.update("DELETE FROM DRUG_ALLERGIES where allergy=:allergy", params);
		}
	}

	public void deleteOtherAllergies(@Valid OtherAllergy[] removedOtherAllergies) {
		Map<String, Object> params = new HashMap<>();
		for (OtherAllergy allergy: removedOtherAllergies) {
			params.put("allergy", allergy.getAllergy());
			this.namedParameterJdbcTemplate.update("DELETE FROM OTHER_ALLERGIES where allergy=:allergy", params);
		}
	}

	public void deleteKnownCases(@Valid KnownCase[] removedKnownCases) {
		Map<String, Object> params = new HashMap<>();
		for (KnownCase issues: removedKnownCases) {
			params.put("issues", issues.getIssues());
			this.namedParameterJdbcTemplate.update("DELETE FROM ISSUES where issues=:issues", params);
		}
	}

	public void deleteComplaints(@Valid Complaints[] removedComplaintss) {
		Map<String, Object> params = new HashMap<>();
		for (Complaints complaints: removedComplaintss) {
			params.put("complaints", complaints.getComplaints());
			this.namedParameterJdbcTemplate.update("DELETE FROM complaints where complaints=:complaints", params);
		}
	}

	public void deleteObservations(@Valid Observation[] removedObservations) {
		Map<String, Object> params = new HashMap<>();
		for (Observation observation: removedObservations) {
			params.put("observations", observation.getObservations());
			this.namedParameterJdbcTemplate.update("DELETE FROM Observations where observations=:observations", params);
		}
	}

	public void deleteDiagnosis(@Valid Diagnosis[] removedDiagnosis) {
		Map<String, Object> params = new HashMap<>();
		for (Diagnosis diagnosis: removedDiagnosis) {
			params.put("diagnosis", diagnosis.getDiagnosis());
			this.namedParameterJdbcTemplate.update("DELETE FROM diagnosis where diagnosis=:diagnosis", params);
		}
	}
}
