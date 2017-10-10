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

@Path ("/v1/inventory")
public class V1_inventory {
	
/*
 * http://localhost:8080/StorageAPI/api/v1/inventory/hdd/UHD
 * 
 */	
	@Path ("/{product_name}/{manufactor}")
	@GET
	@Produces (MediaType.APPLICATION_JSON)
	
	public Response returnSpecificItemType(
			@PathParam("product_name") String product_name,
			@PathParam("manufactor") String manufactor) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null; 
		String returnString = null;
		Response rb = null;
		
		try {
			
		
			datasource connect = new datasource();
			conn = connect.datasource();
			
			query = conn.prepareStatement("Select product_id,product_name,manufactor,model,product_price "
					+ "FROM products "
					+ "where product_name = ? "
					+ "and manufactor = ?" );
			
			query.setString(1, product_name);
			query.setString(2, manufactor);
			
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
