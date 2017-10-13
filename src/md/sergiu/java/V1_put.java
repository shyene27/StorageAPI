package md.sergiu.java;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import md.sergiu.db.*;


public class V1_put {

	/**
	 * This method will allow you to update data in the PC_PARTS table.
	 * In this example we are using both PathParms and the message body (payload).
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/v1/put/{prod_qty}/{total_price}")
	@PUT
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(@PathParam("prod_qty") String prod_qty,
			@PathParam("total_price") int total_price,String incomingData) 
								throws Exception {
		
		System.out.println("incomingData: " + incomingData);
		System.out.println("brand: " + prod_qty);
		System.out.println("item_number: " + total_price);
		
		int id;
		int price;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		try {
			
			JSONObject partsData = new JSONObject(incomingData); //we are using json objects to parse data
			id = partsData.optInt("order_id", 0);
			price = partsData.optInt("total_price", 0);
			
			//call the correct sql method
			http_code = updateOrder(id, price);
			
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Order has been updated successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			
			returnString = jsonArray.put(jsonObject).toString();
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	/**
	 * This method allows you to update PC_PARTS table
	 * 
	 * Note: there is no validation being done... if this was a real project you
	 * must do validation here!
	 * 
	 * @param id
	 * @param price
	 * @return
	 * @throws Exception
	 */
	public int updateOrder(int id, int price) throws Exception {
		
		java.sql.PreparedStatement query = null;
		java.sql.Connection conn = null;
		
		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before updating data.
			 */
			
			datasource connect = new datasource();
			conn = connect.datasource();
			
			query = conn.prepareStatement("update orders " +
											"set total_price = ? " +
											"where order_id= ? ");
			
			query.setInt(1, price);
			query.setInt(2, id);
			query.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return 200;
	}
}
