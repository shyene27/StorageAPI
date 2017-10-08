package md.sergiu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class datasource {
    private Statement st;
    private Connection con;
    private ResultSet rs;

    public Connection datasource() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/store";
            String username = "root";
            String password = "admin";
            Class.forName(driver);

            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();


        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public String getData() throws Exception
    
    {
    	String returnString = null;
    	String name = null;
        String phone = null;
        try {
            String query = "SELECT * FROM customers";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("first_name");
                phone = rs.getString("phone");
            }
                   
            returnString = ("<p> Name: " + name + "  Phone: " + phone + "</p>");
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return returnString;
    }
}
