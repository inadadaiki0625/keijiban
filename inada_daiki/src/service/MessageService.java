package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Message;
import beans.UserContribution;
import dao.MessageDao;
import dao.UserContributionDao;
public class MessageService {

	public void register (Message message) {

		Connection connection = null;
		try{
			connection = getConnection();
			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

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


	public List<UserContribution> getMessage(String category,String start,String end) {

		Connection connection = null;
		List<UserContribution> ret = null;
		try {
			connection = getConnection();

			UserContributionDao messageDao = new UserContributionDao();
			ret = messageDao.getUserContributions(connection,category,start,end);

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


	public void deleteMessage (int contributions_id) {

		Connection connection = null;
		try{
			connection = getConnection();
			MessageDao messageDao = new MessageDao();
			messageDao.delete(connection, contributions_id);

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

	public List<UserContribution> getCtegorys() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserContributionDao UserContributionDao = new UserContributionDao();
			List<UserContribution> ret = UserContributionDao.getCtegorys(connection);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}

	}
}
