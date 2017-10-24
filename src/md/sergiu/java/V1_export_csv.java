package md.sergiu.java;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import jxl.Workbook; 
import jxl.write.*; 

import md.sergiu.db.*;

@Path ("/v1/export_csv")
public class V1_export_csv {
	@GET
	 public static void main(String[] args) throws InterruptedException {

     	WritableWorkbook wworkbook;
         try {
        	wworkbook = Workbook.createWorkbook(new File("C:\\Users\\shyen\\Desktop\\orders_export.xls"));
       			
        		datasource connect = new datasource();
        		Connection conn = connect.datasource();
        		PreparedStatement query=null;
        		ResultSet rs=null;
			if (conn == null ) System.out.println("vanea");
			 
	 			query=conn.prepareStatement("Select * FROM orders");
	 			rs = query.executeQuery();
		 						
				 WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
				 Label label = new Label(0, 0, "A label record");
				 wsheet.addCell(label);
		         int i=0;
				 
		           
		           int j=1;
				while(rs.next()){
					
					i=0;
					
					 label = new Label(i++, j, j+"");
					  wsheet.addCell(label);
					 label = new Label(i++, j, rs.getString("order_id"));
					  wsheet.addCell(label);
					  label = new Label(i++, j, rs.getString("prod_qty"));
					  wsheet.addCell(label);
					  label = new Label(i++, j, rs.getString("total_price"));
					  wsheet.addCell(label);
					  
					  
					  
					 
					j++;
				}
				
				
				
         
         wworkbook.write();
         wworkbook.close();
         System.out.println("finished");
         
         
         
         
         
         } catch (Exception e) {
        	 e.printStackTrace();
			}
     }
}
