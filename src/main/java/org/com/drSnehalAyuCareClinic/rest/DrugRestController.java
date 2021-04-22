/*
 * Copyright 2016-2017 the original author or authors.
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

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.com.drSnehalAyuCareClinic.model.Drug;
import org.com.drSnehalAyuCareClinic.model.DrugUpdateRequest;
import org.com.drSnehalAyuCareClinic.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Sonal Wankhede
 *
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/drugs")
public class DrugRestController {

	@Autowired
	private ClinicService clinicService;

    @PreAuthorize( "hasRole(@roles.ADMIN)" )
	@RequestMapping(value = "/{drugId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Drug> getInventory(@PathVariable("drugId") int drugId){
		Drug drug = this.clinicService.findDrugById(drugId);
		if(drug == null){
			return new ResponseEntity<Drug>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Drug>(drug, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Drug> addInventory(@RequestBody @Valid Drug drug, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (drug == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Drug>(headers, HttpStatus.BAD_REQUEST);
		}
		this.clinicService.saveDrug(drug);
   		this.clinicService.evictAllDrugCacheValues();
		headers.setLocation(ucBuilder.path("/api/drugs/{id}").buildAndExpand(drug.getId()).toUri());
		return new ResponseEntity<Drug>(drug, headers, HttpStatus.CREATED);
	}
    
    @PreAuthorize( "hasRole(@roles.ADMIN)" )
   	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json")
   	public ResponseEntity<Collection<Drug>> uploadDrugs(@RequestBody @Valid Collection<Drug> drugs, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
   		BindingErrorsResponse errors = new BindingErrorsResponse();
   		HttpHeaders headers = new HttpHeaders();
   		if(bindingResult.hasErrors() || (drugs == null)){
   			errors.addAllErrors(bindingResult);
   			headers.add("errors", errors.toJSON());
   			return new ResponseEntity<Collection<Drug>>(headers, HttpStatus.BAD_REQUEST);
   		}
   		this.clinicService.saveAllDrugs(drugs);
   		this.clinicService.evictAllDrugCacheValues();
   		return new ResponseEntity<Collection<Drug>>(drugs, headers, HttpStatus.CREATED);
   	}

    @PreAuthorize( "hasRole(@roles.ADMIN)" )
	@RequestMapping(value = "/{drugId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Drug> updateInventory(@PathVariable("drugId") int drugId, @RequestBody @Valid Drug drug, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (drug == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Drug>(headers, HttpStatus.BAD_REQUEST);
		}
		Drug currentDrug = this.clinicService.findDrugById(drugId);
		if(currentDrug == null){
			return new ResponseEntity<Drug>(HttpStatus.NOT_FOUND);
		}
		currentDrug.setContent(drug.getContent());
		currentDrug.setBrandName(drug.getBrandName());
		currentDrug.setFormOfDrugs(drug.getFormOfDrugs());
		currentDrug.setStrength(drug.getStrength());
		this.clinicService.saveDrug(currentDrug);
   		this.clinicService.evictAllDrugCacheValues();
		return new ResponseEntity<Drug>(currentDrug, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.ADMIN)" )
	@RequestMapping(value = "/{drugId}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deleteInventory(@PathVariable("drugId") int drugId){
		Drug drug = this.clinicService.findDrugById(drugId);
		if(drug == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.clinicService.deleteDrug(drug);
   		this.clinicService.evictAllDrugCacheValues();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
    
    @PreAuthorize( "hasRole(@roles.ADMIN)" )
   	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
   	@Transactional
   	public ResponseEntity<Void> deleteMultipleInventory(@RequestBody @Valid Collection<Integer> drugIds){

   		this.clinicService.deleteDrugs(drugIds);
      		this.clinicService.evictAllDrugCacheValues();
   		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   	}
    
    @PreAuthorize( "hasRole(@roles.ADMIN)" )
   	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
   	@Transactional
   	public ResponseEntity<Void> updateMultipleInventory(@RequestBody DrugUpdateRequest request){
   		this.clinicService.updateDrugs(request);
      		this.clinicService.evictAllDrugCacheValues();
   		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
   	}

    @PreAuthorize( "hasRole(@roles.ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<Drug>> getAllDrugs(){
		Collection<Drug> drugsList = new ArrayList<Drug>();
		drugsList.addAll(this.clinicService.findAllDrugs());
		if (drugsList.isEmpty()){
			return new ResponseEntity<Collection<Drug>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Drug>>(drugsList, HttpStatus.OK);
	}
    
}
