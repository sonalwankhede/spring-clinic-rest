package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.com.drSnehalAyuCareClinic.rest.JacksonCustomPrescriptionDeserializer;
import org.com.drSnehalAyuCareClinic.rest.JacksonCustomPrescriptionSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "prescriptions")
@JsonSerialize(using = JacksonCustomPrescriptionSerializer.class)
@JsonDeserialize(using = JacksonCustomPrescriptionDeserializer.class)
public class Prescription extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "visit_id", referencedColumnName="id")
	private Visit visit;
	
	@Column(name = "serial_number")
	private int serialNumber;
	
	@Column(name = "drug")
	private String drug;
	
	@Column(name = "duration")
	private String duration;
	
	@Column(name = "dose")
	private String dose;
	
	@Column(name = "instructions")
	private String instructions;
	
	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}	

}
