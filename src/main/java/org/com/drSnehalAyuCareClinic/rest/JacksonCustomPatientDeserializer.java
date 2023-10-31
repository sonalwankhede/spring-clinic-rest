/*
 * Copyright 2016 the original author or authors.
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

import java.io.IOException;

import org.com.drSnehalAyuCareClinic.model.Patient;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author Sonal Wankhede
 *
 */

public class JacksonCustomPatientDeserializer extends StdDeserializer<Patient> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JacksonCustomPatientDeserializer(){
		this(null);
	}

	public JacksonCustomPatientDeserializer(Class<Patient> t) {
		super(t);
	}

	@Override
	public Patient deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		Patient patient = new Patient();
		String firstName = node.get("firstName").asText(null);
		String lastName = node.get("lastName").asText(null);
		String address = node.get("address").asText(null);
		String city = node.get("city").asText(null);
		String gender = node.get("gender").asText(null);
		String middleName = node.get("middleName").asText(null);
		String telephone = node.get("telephone").asText(null);
		String drugAllergies = node.get("drugAllergies").asText(null);
		String otherAllergies = node.get("otherAllergies").asText(null);
		String history = node.get("history").asText(null);
		String age = node.get("age").asText(null);
		if (node.hasNonNull("id")) {
			patient.setId(node.get("id").asInt());
		}
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setMiddleName(middleName);
        patient.setGender(gender);
        patient.setAddress(address);
        patient.setCity(city);
        patient.setTelephone(telephone);
        patient.setAge(age);
        patient.setHistory(history);
        patient.setDrugAllergies(drugAllergies);
        patient.setOtherAllergies(otherAllergies);
		return patient;
	}

}
