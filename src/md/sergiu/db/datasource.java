package md.sergiu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

public class datasource {
    private Statement st;
    private Connection con;
    private ResultSet rs;
    
    public  Connection datasource() throws Exception {
        try   {
        	
        	Properties properties = new Properties();
            InputStream reader = datasource.class.getClassLoader().getResourceAsStream("/md/sergiu/db/config");
        	//FileInputStream reader = new FileInputStream("C:\\Users\\shyen\\eclipse-workspace\\StorageAPI\\config");
            properties.load(reader);

            Class.forName("com.mysql.jdbc.Driver");
            String driver = "com.mysql.jdbc.Driver";
            
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            
            //String url = "jdbc:mysql://localhost:3306/store";
            //String username = "root";
            //String password = "admin";
            Class.forName(driver);

            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();


        } catch (Exception e) {
        	e.printStackTrace();
            //System.out.println(e);
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
