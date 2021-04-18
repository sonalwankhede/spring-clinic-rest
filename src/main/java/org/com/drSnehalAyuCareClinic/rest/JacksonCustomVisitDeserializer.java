package org.com.drSnehalAyuCareClinic.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.com.drSnehalAyuCareClinic.model.Patient;
import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Visit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author Sonal Wankhede
 *
 */

@SuppressWarnings("serial")
public class JacksonCustomVisitDeserializer extends StdDeserializer<Visit> {

	/**
	 * 
	 */
	public JacksonCustomVisitDeserializer() {
		this(null);
	}

	public JacksonCustomVisitDeserializer(Class<Visit> t) {
		super(t);
	}

	@Override
	public Visit deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Visit visit = new Visit();
		Patient patient = new Patient();
		ObjectMapper mapper = new ObjectMapper();
		Date visitDate = new Date();
		JsonNode node = parser.getCodec().readTree(parser);
		JsonNode patient_node = node.get("patient");
		patient = mapper.convertValue(patient_node, Patient.class);
		int visitId = node.get("id").asInt();
		String visitDateStr = node.get("visitDate").asText(null);

		try {
			visitDate = formatter.parse(visitDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		if (!(visitId == 0)) {
			visit.setId(visitId);
		}
		visit.setDate(visitDate);
		visit.setTemperature(node.get("temperature").asInt());
		visit.setPulse(node.get("pulse").asInt());
		visit.setSpo2(node.get("spo2").asInt());
		visit.setRespirationRate(node.get("respirationRate").asText(null));
		visit.setBloodPressure(node.get("bloodPressure").asText(null));
		visit.setHeight(node.get("height").asText(null));
		visit.setWeight(node.get("weight").asText(null));
		visit.setBmi(node.get("bmi").asText(null));		
		visit.setComplaints(node.get("complaints").asText(null));
		visit.setObservations(node.get("observations").asText(null));
		visit.setPatient(patient);
		visit.setDiagnosis(node.get("diagnosis").asText(null));

		JsonNode valuesNode = node.get("prescriptions");
		if (valuesNode != null) {
			for (JsonNode valueNode : valuesNode) {
				Prescription prescription = new Prescription();
				prescription.setVisit(visit);
				prescription.setDrug(valueNode.get("drug").asText(null));
				prescription.setDuration(valueNode.get("duration").asText(null));
				prescription.setDose(valueNode.get("dose").asText(null));
				prescription.setInstructions(valueNode.get("instructions").asText(null));
				visit.addPrescription(prescription);
			}
		}
		visit.setPathology(node.get("pathology").asText(null));
		visit.setRadiology(node.get("radiology").asText(null));
		return visit;
	}

//	private String stringFromArray(String fieldName, JsonNode node) {
//		List<String> diagnosisList = new ArrayList<String>();
//		ArrayNode arrayNode = (ArrayNode) node.get(fieldName);
//		Iterator<JsonNode> nodeIterator = arrayNode.elements();
//		while (nodeIterator.hasNext()) {
//			diagnosisList.add(nodeIterator.next().asText(null));
//		}
//		return String.join(", ", diagnosisList); 
//	}
}
