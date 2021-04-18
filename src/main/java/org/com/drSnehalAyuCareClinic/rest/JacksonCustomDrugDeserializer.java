package org.com.drSnehalAyuCareClinic.rest;

import java.io.IOException;

import org.com.drSnehalAyuCareClinic.model.Drug;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class JacksonCustomDrugDeserializer extends StdDeserializer<Drug> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JacksonCustomDrugDeserializer(){
		this(null);
	}

	public JacksonCustomDrugDeserializer(Class<Drug> t) {
		super(t);
	}

	@Override
	public Drug deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		Drug drug = new Drug();
		String brandName = node.get("brandName").asText(null);
		String strength = node.get("strength").asText(null);
		String formOfDrugs = node.get("formOfDrugs").asText(null);
		String content = node.get("content").asText(null);
		if (node.hasNonNull("id")) {
			drug.setId(node.get("id").asInt());
		}
        drug.setBrandName(brandName);
        drug.setFormOfDrugs(formOfDrugs);
        drug.setStrength(strength);
        drug.setContent(content);
		return drug;
	}

}
