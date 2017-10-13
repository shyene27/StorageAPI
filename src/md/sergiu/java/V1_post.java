package md.sergiu.java;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


import md.sergiu.db.*;

@Path("/v1/post")
public class V1_post {
	
	/**
	 * This method will allow to insert data in the orders table.
	 * 
	 */
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)

public Response addOrders(String incomingData) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
		try {
			
			/*
			 * We can create a new instance and it will accept a JSON string
			 * By doing this, we can now access the data.
			 */
			JSONObject partsData = new JSONObject(incomingData);
			System.out.println( "jsonData: " + partsData.toString() );
			
			/*
			 * In order to access the data, you will need to use one of the method in JSONArray
			 * or JSONObject.  I recommend using the optXXXX methods instead of the get method.
			 * 
			 * Example:
			 * partsData.get("PC_PARTS_TITLE");
			 * The example above will get you the data, the problem is, if PC_PARTS_TITLE does
			 * not exist, it will generate a java error.  If you are using get, you need to use
			 * the has method first partsData.has("PC_PARTS_TITLE");. 
			 * 
			 * Example:
			 * partsData.optString("PC_PARTS_TITLE");
			 * The optString example above will also return data but if PC_PARTS_TITLE does not
			 * exist, it will return a BLANK string.
			 * 
			 * partsData.optString("PC_PARTS_TITLE", "NULL");
			 * You can add a second parameter, it will return NULL if PC_PARTS_TITLE does not
			 * exist.
			 */
			int http_code = insertIntoOrders(partsData.optString("prod_qty"), 
														partsData.optString("total_price"), 
														partsData.optString("order_date"), 
														partsData.optString("product_id"), 
														partsData.optString("customer_id") );
			
			if( http_code == 200 ) {
				/*
				 * The put method allows you to add data to a JSONObject.
				 * The first parameter is the KEY (no spaces)
				 * The second parameter is the Value
				 */
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been entered successfully");
				/*
				 * When you are dealing with JSONArrays, the put method is used to add
				 * JSONObjects into JSONArray.
				 */
				returnString = jsonArray.put(jsonObject).toString();
			} else {
				return Response.status(500).entity("Unable to enter Item").build();
			}
			
			System.out.println( "returnString: " + returnString );
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	
	/**
	 * This method will prepare the query for inserting into the Orders table. 
	 * 
	 * @param prod_qty
	 * @param total_price
	 * @param order_date
	 * @param product_id 
	 * @param customer_id
	 * @return integer 200 for success, 500 for error
	 * @throws Exception
	 */
	
	public int insertIntoOrders(String prod_qty, 
			String total_price, 
			String order_date, 
			String product_id, 
			String customer_id) 
		throws Exception {

		java.sql.PreparedStatement query = null;
		Connection conn = null;
try {

	datasource connect = new datasource();
	conn = connect.datasource();
	
	query = conn.prepareStatement("insert into orders " +
			"(prod_qty, total_price, order_date, product_id, customer_id) " +
			"VALUES ( ?, ?, ?, ?, ? ) ");
	
	int avilInt1 = Integer.parseInt(prod_qty);
	query.setInt(1, avilInt1);
	int avilInt2 = Integer.parseInt(total_price);
	query.setInt(2, avilInt2);
	query.setString(3, order_date);
	int avilInt4 = Integer.parseInt(product_id);
	query.setInt(4, avilInt4);
	int avilInt5 = Integer.parseInt(customer_id);
	query.setInt(5, avilInt5);
		
	query.executeUpdate(); //note the new command for insert statement 
	
		
	} catch(Exception e) {
	e.printStackTrace();
	return 500; //if a error occurs, return a 500
	}
finally {
	if (conn != null) conn.close();
		}

return 200;
	}
	
}
