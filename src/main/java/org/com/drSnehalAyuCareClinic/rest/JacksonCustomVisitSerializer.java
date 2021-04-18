package org.com.drSnehalAyuCareClinic.rest;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.com.drSnehalAyuCareClinic.model.Patient;
import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Visit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Sonal Wankhede
 *
 */

@SuppressWarnings("serial")
public class JacksonCustomVisitSerializer extends StdSerializer<Visit> {

	/**
	 * 
	 */
	public JacksonCustomVisitSerializer() {
		this(null);
	}

	protected JacksonCustomVisitSerializer(Class<Visit> t) {
		super(t);
	}

	@Override
	public void serialize(Visit visit, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if ((visit == null) || (visit.getPatient() == null)) {
			throw new IOException("Cannot serialize Visit object - visit or visit.patient is null");
		}
		Format formatter = new SimpleDateFormat("yyyy/MM/dd");
		jgen.writeStartObject(); // visit
		if (visit.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", visit.getId());
		}
		jgen.writeStringField("visitDate", formatter.format(visit.getDate()));
		jgen.writeNumberField("temperature", visit.getTemperature());
		jgen.writeNumberField("pulse", visit.getPulse());
		jgen.writeNumberField("spo2", visit.getSpo2());
		jgen.writeStringField("respirationRate", visit.getRespirationRate());
		jgen.writeStringField("bloodPressure", visit.getBloodPressure());
		jgen.writeStringField("height", visit.getHeight());
		jgen.writeStringField("weight", visit.getWeight());
		jgen.writeStringField("bmi", visit.getBmi());
		jgen.writeStringField("diagnosis", visit.getDiagnosis());
		jgen.writeStringField("complaints", visit.getComplaints());
		jgen.writeStringField("observations", visit.getObservations());
		jgen.writeStringField("pathology", visit.getPathology());
		jgen.writeStringField("radiology", visit.getRadiology());

		Patient patient = visit.getPatient();
		jgen.writeObjectFieldStart("patient");
		if (patient.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", patient.getId());
		}
		jgen.writeStringField("firstName", patient.getFirstName());
		jgen.writeStringField("middleName", patient.getMiddleName());
		jgen.writeStringField("lastName", patient.getLastName());

		jgen.writeNumberField("age", patient.getAge());
		jgen.writeStringField("gender", patient.getGender());
		jgen.writeStringField("history", patient.getHistory());
		jgen.writeStringField("otherAllergies", patient.getOtherAllergies());
		jgen.writeStringField("drugAllergies", patient.getDrugAllergies());
		jgen.writeStringField("address", patient.getAddress());
		jgen.writeStringField("city", patient.getCity());
		jgen.writeStringField("telephone", patient.getTelephone());
		
		jgen.writeEndObject(); // patient

		// write prescriptions array
		jgen.writeArrayFieldStart("prescription");
		for (Prescription prescription : visit.getPrescriptions()) {
			jgen.writeStartObject(); // visit
			if (prescription.getId() == null) {
				jgen.writeNullField("id");
			} else {
				jgen.writeNumberField("id", prescription.getId());
			}
			jgen.writeStringField("drug", prescription.getDrug());
			jgen.writeStringField("duration", prescription.getDuration());
			jgen.writeStringField("dose", prescription.getDose());
			jgen.writeStringField("instructions", prescription.getInstructions());
			jgen.writeNumberField("visit", prescription.getVisit().getId());
			jgen.writeEndObject(); // prescriptions
		}
		jgen.writeEndArray();
		jgen.writeEndObject(); // visit
	}

}
