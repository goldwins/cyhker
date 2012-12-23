package com.cycrypt.module.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.cycrypt.Config;
import com.cycrypt.core.BaseMysqlJDBCDao;

public class AccountDaoJDBCMySQL extends BaseMysqlJDBCDao implements IAccountDao {

	Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public boolean saveAccount(Account account) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into account(cy_id,email,pass) values(?,?,?)";
		boolean result = false;
		try {
			conn = this.getWriteConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, account.getCyId());
			pstmt.setObject(2, account.getEmail());
			pstmt.setObject(3, account.getPass());
			result = pstmt.executeUpdate()>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}finally
		{
			//doesn't need to close connection
			if(pstmt!=null)
			{
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public Integer getLastCyId() {
		// TODO Auto-generated method stub
		Statement st = null;
		ResultSet rs = null;
		String sql="select max(t.cy_id) cy_id from account t";
		String result=null;
		Config cfg = Config.getInstance();
		String rangeMask = cfg.getCyIdRangeMask();
		String baseCyIdIfNull = null;
		if(rangeMask!=null&&rangeMask.trim().length()>0)
		{
			sql+=" where t.cy_id like '"+rangeMask+"%'";
			baseCyIdIfNull = rangeMask+"000000";
			
		}
		try {
			Connection conn = this.getReadConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next())
			{
				result = rs.getString("cy_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result!=null&&!"".equals(result))
		{
			return Integer.valueOf(result);
		}else
		{
			log.info("no last cyId found,use base cyId to start:("+baseCyIdIfNull+"-1)");
			return Integer.valueOf(baseCyIdIfNull)-1;
		}
	}
	
}
