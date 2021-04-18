package org.com.drSnehalAyuCareClinic.rest;

import java.io.IOException;

import org.com.drSnehalAyuCareClinic.model.Visit;
import org.com.drSnehalAyuCareClinic.model.Prescription;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings("serial")
public class JacksonCustomPrescriptionSerializer  extends StdSerializer<Prescription> {
	/**
	 * 
	 */
	public JacksonCustomPrescriptionSerializer() {
		this(null);
	}

	protected JacksonCustomPrescriptionSerializer(Class<Prescription> t) {
		super(t);
	}

	@Override
	public void serialize(Prescription prescription, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if ((prescription == null) || (prescription.getVisit() == null)) {
			throw new IOException("Cannot serialize Visit object - prescriptions or prescriptions.visit is null");
		}
		jgen.writeStartObject(); // prescriptions
		if (prescription.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", prescription.getId());
		}
		jgen.writeStringField("drug", prescription.getDrug());
		jgen.writeStringField("duration", prescription.getDuration());
		jgen.writeStringField("instructions", prescription.getInstructions());
		jgen.writeStringField("dose", prescription.getDose());

		Visit visit = prescription.getVisit();
		jgen.writeObjectFieldStart("visit");
		if (visit.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", visit.getId());
		}

		jgen.writeEndObject(); // visit
		jgen.writeEndObject(); // prescriptions
	}
}
