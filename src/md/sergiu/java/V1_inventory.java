package md.sergiu.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import md.sergiu.db.*;
import md.sergiu.util.*;

@Path ("/v1/inventory/")
public class V1_inventory {
	
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public Response returnAllclients() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null; 
		String returnString = null;
		Response rb = null;
		
		try {
			datasource connect = new datasource();
			conn = connect.datasource();
			
			query = conn.prepareStatement("Select * FROM customers");
			
			ResultSet rs = query.executeQuery();
			 
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (conn != null) conn.close();
		}
	return rb;
	}
	
	

}
