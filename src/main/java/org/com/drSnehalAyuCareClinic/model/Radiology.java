package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Radiology")
public class Radiology {
	@Id
	@Column(name = "radiology")
	private String radiology;

	public String getRadiology() {
		return radiology;
	}

	public void setRadiology(String radiology) {
		this.radiology = radiology;
	}

	

}
