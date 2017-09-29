package com.store.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Cart;
import com.store.domain.CartItem;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;

/**
 * 购物车模块
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取购物车
	 * @Description:
	 * @param request
	 * @return
	 * @author 作者 penghao
	 * @since：2017年9月18日 下午10:54:20
	 */
	public Cart getCart(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//判断购物车是否为空
		if(cart == null) {
			//创建一个cart并放入到session中
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		return cart;
	}
	
	/**
	 * 添加到购物车
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @author 作者 penghao
	 * @throws Exception 
	 * @since：2017年9月18日 下午10:42:51
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		
		//通过pid获取商品的信息
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		Product product = ps.getByPid(pid);
		
		//组装成订单项对象
		CartItem cartItem = new CartItem(product, count);
		
		//添加到购物车
		Cart cart = getCart(request);
		cart.add2Cart(cartItem);
		
		//重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	/**
	 * 删除购物车
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年9月18日 下午11:34:39
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = request.getParameter("pid");
		getCart(request).removeFromCart(pid);
		//重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	/**
	 * 清空购物车
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年9月18日 下午11:47:38
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
		getCart(request).clearCart();
		//重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}

}
