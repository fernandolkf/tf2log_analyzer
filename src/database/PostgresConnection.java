package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnection {
	
	private Connection conn = null;
	private Statement statment = null;
	private ResultSet rs = null;

	public PostgresConnection(String dbName, String user, String password){
		      
	      try {
	         Class.forName("org.postgresql.Driver");
	         conn = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/"+dbName,
	            user, password );
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	   
	   }
	
	public void executeSQL(String sql){
		try {
			this.getStatment().executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
   public Statement getStatment() {
	   try{
		   statment = conn.createStatement();
	   }catch(SQLException e){
		   e.printStackTrace();
	   }	
	return statment;
}

	public void setStatment(Statement statment) {
		this.statment = statment;
	}

	public ResultSet getRs(String sql) {
		try {
			rs = this.getStatment().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
	   

}



