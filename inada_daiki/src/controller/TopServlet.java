package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import beans.UserComments;
import beans.UserContribution;
import service.BranchesService;
import service.CommentsService;
import service.DepartmentsService;
import service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

		String start = request.getParameter("start");
		/*if(start == null || StringUtils.isEmpty(start)) {
			start = "2017-10-01";
		}*/

		String end = request.getParameter("end");
		if(end == null || StringUtils.isEmpty(end)) {
			Date date = new Date();
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy/MM/dd");
			String endDate = d1.format(date); //
			end = endDate;
		}

		String category =request.getParameter("category");

		HttpSession session = request.getSession();

		session.setAttribute("start", start);
		session.setAttribute("end", end);
		session.setAttribute("category", category);
		List<UserContribution> messages = new MessageService().getMessage(category,start,end);
		request.setAttribute("messages", messages);


		List<UserComments> comments = new CommentsService().getComment();
		request.setAttribute("comments", comments);

		List<UserContribution> categorys = new MessageService().getCtegorys();
		request.setAttribute("categorys",categorys);

		List<Branches> branch = new BranchesService().getBranches();
		request.setAttribute("branches", branch);

		List<Departments> department = new DepartmentsService().getDepartments();
		request.setAttribute("departments",department);

		request.getRequestDispatcher("/top.jsp").forward(request, response);

	}

}
