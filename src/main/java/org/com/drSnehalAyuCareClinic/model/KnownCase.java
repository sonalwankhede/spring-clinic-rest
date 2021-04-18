package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "issues")
public class KnownCase{

	@Id
	@Column(name = "issues")
	@NotEmpty
	private String issues;

	public String getIssues() {
		return issues;
	}

	public void setIssues(String allergy) {
		this.issues = allergy;
	}

}
