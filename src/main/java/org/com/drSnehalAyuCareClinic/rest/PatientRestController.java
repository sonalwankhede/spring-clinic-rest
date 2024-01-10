/*
 * Copyright 2021-2022 the original author or authors.
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

package org.com.drSnehalAyuCareClinic.rest;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.com.drSnehalAyuCareClinic.model.Patient;
import org.com.drSnehalAyuCareClinic.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Sonal Wankhede
 *
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type, Access-Control-Allow-Origin")
@RequestMapping("/api/patients")
public class PatientRestController {

	@Autowired
	private ClinicService clinicService;

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/*/lastname/{lastName}", produces = "application/json")
	public ResponseEntity<Collection<Patient>> getPatientsList(@PathVariable("lastName") String patientLastName) {
		if (patientLastName == null) {
			patientLastName = "";
		}
		Collection<Patient> patients = this.clinicService.findPatientByLastName(patientLastName);
		if (patients.isEmpty()) {
			return new ResponseEntity<Collection<Patient>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Patient>>(patients, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "", produces = "application/json")
	public ResponseEntity<Collection<Patient>> getPatients() {
		Collection<Patient> patients = this.clinicService.findAllPatients();
		if (patients.isEmpty()) {
			return new ResponseEntity<Collection<Patient>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Patient>>(patients, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/{patientId}", produces = "application/json")
	public ResponseEntity<Patient> getPatient(@PathVariable("patientId") int patientId) {
		Patient patient = null;
		patient = this.clinicService.findPatientById(patientId);
		if (patient == null) {
			return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "", produces = "application/json")
	public ResponseEntity<Patient> addPatient(@RequestBody @Valid Patient patient, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || patient.getId() != null) {
			BindingErrorsResponse errors = new BindingErrorsResponse(patient.getId());
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Patient>(headers, HttpStatus.BAD_REQUEST);
		}
		this.clinicService.savePatient(patient);
		this.clinicService.evictAllPatientCacheValues();
		headers.setLocation(ucBuilder.path("/api/patients/{id}").buildAndExpand(patient.getId()).toUri());
		return new ResponseEntity<Patient>(patient, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PutMapping(value = "/{patientId}", produces = "application/json")
	public ResponseEntity<Patient> updatePatient(@PathVariable("patientId") int patientId, @RequestBody @Valid Patient patient,
			BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		boolean bodyIdMatchesPathId = patient.getId() == null || patientId == patient.getId();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || !bodyIdMatchesPathId) {
			BindingErrorsResponse errors = new BindingErrorsResponse(patientId, patient.getId());
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Patient>(headers, HttpStatus.BAD_REQUEST);
		}
		Patient currentPatient = this.clinicService.findPatientById(patientId);
		if (currentPatient == null) {
			return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
		}
		currentPatient.setAddress(patient.getAddress());
		currentPatient.setCity(patient.getCity());
		currentPatient.setFirstName(patient.getFirstName());
		currentPatient.setLastName(patient.getLastName());
		currentPatient.setTelephone(patient.getTelephone());
		currentPatient.setAge(patient.getAge());
		currentPatient.setGender(patient.getGender());
		currentPatient.setHistory(patient.getHistory());
		currentPatient.setMiddleName(patient.getMiddleName());
		currentPatient.setDrugAllergies(patient.getDrugAllergies());
		currentPatient.setOtherAllergies(patient.getOtherAllergies());
		this.clinicService.savePatient(currentPatient);
		this.clinicService.evictAllPatientCacheValues();
		return new ResponseEntity<Patient>(currentPatient, HttpStatus.NO_CONTENT);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@DeleteMapping(value = "/{patientId}", produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deletePatient(@PathVariable("patientId") int patientId) {
		Patient patient = this.clinicService.findPatientById(patientId);
		if (patient == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.clinicService.deletePatient(patient);
		this.clinicService.evictAllPatientCacheValues();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
