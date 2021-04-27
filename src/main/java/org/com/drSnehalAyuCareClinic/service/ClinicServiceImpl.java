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

import org.com.drSnehalAyuCareClinic.model.Complaints;
import org.com.drSnehalAyuCareClinic.model.Diagnosis;
import org.com.drSnehalAyuCareClinic.model.Drug;
import org.com.drSnehalAyuCareClinic.model.DrugAllergy;
import org.com.drSnehalAyuCareClinic.model.DrugUpdateRequest;
import org.com.drSnehalAyuCareClinic.model.KnownCase;
import org.com.drSnehalAyuCareClinic.model.Observation;
import org.com.drSnehalAyuCareClinic.model.OtherAllergy;
import org.com.drSnehalAyuCareClinic.model.Pathology;
import org.com.drSnehalAyuCareClinic.model.Patient;
import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Radiology;
import org.com.drSnehalAyuCareClinic.model.Visit;
import org.com.drSnehalAyuCareClinic.repository.CommonComponent;
import org.com.drSnehalAyuCareClinic.repository.ComplaintsRepository;
import org.com.drSnehalAyuCareClinic.repository.DiagnosisRepository;
import org.com.drSnehalAyuCareClinic.repository.DrugAllergyRepository;
import org.com.drSnehalAyuCareClinic.repository.DrugRepository;
import org.com.drSnehalAyuCareClinic.repository.KnownCasesRepository;
import org.com.drSnehalAyuCareClinic.repository.ObservationsRepository;
import org.com.drSnehalAyuCareClinic.repository.OtherAllergyRepository;
import org.com.drSnehalAyuCareClinic.repository.PathologyRepository;
import org.com.drSnehalAyuCareClinic.repository.PatientRepository;
import org.com.drSnehalAyuCareClinic.repository.PrescriptionsRepository;
import org.com.drSnehalAyuCareClinic.repository.RadiologyRepository;
import org.com.drSnehalAyuCareClinic.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all clinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Sonal Wankhede
 */
@Service

public class ClinicServiceImpl implements ClinicService {

	@Autowired
	CommonComponent commonComponent;

	private PatientRepository patientRepository;
	private DrugRepository drugRepository;
	private DrugAllergyRepository drugAllergyRepository;
	private OtherAllergyRepository otherAllergyRepository;
	private VisitRepository visitRepository;
	private DiagnosisRepository diagnosisRepository;
	private KnownCasesRepository knownCasesRepository;
	private ObservationsRepository observationsRepository;
	private PathologyRepository pathologyRepository; 
	private RadiologyRepository radiologyRepository;
	private PrescriptionsRepository prescriptionsRepository;
	private ComplaintsRepository complaintsRepository;

