import java.sql.*;   
import COM.ibm.db2.app.*;   

public class AppMgrSP extends StoredProc
{
  	public void insertProduct (	String product_id,
								String product_name,
								String short_name,
								String description,
								String supplier_id, 
								String url 			) throws Exception
  	{
    	// get caller's connection to the database; inherited from StoredProc
    	Connection connection = getConnection ();

    	// calculate the total payroll and average salary before the increase
    	Statement statement = connection.createStatement ();
    	String sql = "INSERT INTO isvapps VALUES('" + product_id + "','" + 
												 	product_name + "','" + 
												 	short_name + "','" + 
												 	description + "'," +
													"null,null,null,null,null,null,'" +
												 	supplier_id + "',null,'" +
												 	url + "',null)" ;
													
  
  											
    	int rowsAffected = statement.executeUpdate(sql);
		statement.close() ;
		connection.close() ;
  	}
}