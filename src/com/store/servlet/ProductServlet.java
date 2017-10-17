package com.store.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImpl;
import com.store.utils.BeanFactory;
import com.store.utils.CookUtils;

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
			//获取指定的cookie
			Cookie cookie = CookUtils.getCookieByName("pids", request.getCookies());
			String pids = "";
			if(cookie == null) {
				pids = pid;
			}else {
				//获取值
				pids = cookie.getValue();
				String[] splits = pids.split("-");
				//此list长度不变
				List<String> list = Arrays.asList(splits);
				LinkedList<String> linkedList = new LinkedList<>(list);
				if(linkedList.contains(pid)) {
					//若包含pid  那么将pid移除并放到最前面
					linkedList.remove(pid);
					linkedList.addFirst(pid);
				} else {
					//若不包含pid 继续判断长度是否大于6
					if(linkedList.size() > 5) {
						linkedList.removeLast();
						linkedList.addFirst(pid);
					} else {
						linkedList.addFirst(pid);
					}
				}
				pids = "";
				for (String s : linkedList) {
					pids += (s+"-");
				}
				pids = pids.substring(0,pids.length()-1);
			}
			cookie = new Cookie("pids", pids);
			//设置访问路径
			cookie.setPath(request.getContextPath()+"/");
			//设置存活时间
			cookie.setMaxAge(3600);
			//写会浏览器
			response.addCookie(cookie);
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
	
	/**
	 * 搜索商品
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @author 作者 penghao
	 * @throws Exception 
	 * @since：2017年10月17日 下午10:10:29
	 */
	public String selectProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pname = request.getParameter("pname");
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		List<Product> list = ps.selectProduct(pname);
		request.setAttribute("list", list);
		return "/jsp/product_search.jsp";
	}
}
