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

import beans.Message;
import beans.User;
import service.MessageService;

@WebServlet(urlPatterns = {"/message"})
public class MessageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request,
			HttpServletResponse response) throws IOException,
	ServletException {

		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	@Override
	protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		if (isValid(request, messages) == true) {

			User user = (User) session.getAttribute("loginUser");

			Message message = new Message();
			message.setSubject(request.getParameter("subject"));
			message.setCategory(request.getParameter("category"));
			message.setText(request.getParameter("text"));

			message.setBranch_id (user.getBranchId());
			message.setDepartment_id (user.getDepartmentId());
			message.setUser_id (user.getId());

			new MessageService().register(message);
			response.sendRedirect("./");
		} else {
			String text = request.getParameter("text");
			String subject = request.getParameter("subject");
			String category = request.getParameter("category");
			session.setAttribute("errorMessages",messages);
			request.setAttribute("subject",subject);
			request.setAttribute("category",category);
			request.setAttribute("text", text);
			request.getRequestDispatcher("message.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String>messages) {

		String text = request.getParameter("text");
		String subject = request.getParameter("subject");
		String category = request.getParameter("category");

		if (StringUtils.isEmpty(subject) == true) {
			messages.add("件名を入力してください");
		} else if (subject.matches("\\s*")) {
			messages.add("件名が空白のみの投稿はできません");
		} else if (30 < subject.length()) {
			messages.add("件名は30文字以内で入力してください");
		}

		if (StringUtils.isEmpty(category) == true) {
			messages.add("カテゴリーを入力してください");
		} else if (category.matches("\\s*")) {
			messages.add("カテゴリーが空白のみの投稿はできません");
		} else if (10 < category.length()) {
			messages.add("カテゴリーは10文字以内で入力してください");
		}

		if (StringUtils.isEmpty(text) == true) {
			messages.add("本文を入力してください");
		} else if (text.matches("\\s*")) {
			messages.add("本文が空白のみの投稿はできません");
		} else if (1000 < text.length()) {
			messages.add("本文は1000文字以内で入力してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
