package com.store.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;

/**
 * 后台商品管理
 */
public class AdminProductServlet extends BaseServlet {

	/**
	 * @throws Exception 
	 * 
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductService pService = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = pService.findAll();
		
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
	}

}
