package edu.hfut.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class DemoTypeHandler implements TypeHandler<String> {

	@Override
	public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(i, parameter + "DL");
	}

	@Override
	public String getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getString(columnName) + "DL";
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getString(columnIndex) + "DL";
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return cs.getString(columnIndex) + "DL";
	}

}
