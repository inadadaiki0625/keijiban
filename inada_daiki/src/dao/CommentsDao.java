package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.Comments;
import exception.SQLRuntimeException;

public class CommentsDao {

	public void insert (Connection connection, Comments comment) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments (");
			sql.append(" text");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", user_id");
			sql.append(", contribution_id");
			sql.append(", created_at");
			sql.append(", updated_at");
			sql.append(")VALUES(");
			sql.append(" ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1,comment.getText());
			ps.setInt(2, comment.getBranchId());
			ps.setInt(3, comment.getDepartmentId());
			ps.setInt(4, comment.getUserId());
			ps.setInt(5, comment.getContributionId());
			//System.out.println(ps.toString());
			ps.executeUpdate();

		} catch (SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete (Connection connection, int commentsId) {

		PreparedStatement ps = null;
		try{
			String sql = "DELETE FROM comments where id = ?";

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1,commentsId);
			System.out.println(ps.toString());
			ps.executeUpdate();

		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
