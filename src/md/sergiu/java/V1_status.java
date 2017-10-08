package md.sergiu.java;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import md.sergiu.db.*;

@Path("/v1/status/")
public class V1_status {

private static final String api_verison ="00.01.00";
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<p>Java Web Service</p>";
	}
	
@Path("/test")	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version: </p>" + api_verison;
	}

@Path ("/db")
@GET
@Produces(MediaType.TEXT_HTML)
public String returnData() throws Exception {
datasource connect = new datasource();
String returnString = connect.getData();
return returnString;
}
}

