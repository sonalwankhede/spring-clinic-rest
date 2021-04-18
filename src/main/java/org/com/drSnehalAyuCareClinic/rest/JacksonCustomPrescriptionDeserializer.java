package org.com.drSnehalAyuCareClinic.rest;


import java.io.IOException;
import org.com.drSnehalAyuCareClinic.model.Prescription;
import org.com.drSnehalAyuCareClinic.model.Visit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class JacksonCustomPrescriptionDeserializer  extends StdDeserializer<Prescription> {

	public JacksonCustomPrescriptionDeserializer() {
		this(null);
	}

	public JacksonCustomPrescriptionDeserializer(Class<Prescription> t) {
		super(t);
	}

	@Override
	public Prescription deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
		Prescription prescription = new Prescription();
		Visit visit = new Visit();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = parser.getCodec().readTree(parser);
		JsonNode visit_node = node.get("visits");
		visit = mapper.convertValue(visit_node, Visit.class);
		int prescriptionsId = node.get("id").asInt();
		if (!(prescriptionsId == 0)) {
			prescription.setId(prescriptionsId);
		}
		prescription.setDrug(node.get("drug").asText(null));
		prescription.setDuration(node.get("duration").asText(null));
		prescription.setDose(node.get("dose").asText(null));
		prescription.setInstructions(node.get("instructions").asText(null));
		prescription.setVisit(visit);
		return prescription;
		
	}
}
