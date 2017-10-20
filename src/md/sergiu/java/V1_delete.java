package md.sergiu.java;

import java.sql.*;


import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import md.sergiu.db.*;


@Path("/v1/delete")
public class V1_delete {
	/**
	 * This method allows you to delete a row from store table
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	
	@DELETE
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(String incomingData) 
		throws Exception {	
		
		//System.out.println("incomingData: " + incomingData);
		//System.out.println("item_number: " + item_number);
		
		int id;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		try {
			
			JSONObject partsData = new JSONObject(incomingData);
			id = partsData.optInt("order_id", 0);
			
			http_code = delete_order_id(id);
			
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been deleted successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			
			returnString = jsonArray.put(jsonObject).toString();		
			
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	
		
	
	public int delete_order_id (int id) throws Exception{
	

		
		java.sql.PreparedStatement query_insert = null;
		java.sql.PreparedStatement query_delete = null;
		java.sql.Connection conn = null;
		
		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before updating data.
			 */
			
			datasource connect = new datasource();
			conn = connect.datasource();
			
			query_insert = conn.prepareStatement("INSERT INTO store.archiveorders (order_id, prod_qty, total_price, order_date, product_id, customer_id) (\r\n" + 
					"SELECT order_id, prod_qty, total_price, order_date, product_id, customer_id\r\n" + 
					"FROM store.orders\r\n" + 
					"WHERE order_id= ? );\r\n");
					//"DELETE FROM store.orders WHERE order_id= ?; ");
			query_insert.setInt(1, id);
			query_insert.executeUpdate();
			
			
			query_delete = conn.prepareStatement("DELETE FROM store.orders WHERE order_id= ? ");
			query_delete.setInt(1, id);
			query_delete.executeUpdate();
			//System.out.println("querry = " + query);

						
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
