package org.com.drSnehalAyuCareClinic.rest;

import java.io.IOException;

import org.com.drSnehalAyuCareClinic.model.Diagnosis;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class JacksonCustomDiagnosisDeserialzer extends StdDeserializer<Diagnosis>{

	public JacksonCustomDiagnosisDeserialzer(){
		this(null);
	}

	public JacksonCustomDiagnosisDeserialzer(Class<Diagnosis> t) {
		super(t);
	}

	@Override
	public Diagnosis deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		Diagnosis diagnosis = new Diagnosis();
		String diagnosisName = node.get("diagnosis").asText(null);
		if (node.hasNonNull("id")) {
			diagnosis.setId(node.get("id").asInt());
		}
		diagnosis.setDiagnosis(diagnosisName);
		return diagnosis;
	}

}
