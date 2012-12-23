package com.cycrypt.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDao {
	protected Connection getConnection() throws SQLException
	{
		return ConnectionPool.getConnection();
	}
	
	protected void closeResource(Statement stmt,ResultSet rs,Connection conn) throws SQLException
	{
			if(stmt!=null)
				stmt.close();
			if(rs!=null)
				rs.close();
			if(conn!=null)
				conn.close();
	}
}
