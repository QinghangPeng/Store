package com.store.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.service.impl.CategoryServiceImpl;
import com.store.utils.BeanFactory;
import com.store.utils.JsonUtil;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	
	/**
	 * 查询所有的分类
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//查询所有的分类到主页上
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> cList = null;
		try {
			cList = cs.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//将返回值转换成json格式
		//request.setAttribute("cList", cList);
		String json = JsonUtil.list2json(cList);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(json);
		return null;
	}

}
