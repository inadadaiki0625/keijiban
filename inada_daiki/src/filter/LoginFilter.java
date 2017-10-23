package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import service.UserService;


@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
																		throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();

		User loginUser = (User) session.getAttribute("loginUser");

		String path = ((HttpServletRequest)request).getServletPath();
		List<String> message = new ArrayList<String>();

		if(!path.matches(".*/css.*")){

			if(!path.equals("/login")){

				if(loginUser == null){
					message.add("ログインしてください");
					session.setAttribute("errorMessages", message);
					((HttpServletResponse)response).sendRedirect("login");
					return;
				}else if (loginUser.getIsWorking()!=1){
					message.add("ログインに失敗しました");
					session.setAttribute("errorMessages", message);
					((HttpServletResponse)response).sendRedirect("login");
					return;
				}

				new UserService().getUsers(loginUser.getId());
				session.setAttribute("loginUser",new UserService().getUsers(loginUser.getId()));
				session.getAttribute("loginUser");
				loginUser=(User) session.getAttribute("loginUser");

			}if(path.equals("/management")){

				if(loginUser.getDepartmentId() != 1){
					message.add("不正なアクセスです");
					session.setAttribute("errorMessages", message);
					((HttpServletResponse)response).sendRedirect("./");
					return;
				}

			}if(path.equals("/settings")){

				if(loginUser.getDepartmentId() != 1){
					message.add("不正なアクセスです");
					session.setAttribute("errorMessages", message);
					((HttpServletResponse)response).sendRedirect("./");
					return;
				}
			}if(path.equals("/signup")){

				if(loginUser.getDepartmentId() != 1){
					message.add("不正なアクセスです");
					session.setAttribute("errorMessages", message);
					((HttpServletResponse)response).sendRedirect("./");
					return;
				}
			}
		}
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	@Override
	public void destroy() {

	}


}
