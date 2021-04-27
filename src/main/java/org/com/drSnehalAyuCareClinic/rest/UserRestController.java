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

import javax.validation.Valid;

import org.com.drSnehalAyuCareClinic.model.UserModel;
import org.com.drSnehalAyuCareClinic.service.UserService;
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

@RestController
@RequestMapping("users")
public class UserRestController {

	@Autowired
	private UserService userService;

	@PreAuthorize( "hasRole(@roles.ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	@CrossOrigin(exposedHeaders = "errors, content-type, Access-Control-Allow-Origin")
	public ResponseEntity<UserModel> addOwner(@RequestBody @Valid UserModel user,  BindingResult bindingResult) throws Exception {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (user == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<UserModel>(user, headers, HttpStatus.BAD_REQUEST);
		}

		this.userService.saveUser(user);
		return new ResponseEntity<UserModel>(user, headers, HttpStatus.CREATED);
	}
}
