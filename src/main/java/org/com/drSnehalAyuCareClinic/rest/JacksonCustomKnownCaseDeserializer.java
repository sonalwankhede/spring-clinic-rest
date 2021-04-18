package org.com.drSnehalAyuCareClinic.rest;

import java.io.IOException;

import org.com.drSnehalAyuCareClinic.model.KnownCase;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class JacksonCustomKnownCaseDeserializer extends StdDeserializer<KnownCase>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JacksonCustomKnownCaseDeserializer(Class<KnownCase> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public KnownCase deserialize(JsonParser parser, DeserializationContext cotext)
			throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		KnownCase knownCase = new KnownCase();
		String knownCaseName = node.get("knownCase").asText(null);
        knownCase.setIssues(knownCaseName);
		return knownCase;
	}

}
