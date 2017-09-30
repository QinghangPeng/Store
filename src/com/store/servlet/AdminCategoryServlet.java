package com.store.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BeanFactory;
import com.store.utils.UUIDUtils;

/**
 * 后台分类信息查询
 */
public class AdminCategoryServlet extends BaseServlet {
	
	/**
	 * 展示所有分类
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月1日 上午12:19:42
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询所有的分类信息
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> list = categoryService.findAll();
		request.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}
	
	/**
	 * 跳转到添加页面上
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月1日 上午12:19:53
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/category/add.jsp";
	}
	
	/**
	 * 添加分类
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月1日 上午12:28:18
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
		categoryService.add(category);
		response.sendRedirect(request.getContextPath()+"/adminCategoryServlet?method=findAll");
		return null;
	}

}
