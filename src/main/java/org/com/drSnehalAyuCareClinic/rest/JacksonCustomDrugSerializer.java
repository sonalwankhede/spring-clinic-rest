package org.com.drSnehalAyuCareClinic.rest;

import java.io.IOException;

import org.com.drSnehalAyuCareClinic.model.Drug;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonCustomDrugSerializer extends StdSerializer<Drug> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JacksonCustomDrugSerializer() {
		this(null);
	}

	public JacksonCustomDrugSerializer(Class<Drug> t) {
		super(t);
	}

	@Override
	public void serialize(Drug drug, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		jgen.writeStartObject();
		if (drug.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", drug.getId());
		}

		jgen.writeStringField("brandName", drug.getBrandName());
		jgen.writeStringField("formOfDrugs", drug.getFormOfDrugs());
		jgen.writeStringField("strength", drug.getStrength());
		jgen.writeStringField("content", drug.getContent());
		
		jgen.writeEndObject(); // drug
	}


}
