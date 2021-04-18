package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Observations")
public class Observation {
	@Id
	@Column(name = "observations")
	private String observations;

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

}
