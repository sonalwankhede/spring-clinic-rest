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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.com.drSnehalAyuCareClinic.rest.JacksonCustomPatientDeserializer;
import org.com.drSnehalAyuCareClinic.rest.JacksonCustomPatientSerializer;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Simple JavaBean domain object representing an patient.
 *
 * @author Sonal Wankhede
 */
@Entity
@Table(name = "patients")
@JsonSerialize(using = JacksonCustomPatientSerializer.class)
@JsonDeserialize(using = JacksonCustomPatientDeserializer.class)
public class Patient extends Person {
	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "gender")
	@NotEmpty
	private String gender;

	@Column(name = "drug_allergies")
	private String drugAllergies;
	
	@Column(name = "other_allergies")
	private String otherAllergies;

	@Column(name = "history")
	private String history;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.EAGER)
	private Set<Visit> visits;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name= "age")
	private int age;

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getAddress() {
		return this.address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDrugAllergies() {
		return drugAllergies;
	}

	public void setDrugAllergies(String drugAllergies) {
		this.drugAllergies = drugAllergies;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getOtherAllergies() {
		return otherAllergies;
	}

	public void setOtherAllergies(String otherAllergies) {
		this.otherAllergies = otherAllergies;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId())
				.append("new", this.isNew())
				.append("lastName", this.getLastName())
				.append("firstName", this.getFirstName())
				.append("middleName", this.getMiddleName())
				.append("address", this.address)
				.append("city", this.city)
				.append("telephone", this.telephone)
				.append("age", this.age)
				.append("gender", this.gender)
				.append("history", this.history)
				.append("drugAllergies", this.drugAllergies)
				.append("otherAllergies", this.otherAllergies)
				.toString();
	}

	@JsonIgnore
	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}

	protected void setVisitsInternal(Set<Visit> visits) {
		this.visits = visits;
	}

	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}

	public void addVisit(Visit visit) {
		getVisitsInternal().add(visit);
		visit.setPatient(this);
	}

}
