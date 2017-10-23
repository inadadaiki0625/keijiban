package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Branches;
import exception.SQLRuntimeException;

public class BranchesDao {
	public List<Branches> getBranches(Connection connection){

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM branches");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Branches> ret = toBranchesList(rs);
			return ret;
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<Branches> toBranchesList(ResultSet rs) throws SQLException {
		List<Branches> ret = new ArrayList<Branches>();
		try {
			while (rs.next()) {

				int id = rs.getInt("id");
				String name = rs.getString("name");

				Branches branch = new Branches();
				branch.setId(id);
				branch.setName(name);

				ret.add(branch);
			}return ret;
		} finally {
			close(rs);
		}
	}
}

