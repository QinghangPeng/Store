package com.store.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的Servlet
 */
public class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			//获取子类
			Class clazz = this.getClass();
			
			//获取请求的方法
			String m = req.getParameter("method");
			if(m == null) {
				m = "index";
			}
			
			//获取方法对象
			Method method = clazz.getMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			
			//让方法执行，返回值为请求转发路径
			String s = (String)method.invoke(this, req, res);
			
			//判断s是否为空
			if(s != null ) {
				req.getRequestDispatcher(s).forward(req, res);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 
	}
	
	public String index(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		return null;
	}

}
