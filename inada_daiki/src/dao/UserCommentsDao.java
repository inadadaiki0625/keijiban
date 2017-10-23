package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.UserComments;
import exception.SQLRuntimeException;

public class UserCommentsDao {
	public List<UserComments> getComment(Connection connection){

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_comments");
			sql.append(" ORDER BY created_at") ;

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComments> ret = toCommentsList(rs);
			return ret;
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<UserComments> toCommentsList(ResultSet rs) throws SQLException {
		List<UserComments> ret = new ArrayList<UserComments>();
		try {
			while (rs.next()) {
				String text = rs.getString("text");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				int userId = rs.getInt("user_id");
				int contributionId = rs.getInt("contribution_id");
				String name = rs.getString("name");
				int commentId = rs.getInt("comment_id");
				Timestamp createdAt = rs.getTimestamp("created_at");

				UserComments comment = new UserComments();
				comment.setText(text);
				comment.setBranchId(branchId);
				comment.setDepartmentId(departmentId);
				comment.setUserId(userId);
				comment.setContributionId(contributionId);
				comment.setName(name);
				comment.setCommentId(commentId);
				comment.setCreatedAt(createdAt);


				ret.add(comment);
			}return ret;
		} finally {
			close(rs);
		}
	}
}
