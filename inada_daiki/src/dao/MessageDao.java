package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.Message;
import exception.SQLRuntimeException;


public class MessageDao {

	public void insert (Connection connection, Message message) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO contributions (");
			sql.append(" subject");
			sql.append(", text");
			sql.append(", category");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", user_id");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(")VALUES(");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getSubject());
			ps.setString(2, message.getText());
			ps.setString(3, message.getCategory());
			ps.setInt(4, message.getBranch_id());
			ps.setInt(5, message.getDepartment_id());
			ps.setInt(6, message.getUser_id());
			//System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete (Connection connection, int contributions_id) {

		PreparedStatement ps = null;
		try{
			String sql = "DELETE FROM contributions where id = ?";

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1,contributions_id);
			ps.executeUpdate();

		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
