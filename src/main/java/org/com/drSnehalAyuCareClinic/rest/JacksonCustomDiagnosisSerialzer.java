package org.com.drSnehalAyuCareClinic.rest;

import java.io.IOException;

import org.com.drSnehalAyuCareClinic.model.Diagnosis;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonCustomDiagnosisSerialzer extends StdSerializer<Diagnosis> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JacksonCustomDiagnosisSerialzer() {
		this(null);
	}

	public JacksonCustomDiagnosisSerialzer(Class<Diagnosis> t) {
		super(t);
	}

	@Override
	public void serialize(Diagnosis diagnosis, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		if (diagnosis.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", diagnosis.getId());
		}
		jgen.writeStringField("diagnosis", diagnosis.getDiagnosis());

		jgen.writeEndObject(); // diagnosis
	}


}
