package com.pcwerk.seck.rest.restletTest.resources;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.pcwerk.seck.rest.restletTest.SeckWebRestletAppTest;

public class HomeResourceTest extends ServerResource{
	@Get("json:json")
	public Representation getEntries(Representation r) {
		// Return data for HTML Freemarker representation
		

		//JSONObject jsRequest = new JSONObject();

		if(this.getQuery().isEmpty())
		{
			return toRepresentation(null, "home.jsp", MediaType.TEXT_HTML);
				
		}
		
		Map<String, Object> map = new HashMap<String, Object>();

		// Get the name parameter from the URI pattern /helloworld/{name}
		
		JSONObject js =new JSONObject();
		JSONArray jsArray =  new JSONArray();
		try {
			for(int i=0;i<10;i++){
				js.put("link", "http://resulturl.com/example"+i);
				js.put("score", 60 + (int)(Math.random() * ((100 - 60) + 1)));
				jsArray.put(js);
				
			}
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		map.put("jsArray", jsArray);
				
		
		
		return toRepresentation(map, "results.jsp", MediaType.TEXT_HTML);
			


		 }
		
		
		
		
		
		
		//return toRepresentation("results.jsp", MediaType.TEXT_HTML);
		

	
	public SeckWebRestletAppTest getApplication() {
		return (SeckWebRestletAppTest) super.getApplication();
	}
	private Representation toRepresentation(Map<String, Object> map,String templateName, MediaType mediaType) {
		return new TemplateRepresentation(templateName, getApplication()
				.getConfiguration(),map,mediaType);
	}

}
