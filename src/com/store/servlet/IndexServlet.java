package com.store.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.domain.Product;
import com.store.service.CategoryService;
import com.store.service.ProductService;
import com.store.service.impl.CategoryServiceImpl;
import com.store.service.impl.ProductServiceImpl;
import com.store.utils.BeanFactory;

/**
 * 和首页相关的servlet
 */
public class IndexServlet extends BaseServlet {
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//去数据库中查询最新商品和热门商品，将它们放入到request域中，请求转发
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> newList = null;
		List<Product> hotList = null;
		try {
			//查询最新商品
			newList = ps.findNew();
			//查询热门商品
			hotList = ps.findHot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("newList", newList);
		request.setAttribute("hotList", hotList);
		return "/jsp/index.jsp";
	}

}
