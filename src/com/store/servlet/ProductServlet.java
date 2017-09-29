package com.store.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImpl;
import com.store.utils.BeanFactory;

/**
 * 商品servlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 通过id查询单个商品详情
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author 作者 penghao
	 * @since：2017年9月16日 下午9:57:17
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取商品的id
		String pid = request.getParameter("pid");
		ProductService ps = new ProductServiceImpl();
		Product product = new Product();
		try {
			product = ps.getByPid(pid);
			request.setAttribute("bean", product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/product_info.jsp";
	}
	
	/**
	 * 分页查询数据
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author 作者 penghao
	 * @since：2017年9月16日 下午9:56:36
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取当前页，设置一个pageSize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12;
		
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		try {
			PageBean<Product> pageBean = ps.findByPage(currPage,pageSize,cid);
			request.setAttribute("pageBean", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/product_list.jsp";
	}

}
