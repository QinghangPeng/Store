package com.store.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.User;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年10月19日 下午10:53:38
 * 
 */
public class AdminUserFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		//强转
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		//判断管理员是否登录
		User adminUser = (User) request.getSession().getAttribute("adminUser");
		if (adminUser == null) {
			request.setAttribute("msg", "请登录！");
			request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
			return;
		}
		//放行
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
