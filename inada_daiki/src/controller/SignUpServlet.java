package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import beans.Branches;
import beans.Departments;
import beans.User;
import service.BranchesService;
import service.DepartmentsService;
import service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Branches> branch = new BranchesService().getBranches();
		request.setAttribute("branches", branch);

		List<Departments> department = new DepartmentsService().getDepartments();
		request.setAttribute("departments",department);

		request.getRequestDispatcher("signup.jsp").forward(request, response);


	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String loginId = request.getParameter("loginId");
		String name = request.getParameter("name");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int departmentId = Integer.parseInt(request.getParameter("departmentId"));
		int isWorking = Integer.parseInt(request.getParameter("isWorking"));

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();
		if (isValid(request, messages) == true) {

			User user = new User();
			user.setLoginId(request.getParameter("loginId"));
			user.setPassword(request.getParameter("password"));
			user.setName(request.getParameter("name"));
			user.setBranchId (Integer.parseInt(request.getParameter("branchId")));
			user.setDepartmentId (Integer.parseInt(request.getParameter("departmentId")));
			user.setIsWorking (Integer.parseInt(request.getParameter("isWorking")));

			new UserService().register(user);

			response.sendRedirect("management");

		} else {
			List<Branches> branch = new BranchesService().getBranches();
			request.setAttribute("branches", branch);

			List<Departments> department = new DepartmentsService().getDepartments();
			request.setAttribute("departments",department);

			session.setAttribute("errorMessages", messages);
			request.setAttribute("loginId",loginId);
			request.setAttribute("name", name);
			request.setAttribute("branchId",branchId);
			request.setAttribute("departmentId",departmentId);
			request.setAttribute("isWorking",isWorking);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("loginId");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		String branchId = request.getParameter("branchId");
		String departmentId = request.getParameter("departmentId");

		User loginIdCheck = new UserService().loginIdCheck (loginId);

		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		} else if (!loginId.matches("[a-zA-Z0-9]{6,20}")) {
			messages.add("ログインIDは半角英数字で6～20文字で入力してください");
		}

		if(loginIdCheck != null) {
			messages.add("ログインIDが重複しています");
		}

		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		} else if (!name.matches("[\\W\\w]{1,10}")) {
			messages.add("名前は1～10文字で入力してください");
		}

		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		} else if (!password.matches("[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]{6,20}")) {
			messages.add("パスワードは半角英数字記号で6～20文字で入力してください");
		} else if (!password.equals(checkPassword)){
			messages.add("パスワードと確認用パスワードが一致しません");
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
