package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection con = null;
	
	public static Connection getConnection() {
		if(con == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				con = DriverManager.getConnection(url, props);
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return con;
	}
	
	public static void closeConnection() {
		if(con != null) {
			try {
				con.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		try {
			rs.close();
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
}
