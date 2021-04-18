/*
 * Copyright 2002-2017 the original author or authors.
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
package org.com.drSnehalAyuCareClinic.service;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.com.drSnehalAyuCareClinic.model.Diagnosis;
import org.com.drSnehalAyuCareClinic.model.Drug;
import org.com.drSnehalAyuCareClinic.model.DrugAllergy;
import org.com.drSnehalAyuCareClinic.model.KnownCase;
import org.com.drSnehalAyuCareClinic.model.Observation;
import org.com.drSnehalAyuCareClinic.model.OtherAllergy;
import org.com.drSnehalAyuCareClinic.model.Pathology;
import org.com.drSnehalAyuCareClinic.model.Patient;
import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Radiology;
import org.com.drSnehalAyuCareClinic.model.Visit;
import org.springframework.dao.DataAccessException;


/**
 * Mostly used as a facade so all controllers have a single point of entry
 *
 * @author Sonal Wankhede
 */
public interface ClinicService {
	
	Patient findPatientById(int id) throws DataAccessException;
	Collection<Patient> findAllPatients() throws DataAccessException;
	void savePatient(Patient patient) throws DataAccessException;
	void deletePatient(Patient patient) throws DataAccessException;
	Collection<Patient> findPatientByLastName(String lastName) throws DataAccessException;

	Collection<Drug> findAllDrugs() throws DataAccessException;
	Drug findDrugById(int drugId);
	void saveDrug(@Valid Drug drug);
	void deleteDrug(Drug drugId);
	void saveAllDrugs(Collection<Drug> drugs);
	
	Collection<DrugAllergy> findAllDrugAllergies();
	
	Collection<? extends Visit> findAllVisits();
	Visit findVisitById(int visitId);
	void saveVisit(@Valid Visit visit);
	void deleteVisit(Visit visit);
	
	Collection<Diagnosis> findAllDiagnosis();
	
	Collection<KnownCase> findAllKnownCases();
	
	void addNewlyAddedDiagnosis(@Valid  Diagnosis[] newlyAddedDiagnosis);
	Collection<OtherAllergy> findAllOtherAllergies();
	Collection<Observation> findAllObservations();
	Collection<Pathology> findPathology();
	Collection<Radiology> findRadiology();
	
	void savePrescription(Prescription currentPrescription);
	void evictAllDrugCacheValues();
	void evictAllPatientCacheValues();
	void evictAllVisitCacheValues();
	List<Visit> findVisitsByPatientId(int patientId);
	void deletePrescriptionByVisitId(List<Prescription> prescriptions);
}
