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

import beans.Comments;
import beans.User;
import service.CommentsService;

@WebServlet(urlPatterns = {"/comments"})
public class CommentsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {

		response.sendRedirect("./");
	}

	@Override
	protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		if (isValid(request, messages) == true) {

			User user = (User) session.getAttribute("loginUser");

			Comments comment = new Comments();
			comment.setText(request.getParameter("text"));
			comment.setBranchId (user.getBranchId());
			comment.setDepartmentId (user.getDepartmentId());
			comment.setUserId (user.getId());
			comment.setContributionId(Integer.parseInt(request.getParameter("contributions_id")));

			new CommentsService().register(comment);
			response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessages",messages);
			response.sendRedirect("./");
		}
	}


	private boolean isValid(HttpServletRequest request, List<String>messages) {

		String text = request.getParameter("text");

		if (StringUtils.isEmpty(text) == true){
			messages.add("コメントを入力してください");
		} else if (500< text.length()){
			messages.add("コメントは500文字以内で入力してください");
		} else if (text.matches("\\s*")) {
			messages.add("空白のみの投稿はできません");
		}

		if(messages.size() == 0 ){
			return true;
		} else {
			return false;
		}
	}

}
