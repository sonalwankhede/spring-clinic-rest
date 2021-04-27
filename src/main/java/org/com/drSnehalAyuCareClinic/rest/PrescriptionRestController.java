package org.com.drSnehalAyuCareClinic.rest;

import javax.validation.Valid;

import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type, Access-Control-Allow-Origin")
@RequestMapping("api/prescriptions")
public class PrescriptionRestController {

	@Autowired
	private ClinicService clinicService;

//	@PreAuthorize( "hasRole(@roles.ADMIN)" )
//	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
//	public ResponseEntity<Collection<Prescription>> getAllPrescriptions(){
//		Collection<Prescription> prescriptions = new ArrayList<Prescription>();
//		prescriptions.addAll(this.clinicService.findAllPrescriptions());
//		if (prescriptions.isEmpty()){
//			return new ResponseEntity<Collection<Prescription>>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<Collection<Prescription>>(prescriptions, HttpStatus.OK);
//	}

//	@PreAuthorize( "hasRole(@roles.ADMIN)" )
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
//	public ResponseEntity<Prescription> getPrescription(@PathVariable("id") int id){
//		Prescription prescription = this.clinicService.findPrescriptionById(id);
//		if(prescription == null){
//			return new ResponseEntity<Prescription>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<Prescription>(prescription, HttpStatus.OK);
//	}

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Prescription> addPrescription(@RequestBody @Valid Prescription prescription, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (prescription == null) || (prescription.getVisit() == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Prescription>(headers, HttpStatus.BAD_REQUEST);
		}
		this.clinicService.savePrescription(prescription);
		headers.setLocation(ucBuilder.path("/api/prescriptions/{id}").buildAndExpand(prescription.getId()).toUri());
		return new ResponseEntity<Prescription>(prescription, headers, HttpStatus.CREATED);
	}

//	@PreAuthorize( "hasRole(@roles.ADMIN)" )
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
//	public ResponseEntity<Prescription> updatePrescription(@PathVariable("id") int id, @RequestBody @Valid Prescription prescription, BindingResult bindingResult){
//		BindingErrorsResponse errors = new BindingErrorsResponse();
//		HttpHeaders headers = new HttpHeaders();
//		if(bindingResult.hasErrors() || (prescription == null) || (prescription.getVisit() == null)){
//			errors.addAllErrors(bindingResult);
//			headers.add("errors", errors.toJSON());
//			return new ResponseEntity<Prescription>(headers, HttpStatus.BAD_REQUEST);
//		}
//		Prescription currentPrescription = this.clinicService.findPrescriptionById(id);
//		if(currentPrescription == null){
//			return new ResponseEntity<Prescription>(HttpStatus.NOT_FOUND);
//		}
//		currentPrescription.setDrug(prescription.getDrug());
//		currentPrescription.setDuration(prescription.getDuration());
//		currentPrescription.setDose(prescription.getDose());
//		currentPrescription.setInstructions(prescription.getInstructions());
//		this.clinicService.savePrescription(currentPrescription);
//		return new ResponseEntity<Prescription>(currentPrescription, HttpStatus.NO_CONTENT);
//	}

//	@PreAuthorize( "hasRole(@roles.ADMIN)" )
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
//	@Transactional
//	public ResponseEntity<Void> deletePrescription(@PathVariable("id") int id){
//		Prescription prescription = this.clinicService.findPrescriptionById(id);
//		if(prescription == null){
//			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
//		}
//		this.clinicService.deletePrescription(prescription);
//		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//	}

}
