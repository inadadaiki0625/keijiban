package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Departments;
import exception.SQLRuntimeException;

public class DepartmentsDao {
	public List<Departments> getDepartments(Connection connection){

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM departments");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Departments> ret = toDepartmentsList(rs);
			return ret;
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<Departments> toDepartmentsList(ResultSet rs) throws SQLException {
		List<Departments> ret = new ArrayList<Departments>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				 Departments department = new  Departments();
				department.setId(id);
				department.setName(name);

				ret.add(department);
			}return ret;
		} finally {
			close(rs);
		}
	}

}
