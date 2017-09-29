package com.store.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BeanFactory;

/**
 * 后台分类管理模块
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询所有的分类信息
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = categoryService.findAll();
		request.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}

}
