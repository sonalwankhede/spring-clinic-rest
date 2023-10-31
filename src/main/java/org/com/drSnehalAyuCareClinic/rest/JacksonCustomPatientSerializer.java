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
import java.text.Format;
import java.text.SimpleDateFormat;

import org.com.drSnehalAyuCareClinic.model.Patient;
import org.com.drSnehalAyuCareClinic.model.Visit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Sonal Wankhede
 *
 */

public class JacksonCustomPatientSerializer extends StdSerializer<Patient> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JacksonCustomPatientSerializer() {
		this(null);
	}

	public JacksonCustomPatientSerializer(Class<Patient> t) {
		super(t);
	}

	@Override
	public void serialize(Patient patient, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		Format formatter = new SimpleDateFormat("yyyy/MM/dd");
		jgen.writeStartObject();
		if (patient.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", patient.getId());
		}

		jgen.writeStringField("firstName", patient.getFirstName());
		jgen.writeStringField("lastName", patient.getLastName());
		jgen.writeStringField("address", patient.getAddress());
		jgen.writeStringField("city", patient.getCity());
		jgen.writeStringField("gender", patient.getGender());
		jgen.writeStringField("middleName", patient.getMiddleName());
		jgen.writeStringField("telephone", patient.getTelephone());
		jgen.writeStringField("history", patient.getHistory());
		jgen.writeStringField("drugAllergies", patient.getDrugAllergies());
		jgen.writeStringField("otherAllergies", patient.getOtherAllergies());
		jgen.writeStringField("age", patient.getAge());
		
		// write visits array
		jgen.writeArrayFieldStart("visits");
		for (Visit visit : patient.getVisits()) {
			jgen.writeStartObject(); // visit
			if (visit.getId() == null) {
				jgen.writeNullField("id");
			} else {
				jgen.writeNumberField("id", visit.getId());
			}
			jgen.writeStringField("visitDate", formatter.format(visit.getDate()));
			jgen.writeStringField("nextFollowUp", visit.getNextFollowUp());
			jgen.writeStringField("diagnosis", visit.getDiagnosis());
			jgen.writeStringField("complaints", visit.getComplaints());
			jgen.writeStringField("observations", visit.getObservations());
			jgen.writeStringField("pathology", visit.getPathology());
			jgen.writeStringField("radiology", visit.getRadiology());

			
			jgen.writeNumberField("patient", visit.getPatient().getId());
			jgen.writeEndObject(); // visit
		}
		jgen.writeEndArray(); // visits
		jgen.writeEndObject(); // patient
	}

}
