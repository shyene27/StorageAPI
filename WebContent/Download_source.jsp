
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>

<%@page import="md.sergiu.db.*"%> 

<%@page import="java.sql.*"%> 
<%@page import="java.io.*" %>



<% 
String filename = "C:\\Users\\shyen\\Desktop\\orders_export1.csv";

Connection conn = null; 
String returnString = null;
Statement stmt;

try
{
FileWriter fw = new FileWriter(filename);
fw.append("Order ID");
fw.append(',');
fw.append("Prod Qty");
fw.append(',');
fw.append("Total Price");
fw.append(',');
fw.append("Order Date");
fw.append(',');
fw.append("Customer ID");
fw.append('\n');


datasource connect = new datasource();
conn = connect.datasource();
String query = "Select * FROM orders";
stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next())
{
fw.append(rs.getString(1));
fw.append(',');
fw.append(rs.getString(2));
fw.append(',');
fw.append(rs.getString(3));
fw.append(',');
fw.append(rs.getString(4));
fw.append(',');
fw.append(rs.getString(5));
fw.append('\n');
}
fw.flush();
fw.close();
conn.close();
out.println("<b>You have Successfully Created Csv file.</b>");
} catch (Exception e) {
e.printStackTrace();
}
%>
</body>
</html>
