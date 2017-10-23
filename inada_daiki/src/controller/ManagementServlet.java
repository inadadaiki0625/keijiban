package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Branches;
import beans.Departments;
import beans.User;
import service.BranchesService;
import service.DepartmentsService;
import service.ManagementService;
import service.UserService;

@WebServlet(urlPatterns = { "/management" })
public class ManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		List<User> user = new ManagementService().getUser();
		request.setAttribute("users", user);

		List<Branches> branch = new BranchesService().getBranches();
		request.setAttribute("branches", branch);

		List<Departments> department = new DepartmentsService().getDepartments();
		request.setAttribute("departments",department);

		request.getRequestDispatcher("/management.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		User isWorking = new User();
		isWorking.setId(Integer.parseInt(request.getParameter("id")));
		isWorking.setIsWorking(Integer.parseInt(request.getParameter("isWorking")));

		new UserService().isWorkingUpdate(isWorking);

		response.sendRedirect("management");
	}
}
