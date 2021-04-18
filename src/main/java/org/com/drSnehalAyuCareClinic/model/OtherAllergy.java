package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OTHER_ALLERGIES")
public class OtherAllergy {

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
