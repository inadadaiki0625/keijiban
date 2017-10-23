package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Comments;
import beans.UserComments;
import dao.CommentsDao;
import dao.UserCommentsDao;

public class CommentsService {
	public void register (Comments comment) {

		Connection connection = null;
		try{
			connection = getConnection();
			CommentsDao commentDao = new CommentsDao();
			commentDao.insert(connection, comment);

			commit(connection);
		}catch (RuntimeException e) {
			rollback(connection);
			throw e;
		}catch (Error e) {
			rollback(connection);
			throw e;
		}finally {
			close (connection);
		}
	}


	public List<UserComments> getComment() {

		Connection connection = null;
		List<UserComments> ret = null;
		try {
			connection = getConnection();

			UserCommentsDao userCommentsDao = new UserCommentsDao();
			ret = userCommentsDao.getComment(connection);

			commit (connection);

		} catch (RuntimeException e){
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);

		} finally {
				close(connection);
		}
		return ret;
	}

	public void deleteComment (int commentId) {

		Connection connection = null;
		try{
			connection = getConnection();
			CommentsDao commentsDao = new CommentsDao();
			commentsDao.delete(connection, commentId);

			commit(connection);
		}catch (RuntimeException e) {
			rollback(connection);
			throw e;
		}catch (Error e) {
			rollback(connection);
			throw e;
		}finally {
			close (connection);
		}
	}
}
