package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Branches;
import dao.BranchesDao;

public class BranchesService {

	public List<Branches> getBranches() {

		Connection connection = null;
		try {
			connection = getConnection();

			BranchesDao branchesDao = new BranchesDao();
			List<Branches> ret = branchesDao.getBranches(connection);

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
