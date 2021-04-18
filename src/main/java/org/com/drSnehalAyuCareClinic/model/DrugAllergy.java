package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DRUG_ALLERGIES")
public class DrugAllergy {

	@Id
	@Column(name = "allergy")
	private String allergy;

	public String getAllergy() {
		return allergy;
	}

	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}
}
