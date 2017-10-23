package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import beans.Branches;
import beans.Departments;
import beans.User;
import exception.NoRowsUpdatedRuntimeException;
import service.BranchesService;
import service.DepartmentsService;
import service.UserService;

@WebServlet(urlPatterns = { "/settings" })
@MultipartConfig(maxFileSize = 100000)
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		if (StringUtils.isEmpty(request.getParameter("id")) || request.getParameter("id") == null) {
			messages.add("不正なIDです");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("management");
			return;
		} else if (!request.getParameter("id").matches("^\\d{1,8}$")) {
			messages.add("不正なIDです");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("management");
			return;
		}

		User editUser = new UserService().getUsers(Integer.parseInt(request.getParameter("id")));
		if(editUser == null) {
			messages.add("不正なIDです");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("management");
			return;
		}

		request.setAttribute("editUser", editUser);

		List<Branches> branch = new BranchesService().getBranches();
		request.setAttribute("branches", branch);

		List<Departments> department = new DepartmentsService().getDepartments();
		request.setAttribute("departments",department);

		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		User editUser = getEditUser(request);

		if (isValid(request, messages) == true) {

			try {
				new UserService().update(editUser);

			} catch (NoRowsUpdatedRuntimeException e) {
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("settings");
			}
			User loginUser = (User) session.getAttribute("loginUser");

			if (editUser.getId()== loginUser.getId()){

				loginUser.setName(editUser.getName());
				session.setAttribute("loginUser", loginUser);
			}
			response.sendRedirect("management");

		} else {
			session.setAttribute("errorMessages", messages);

			request.setAttribute("editUser", editUser);

			List<Branches> branch = new BranchesService().getBranches();
			request.setAttribute("branches", branch);

			List<Departments> department = new DepartmentsService().getDepartments();
			request.setAttribute("departments",department);

			request.getRequestDispatcher("settings.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request)throws IOException, ServletException {

		User editUser = new User();

		editUser.setLoginId(request.getParameter("loginId"));
		editUser.setName(request.getParameter("name"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setBranchId (Integer.parseInt(request.getParameter("branchId")));
		editUser.setDepartmentId (Integer.parseInt(request.getParameter("departmentId")));
		editUser.setId (Integer.parseInt(request.getParameter("id")));

		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String loginId = request.getParameter("loginId");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		String branchId = request.getParameter("branchId");
		String departmentId = request.getParameter("departmentId");
		int id = Integer.parseInt(request.getParameter("id"));

		User loginIdCheck = new UserService().loginIdCheck (loginId);

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");

		} else if (!loginId.matches("[a-zA-Z0-9]{6,20}")) {
			messages.add("ログインIDは半角英数字6～20文字で入力してください");

		} else if (loginIdCheck != null && loginIdCheck.getId() != id) {
			messages.add("ログインIDが重複しています");
		}

		if (StringUtils.isEmpty(password) != true) {
			if (!password.matches("[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]{6,20}")) {
				messages.add("パスワードは半角英数字記号6～20文字で入力してください");
			} else if (!password.equals(checkPassword)){
				messages.add("パスワードと確認用パスワードが一致しません");
			}
		}

		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		} else if (!name.matches("[\\W\\w]{1,10}")) {
			messages.add("名前は1～10文字で入力してください");
		}

		if (branchId.equals("1")) {
			if(departmentId.equals("3") || departmentId.equals("4")){
				messages.add("支店と部署・役職の組み合わせが適切ではありません");
			}
		}

		if (branchId.equals("2") || branchId.equals("3") || branchId.equals("4")) {
			if(departmentId.equals("1") || departmentId.equals("2")){
				messages.add("支店と部署・役職の組み合わせが適切ではありません");
			}
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
