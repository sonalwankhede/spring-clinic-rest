package org.com.drSnehalAyuCareClinic.rest;

import java.util.Collection;

import javax.validation.Valid;

import org.com.drSnehalAyuCareClinic.model.Complaints;
import org.com.drSnehalAyuCareClinic.model.Diagnosis;
import org.com.drSnehalAyuCareClinic.model.DrugAllergy;
import org.com.drSnehalAyuCareClinic.model.KnownCase;
import org.com.drSnehalAyuCareClinic.model.Observation;
import org.com.drSnehalAyuCareClinic.model.OtherAllergy;
import org.com.drSnehalAyuCareClinic.model.Pathology;
import org.com.drSnehalAyuCareClinic.model.Radiology;
import org.com.drSnehalAyuCareClinic.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type, Access-Control-Allow-Origin")
@RequestMapping("/api")
public class CommonRestController {
	@Autowired
	private ClinicService clinicService;

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/drugAllergies", produces = "application/json")
	public ResponseEntity<Collection<DrugAllergy>> getDrugAllergies() {
		Collection<DrugAllergy> allergies = this.clinicService.findAllDrugAllergies();
		if (allergies.isEmpty()) {
			return new ResponseEntity<Collection<DrugAllergy>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<DrugAllergy>>(allergies, HttpStatus.OK);
	}
	
	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/complaints", produces = "application/json")
	public ResponseEntity<Collection<Complaints>> getComplaints() {
		Collection<Complaints> complaints = this.clinicService.findAllComplaints();
		if (complaints.isEmpty()) {
			return new ResponseEntity<Collection<Complaints>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Complaints>>(complaints, HttpStatus.OK);
	}
	
	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/complaints", produces = "application/json")
	public ResponseEntity<Complaints[]> addComplaints(@RequestBody @Valid Complaints[] newlyAddedComplaints, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(newlyAddedComplaints != null && newlyAddedComplaints.length > 0 ) {
			this.clinicService.addComplaints(newlyAddedComplaints);
			this.clinicService.evictAllComplaintsCacheValues();
		}
		return new ResponseEntity<Complaints[]>(newlyAddedComplaints, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/drugAllergies", produces = "application/json")
	public ResponseEntity<DrugAllergy[]> addDrugAllergies(@RequestBody @Valid DrugAllergy[] newlyAddedDrugAllergies, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(newlyAddedDrugAllergies != null && newlyAddedDrugAllergies.length > 0 ) {
			this.clinicService.addDrugAllergies(newlyAddedDrugAllergies);
			this.clinicService.evictAllDrugAllergiesCacheValues();
		}
		return new ResponseEntity<DrugAllergy[]>(newlyAddedDrugAllergies, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/otherAllergies", produces = "application/json")
	public ResponseEntity<Collection<OtherAllergy>> getOtherAllergies() {
		Collection<OtherAllergy> allergies = this.clinicService.findAllOtherAllergies();
		if (allergies.isEmpty()) {
			return new ResponseEntity<Collection<OtherAllergy>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<OtherAllergy>>(allergies, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/otherAllergies", produces = "application/json")
	public ResponseEntity<OtherAllergy[]> addOtherAllergies(@RequestBody @Valid OtherAllergy[] newlyAddedOtherAllergies, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(newlyAddedOtherAllergies != null && newlyAddedOtherAllergies.length > 0 ) {
			this.clinicService.addOtherAllergies(newlyAddedOtherAllergies);
			this.clinicService.evictAllOtherAllergiesCacheValues();
		}
		return new ResponseEntity<OtherAllergy[]>(newlyAddedOtherAllergies, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/diagnosisDictionary", produces = "application/json")
	public ResponseEntity<Collection<Diagnosis>> getDiagnosisDictionary() {
		Collection<Diagnosis> diagnosisDictionary = this.clinicService.findAllDiagnosis();
		if (diagnosisDictionary.isEmpty()) {
			return new ResponseEntity<Collection<Diagnosis>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Diagnosis>>(diagnosisDictionary, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/diagnosisDictionary", produces = "application/json")
	public ResponseEntity<Diagnosis[]> addNewlyAddedDiagnosis(@RequestBody @Valid Diagnosis[] newlyAddedDiagnosis, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(newlyAddedDiagnosis != null && newlyAddedDiagnosis.length > 0 ) {
			this.clinicService.addNewlyAddedDiagnosis(newlyAddedDiagnosis);
			this.clinicService.evictAllDiagnosisCacheValues();
		}
		return new ResponseEntity<Diagnosis[]>(newlyAddedDiagnosis, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/knownCases", produces = "application/json")
	public ResponseEntity<Collection<KnownCase>> getKnownCases() {
		Collection<KnownCase> knownCasesDictionary = this.clinicService.findAllKnownCases();
		if (knownCasesDictionary.isEmpty()) {
			return new ResponseEntity<Collection<KnownCase>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<KnownCase>>(knownCasesDictionary, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/knownCases", produces = "application/json")
	public ResponseEntity<KnownCase[]> addOtherAllergies(@RequestBody @Valid KnownCase[] newlyAddedKnownCases, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(newlyAddedKnownCases != null && newlyAddedKnownCases.length > 0 ) {
			this.clinicService.addKnownCases(newlyAddedKnownCases);
			this.clinicService.evictAllHistoryCacheValues();
		}
		return new ResponseEntity<KnownCase[]>(newlyAddedKnownCases, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/observations", produces = "application/json")
	public ResponseEntity<Collection<Observation>> getObservations() {
		Collection<Observation> observations = this.clinicService.findAllObservations();
		if (observations.isEmpty()) {
			return new ResponseEntity<Collection<Observation>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Observation>>(observations, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/observations", produces = "application/json")
	public ResponseEntity<Observation[]> addObservations(@RequestBody @Valid Observation[] newlyAddedObservations, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(newlyAddedObservations != null && newlyAddedObservations.length > 0 ) {
			this.clinicService.addObservations(newlyAddedObservations);
			this.clinicService.evictAllObservationCacheValues();
		}
		return new ResponseEntity<Observation[]>(newlyAddedObservations, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/pathology", produces = "application/json")
	public ResponseEntity<Collection<Pathology>> getPathologyTests() {
		Collection<Pathology> pathology = this.clinicService.findPathology();
		if (pathology.isEmpty()) {
			return new ResponseEntity<Collection<Pathology>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Pathology>>(pathology, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/pathology", produces = "application/json")
	public ResponseEntity<Pathology[]> addPathScans(@RequestBody @Valid Pathology[] newlyAddedPathScans, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(newlyAddedPathScans != null && newlyAddedPathScans.length > 0 ) {
			this.clinicService.addPathScans(newlyAddedPathScans);
			this.clinicService.evictAllPathologyCacheValues();
		}
		return new ResponseEntity<Pathology[]>(newlyAddedPathScans, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/pathology/delete", produces = "application/json")
	public ResponseEntity<Pathology[]> deletePathScans(@RequestBody @Valid Pathology[] removedPathScans, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(removedPathScans != null && removedPathScans.length > 0 ) {
			this.clinicService.deletePathScans(removedPathScans);
			this.clinicService.evictAllPathologyCacheValues();
		}
		return new ResponseEntity<Pathology[]>(removedPathScans, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@GetMapping(value = "/radiology", produces = "application/json")
	public ResponseEntity<Collection<Radiology>> getRadiologyScans() {
		Collection<Radiology> radiologies = this.clinicService.findRadiology();
		if (radiologies.isEmpty()) {
			return new ResponseEntity<Collection<Radiology>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Radiology>>(radiologies, HttpStatus.OK);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/radiology", produces = "application/json")
	public ResponseEntity<Radiology[]> addRadioScans(@RequestBody @Valid Radiology[] newlyAddedRadioScans, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(newlyAddedRadioScans != null && newlyAddedRadioScans.length > 0 ) {
			this.clinicService.addRadioScans(newlyAddedRadioScans);
			this.clinicService.evictAllRadiologyCacheValues();
		}
		return new ResponseEntity<Radiology[]>(newlyAddedRadioScans, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/radiology/delete", produces = "application/json")
	public ResponseEntity<Radiology[]> deleteRadioScans(@RequestBody @Valid Radiology[] removedScans, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(removedScans != null && removedScans.length > 0 ) {
			this.clinicService.deleteRadioScans(removedScans);
			this.clinicService.evictAllRadiologyCacheValues();
		}
		return new ResponseEntity<Radiology[]>(removedScans, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/drugAllergies/delete", produces = "application/json")
	public ResponseEntity<DrugAllergy[]> deleteDrugAllergies(@RequestBody @Valid DrugAllergy[] removedDrugAllergies, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(removedDrugAllergies != null && removedDrugAllergies.length > 0 ) {
			this.clinicService.deleteDrugAllergies(removedDrugAllergies);
			this.clinicService.evictAllDrugAllergiesCacheValues();
		}
		return new ResponseEntity<DrugAllergy[]>(removedDrugAllergies, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/otherAllergies/delete", produces = "application/json")
	public ResponseEntity<OtherAllergy[]> deleteOtherAllergies(@RequestBody @Valid OtherAllergy[] removedOtherAllergies, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(removedOtherAllergies != null && removedOtherAllergies.length > 0 ) {
			this.clinicService.deleteOtherAllergies(removedOtherAllergies);
			this.clinicService.evictAllOtherAllergiesCacheValues();
		}
		return new ResponseEntity<OtherAllergy[]>(removedOtherAllergies, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/knownCases/delete", produces = "application/json")
	public ResponseEntity<KnownCase[]> deleteOtherAllergies(@RequestBody @Valid KnownCase[] removedKnownCases, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(removedKnownCases != null && removedKnownCases.length > 0 ) {
			this.clinicService.deleteKnownCases(removedKnownCases);
			this.clinicService.evictAllHistoryCacheValues();
		}
		return new ResponseEntity<KnownCase[]>(removedKnownCases, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/complaints/delete", produces = "application/json")
	public ResponseEntity<Complaints[]> deleteOtherAllergies(@RequestBody @Valid Complaints[] removedComplaints, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(removedComplaints != null && removedComplaints.length > 0 ) {
			this.clinicService.deleteComplaints(removedComplaints);
			this.clinicService.evictAllComplaintsCacheValues();
		}
		return new ResponseEntity<Complaints[]>(removedComplaints, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/observations/delete", produces = "application/json")
	public ResponseEntity<Observation[]> deleteObservations(@RequestBody @Valid Observation[] removedObservations, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(removedObservations != null && removedObservations.length > 0 ) {
			this.clinicService.deleteObservations(removedObservations);
			this.clinicService.evictAllObservationCacheValues();
		}
		return new ResponseEntity<Observation[]>(removedObservations, headers, HttpStatus.CREATED);
	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@PostMapping(value = "/diagnosisDictionary/delete", produces = "application/json")
	public ResponseEntity<Diagnosis[]> deletePreliminaryDiagnosis(@RequestBody @Valid Diagnosis[] removedDiagnosis, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		if(removedDiagnosis != null && removedDiagnosis.length > 0 ) {
			this.clinicService.deleteDiagnosis(removedDiagnosis);
			this.clinicService.evictAllDiagnosisCacheValues();
		}
		return new ResponseEntity<Diagnosis[]>(removedDiagnosis, headers, HttpStatus.CREATED);
	}
}
