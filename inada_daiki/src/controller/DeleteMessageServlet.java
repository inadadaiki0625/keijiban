package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MessageService;

@WebServlet(urlPatterns = { "/deletemessage" })
public class DeleteMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {

		int contributions_id = Integer.parseInt(request.getParameter("contributions_id"));
		new MessageService().deleteMessage(contributions_id);

		response.sendRedirect("./");

	}

}
