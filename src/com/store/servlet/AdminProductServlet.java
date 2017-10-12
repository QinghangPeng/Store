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
import com.store.utils.BeanFactory;

/**
 * 后台商品管理
 */
public class AdminProductServlet extends BaseServlet {

	/**
	 * 查询所有商品
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月10日 下午10:09:28
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductService pService = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = pService.findAll();
		
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
	}
	
	/**
	 * 跳转到添加商品的界面
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月10日 下午10:09:55
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询所有的分类
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
		List<Category> cList = categoryService.findAll();
		request.setAttribute("cList", cList);
		return "/admin/product/add.jsp";
	}
	
	/**
	 * 商品上下架
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月10日 下午10:10:36
	 */
	public String changeTheShelf(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = request.getParameter("pid");
		String pflag = request.getParameter("pflag");
		ProductService pService = (ProductService) BeanFactory.getBean("ProductService");
		Product product = pService.getByPid(pid);
		//改变状态
		product.setPflag(Integer.parseInt(pflag));
		pService.update(product);
		//重定向
		if("1".equals(pflag)) {
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		} else {
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAllDown");
		}
		
		return null;
	}
	
	/**
	 * 查询所有下架商品
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月12日 下午9:57:29
	 */
	public String findAllDown(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductService pService = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = pService.findAllDown();
		
		request.setAttribute("list", list);
		return "/admin/product/list_down.jsp";
	}

}
