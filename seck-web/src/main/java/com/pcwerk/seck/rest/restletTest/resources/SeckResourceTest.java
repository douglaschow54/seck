package com.pcwerk.seck.rest.restletTest.resources;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.json.*;

import com.pcwerk.seck.rest.restletTest.SeckWebRestletAppTest;

public class SeckResourceTest extends ServerResource{
	@Get
	public Representation getEntries(Representation r) {
		// Return data for HTML Freemarker representation
		Map<String, Object> map = new HashMap<String, Object>();

		//JSONObject jsRequest = new JSONObject();
		JSONObject object = new JSONObject();
		
		try {
			object.put("name","amir"); 
			object.put("Max.Marks",new Integer(100));
		
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("ghsgg");
		System.out.println(object);
		return toRepresentation(object, "home.html", MediaType.TEXT_HTML);
		
	}
	
	public SeckWebRestletAppTest getApplication() {
		return (SeckWebRestletAppTest) super.getApplication();
	}
	private Representation toRepresentation(JSONObject js,
			String templateName, MediaType mediaType) {
		return new TemplateRepresentation(templateName, getApplication()
				.getConfiguration(), js, mediaType);
	}

}