	@Autowired
	public ClinicServiceImpl(
			PatientRepository patientRepository,
			DrugRepository drugRepository,
			DrugAllergyRepository drugAllergyRepository,
			OtherAllergyRepository otherAllergyRepository,
			VisitRepository visitRepository,
			DiagnosisRepository diagnosisRepository,
			KnownCasesRepository knownCasesRepository,
			ObservationsRepository observationsRepository,
			PathologyRepository pathologyRepository,
			RadiologyRepository radiologyRepository,
			PrescriptionsRepository prescriptionsRepository,
			ComplaintsRepository complaintsRepository) {
		this.patientRepository = patientRepository;
		this.drugRepository = drugRepository;
		this.drugAllergyRepository = drugAllergyRepository;
		this.visitRepository = visitRepository;
		this.diagnosisRepository = diagnosisRepository;
		this.knownCasesRepository = knownCasesRepository;
		this.otherAllergyRepository = otherAllergyRepository;
		this.observationsRepository = observationsRepository;
		this.pathologyRepository = pathologyRepository;
		this.radiologyRepository = radiologyRepository;
		this.prescriptionsRepository = prescriptionsRepository;
		this.complaintsRepository = complaintsRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Patient findPatientById(int id) throws DataAccessException {
		Patient patient = null;
		try {
			patient = patientRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
			// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return patient;
	}

	@Override
	@Cacheable("patients")
	@Transactional(readOnly = true)
	public Collection<Patient> findAllPatients() throws DataAccessException {
		return patientRepository.findAll();
	}

	@Override
	@Transactional
	public void savePatient(Patient patient) throws DataAccessException {
		patientRepository.save(patient);

	}

	@Override
	@Transactional
	public void deletePatient(Patient patient) throws DataAccessException {
		patientRepository.delete(patient);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Patient> findPatientByLastName(String lastName) throws DataAccessException {
		return patientRepository.findByLastName(lastName);
	}

	@Override
	@Cacheable("drugs")
	public Collection<Drug> findAllDrugs() throws DataAccessException {
		return drugRepository.findAll();
	}

	@Override
	@CacheEvict("drugs")
	public void evictAllDrugCacheValues() {}

	@Override
	@CacheEvict("patients")
	public void evictAllPatientCacheValues() {}

	@Override
	@CacheEvict("visits")
	public void evictAllVisitCacheValues() {}

	@Override
	@CacheEvict("diagnosis")
	public void evictAllDiagnosisCacheValues() {}

	@Override
	@CacheEvict("drugAllergies")
	public void evictAllDrugAllergiesCacheValues() {}

	@Override
	@CacheEvict("otherAllergies")
	public void evictAllOtherAllergiesCacheValues() {}

	@Override
	@CacheEvict("history")
	public void evictAllHistoryCacheValues() {}

	@Override
	@CacheEvict("observations")
	public void evictAllObservationCacheValues() {}

	@Override
	@CacheEvict("radiology")
	public void evictAllRadiologyCacheValues() {}

	@Override
	@CacheEvict("pathology")
	public void evictAllPathologyCacheValues() {}

	@Override
	public Drug findDrugById(int drugId) {
		Drug drug = null;
		try {
			drug = drugRepository.findById(drugId);
		} catch (Exception e) {
			// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return drug;	}

	@Override
	public void saveDrug(@Valid Drug drug) {
		drugRepository.save(drug);
	}

	@Override
	public void deleteDrug(Drug drug) {
		drugRepository.delete(drug);
	}

	@Override
	public void deleteDrugs(Collection<Integer> drugIds) {
		commonComponent.deleteDrugs(drugIds);
	}


	@Override
	public void updateDrugs(@Valid DrugUpdateRequest request) {
		commonComponent.updateDrugs(request.getIds(), request.getFieldName(), request.getFieldValue());
	}

	@Override
	@Cacheable("drugAllergies")
	public Collection<DrugAllergy> findAllDrugAllergies() {
		return drugAllergyRepository.findAll();
	}

	@Override
	@Cacheable("visits")
	public Collection<? extends Visit> findAllVisits() {
		return visitRepository.findAll();
	}

	public List<Visit> findVisitsByPatientId(int patientId) {
		return visitRepository.findByPatientId(patientId);		
	}

	@Override
	public Visit findVisitById(int visitId) {
		return visitRepository.findById(visitId);
	}

	@Override
	public void saveVisit(@Valid Visit visit) {
		this.visitRepository.save(visit);
	}

	@Override
	public void deleteVisit(Visit visit) {
		this.visitRepository.delete(visit);
	}

	@Override
	@Cacheable("diagnosis")
	public Collection<Diagnosis> findAllDiagnosis() {
		return diagnosisRepository.findAll();
	}

	@Override
	@Cacheable("history")
	public Collection<KnownCase> findAllKnownCases() {
		return knownCasesRepository.findAll();
	}

	@Override
	public void addNewlyAddedDiagnosis(@Valid Diagnosis[] newlyAddedDiagnosis) {
		for (Diagnosis diagnosis : newlyAddedDiagnosis) {
			diagnosisRepository.save(diagnosis);
		}
	}

	@Override
	public void addDrugAllergies(@Valid DrugAllergy[] newlyAddedDrugAllergies) {
		for (DrugAllergy drugAllergy : newlyAddedDrugAllergies) {
			drugAllergyRepository.save(drugAllergy);
		}
	}

	@Override
	public void addOtherAllergies(@Valid OtherAllergy[] newlyAddedOtherAllergies) {
		for (OtherAllergy otherAllergy : newlyAddedOtherAllergies) {
			otherAllergyRepository.save(otherAllergy);
		}
	}

	@Override
	public void addKnownCases(@Valid KnownCase[] newlyAddedKnownCases) {
		for (KnownCase knownCase : newlyAddedKnownCases) {
			knownCasesRepository.save(knownCase);
		}
	}

	@Override
	public void addObservations(@Valid Observation[] newlyAddedObservations) {
		for (Observation observation : newlyAddedObservations) {
			observationsRepository.save(observation);
		}
	}

	@Override
	public void addRadioScans(@Valid Radiology[] newlyAddedRadioScans) {
		for (Radiology radiology : newlyAddedRadioScans) {
			radiologyRepository.save(radiology);
		}
	}

	@Override
	public void deleteRadioScans(@Valid Radiology[] removedScans) {
		commonComponent.deleteRadioScans(removedScans);
	}

	@Override
	public void addPathScans(@Valid Pathology[] newlyAddedPathScans) {
		for (Pathology pathology : newlyAddedPathScans) {
			pathologyRepository.save(pathology);
		}
	}

	@Override
	public void deletePathScans(@Valid Pathology[] removedPathScans) {
		commonComponent.deletePathScans(removedPathScans);
	}

	@Override
	@Cacheable("otherAllergies")
	public Collection<OtherAllergy> findAllOtherAllergies() {
		return otherAllergyRepository.findAll();
	}

	@Override
	@Cacheable("observations")
	public Collection<Observation> findAllObservations() {
		return observationsRepository.findAll();
	}

	@Override
	@Cacheable("pathology")
	public Collection<Pathology> findPathology() {
		return pathologyRepository.findAll();
	}

	@Override
	@Cacheable("radiology")
	public Collection<Radiology> findRadiology() {
		return radiologyRepository.findAll();
	}

	@Override
	public void savePrescription(Prescription currentPrescription) {
		prescriptionsRepository.save(currentPrescription);
	}

	@Override
	public void saveAllDrugs(Collection<Drug> drugs) {
		commonComponent.saveAllDrugs((List<Drug>) drugs);
	}

	@Override
	@Transactional
	public void deletePrescriptionByVisitId(List<Prescription> prescriptions) {
		commonComponent.deleteAllPrescriptions(prescriptions);
	}

	@Override
	public Collection<Complaints> findAllComplaints() {
		return complaintsRepository.findAll();
	}

}
