package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import beans.UserContribution;
import exception.SQLRuntimeException;

public class UserContributionDao {
	public List<UserContribution> getUserContributions(Connection connection, String category,String start,String end){

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_contributions WHERE");
			sql.append(" created_at >= ?");
			sql.append(" AND created_at <= ?");
			if (category == null || StringUtils.isEmpty(category)) {
			} else {
				sql.append(" AND category = ?");
			}
			sql.append(" ORDER BY created_at DESC ");

			ps = connection.prepareStatement(sql.toString());
			if (start== null || StringUtils.isEmpty(start)) {
				ps.setString(1, "2017-10-01");
			} else {
				ps.setString(1, start);
			}
			ps.setString(2, end + " 23:59");
			if(category == null || StringUtils.isEmpty(category)) {
			} else {
				ps.setString(3, category);
			}

			ResultSet rs = ps.executeQuery();
			List<UserContribution> ret = toUserContributionList(rs);
			return ret;
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserContribution> toUserContributionList(ResultSet rs)
		throws SQLException {

		List<UserContribution> ret = new ArrayList<UserContribution>();
		try {
			while (rs.next()) {
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				int branch_id = rs.getInt("branch_id");
				int department_id = rs.getInt("department_id");
				String name = rs.getString("name");
				Timestamp created_at = rs.getTimestamp("created_at");
				int user_id = rs.getInt("user_id");
				int contributions_id = rs.getInt("contributions_id");

				UserContribution message = new UserContribution();
				message.setSubject(subject);
				message.setText(text);
				message.setCategory(category);
				message.setBranch_id(branch_id);
				message.setDepartment_id(department_id);
				message.setName(name);
				message.setCreated_at(created_at);
				message.setUser_id(user_id);
				message.setContributions_id(contributions_id);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public List<UserContribution> getCtegorys(Connection connection){

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT category FROM contributions GROUP BY category");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserContribution> ret = toCategoryList(rs);
			return ret;
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<UserContribution> toCategoryList(ResultSet rs) throws SQLException {
		List<UserContribution> ret = new ArrayList<UserContribution>();
		try {
			while (rs.next()) {

				String category = rs.getString("category");

				UserContribution categorys = new UserContribution();
				categorys.setCategory(category);

				ret.add(categorys);
			}return ret;
		} finally {
			close(rs);
		}
	}
}
