/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.com.drSnehalAyuCareClinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotEmpty;

import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.com.drSnehalAyuCareClinic.rest.JacksonCustomVisitDeserializer;
import org.com.drSnehalAyuCareClinic.rest.JacksonCustomVisitSerializer;

/**
 * Simple JavaBean domain object representing a visit.
 *
 * @author Sonal Wankhede
 */
@Entity
@Table(name = "visits")
@JsonSerialize(using = JacksonCustomVisitSerializer.class)
@JsonDeserialize(using = JacksonCustomVisitDeserializer.class)
public class Visit extends BaseEntity {

	@Column(name = "visit_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Date date;

	@NotEmpty
	@Column(name = "diagnosis")
	private String diagnosis;

	@NotEmpty
	@Column(name = "complaints")
	private String complaints;

	@NotEmpty
	@Column(name = "observations")
	private String observations;

	@Column(name = "pathology")
	private String pathology;

	@Column(name = "radiology")
	private String radiology;
	
	@Column(name = "temperature")
	private Integer temperature;
	
	@Column(name = "pulse")
	private Integer pulse;
	
	@Column(name = "spo2")
	private Integer spo2;
	
	@Column(name = "respiration_rate")
	private String respirationRate;
	
	@Column(name = "blood_pressure")
	private String bloodPressure;
	
	@Column(name = "height")
	private String height;
	
	@Column(name = "weight")
	private String weight;
	
	@Column(name = "bmi")
	private String bmi;
	
	@Column(name = "next_follow_up")
	private String nextFollowUp;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "visit", fetch = FetchType.EAGER)
	private Set<Prescription> prescription;


	public String getPathology() {
		return pathology;
	}


	public void setPathology(String pathology) {
		this.pathology = pathology;
	}


	public String getRadiology() {
		return radiology;
	}


	public void setRadiology(String radiology) {
		this.radiology = radiology;
	}

	public String getComplaints() {
		return complaints;
	}


	public void setComplaints(String complaints) {
		this.complaints = complaints;
	}


	public String getObservations() {
		return observations;
	}


	public void setObservations(String observations) {
		this.observations = observations;
	}


	public String getDiagnosis() {
		return diagnosis;
	}


	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	
	public int getTemperature() {
		return temperature;
	}

	public int getPulse() {
		return pulse;
	}


	public void setPulse(int pulse) {
		this.pulse = pulse;
	}


	public int getSpo2() {
		return spo2;
	}


	public void setSpo2(int spo2) {
		this.spo2 = spo2;
	}
	
	public String getRespirationRate() {
		return respirationRate;
	}


	public void setRespirationRate(String respirationRate) {
		this.respirationRate = respirationRate;
	}


	public String getBloodPressure() {
		return bloodPressure;
	}


	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}


	public String getHeight() {
		return height;
	}


	public void setHeight(String height) {
		this.height = height;
	}


	public String getWeight() {
		return weight;
	}


	public void setWeight(String weight) {
		this.weight = weight;
	}


	public String getBmi() {
		return bmi;
	}


	public void setBmi(String bmi) {
		this.bmi = bmi;
	}


	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}


	public void setPulse(Integer pulse) {
		this.pulse = pulse;
	}


	public void setSpo2(Integer spo2) {
		this.spo2 = spo2;
	}


	public Visit() {
		this.date = new Date();
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNextFollowUp() {
		return nextFollowUp;
	}

	public void setNextFollowUp(String nextFollowUp) {
		this.nextFollowUp = nextFollowUp;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setPrescriptions(Set<Prescription> prescription) {
		this.prescription = prescription;
	}
	
	@JsonIgnore
	protected Set<Prescription> getPrescriptionsInternal() {
		if (this.prescription == null) {
			this.prescription = new HashSet<>();
		}
		return this.prescription;
	}

	protected void setPrescriptionsInternal(Set<Prescription> prescription) {
		this.prescription = prescription;
	}

	public List<Prescription> getPrescriptions() {
		List<Prescription> sortedPrescriptions = new ArrayList<>(getPrescriptionsInternal());
		return Collections.unmodifiableList(sortedPrescriptions);
	}

	public void addPrescription(Prescription prescription) {
		getPrescriptionsInternal().add(prescription);
		prescription.setVisit(this);
	}	

	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId())
				.append("new", this.isNew())
				.append("visitDate", this.date)
				.append("temperature", this.temperature)
				.append("pulse", this.pulse)
				.append("spo2", this.spo2)
				.append("respiration_rate", this.respirationRate)
				.append("blood_pressure", this.bloodPressure)
				.append("height", this.height)
				.append("weight", this.weight)
				.append("bmi", this.bmi)
				.append("diagnosis", this.diagnosis)
				.append("observations", this.observations)
				.append("complaints", this.complaints)
				.append("radiology", this.radiology)
				.append("pathology", this.pathology)
				.toString();
	}
}
