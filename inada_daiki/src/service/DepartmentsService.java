package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Departments;
import dao.DepartmentsDao;

public class DepartmentsService {
	public List<Departments> getDepartments() {

		Connection connection = null;
		try {
			connection = getConnection();

			DepartmentsDao departmentsDao = new DepartmentsDao();
			List<Departments> ret = departmentsDao.getDepartments(connection);

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
