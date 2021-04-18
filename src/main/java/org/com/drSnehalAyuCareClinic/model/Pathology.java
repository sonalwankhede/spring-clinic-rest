package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Pathology")
public class Pathology {

	@Id
	@Column(name = "pathology")
	private String pathology;

	public String getPathology() {
		return pathology;
	}

	public void setPathology(String pathology) {
		this.pathology = pathology;
	}

	
}
