package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "diagnosis")
public class Diagnosis extends BaseEntity{
	@Column(name = "diagnosis")
	@NotEmpty
	private String diagnosis;

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
}
