package com.pcwerk.seck.rest.restlet.resources;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


import com.pcwerk.seck.rest.restlet.SeckWebRestletApp;

public class SeckResource extends ServerResource{
	@Get
	public Representation getEntries(Representation r) {
		// Return data for HTML Freemarker representation
		Map<String, Object> map = new HashMap<String, Object>();


		return toRepresentation(map, "seck.html", MediaType.TEXT_HTML);
	}
	
	public SeckWebRestletApp getApplication() {
		return (SeckWebRestletApp) super.getApplication();
	}
	private Representation toRepresentation(Map<String, Object> map,
			String templateName, MediaType mediaType) {
		return new TemplateRepresentation(templateName, getApplication()
				.getConfiguration(), map, mediaType);
	}

}
