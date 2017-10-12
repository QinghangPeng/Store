package com.store.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.service.OrderService;
import com.store.utils.BeanFactory;
import com.store.utils.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 后台订单模块
 */
public class AdminOrderServlet extends BaseServlet {

	/**
	 * 查询订单
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月11日 下午10:04:36
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String state = request.getParameter("state");
		OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
		List<Order> list = orderService.findAllByState(state);
		request.setAttribute("list", list);
		return "/admin/order/list.jsp";
	}
	
	/**
	 * 查询订单详情
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月11日 下午10:39:06
	 */
	public String getDetailByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String oid = request.getParameter("oid");
		OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
		List<OrderItem> items = orderService.getById(oid).getItems();
		//排除不用写回去的数据
		JsonConfig configJson = JsonUtil.configJson(new String[]{"class","itemid","order"});
		JSONArray json = JSONArray.fromObject(items, configJson);
		response.getWriter().println(json);
		return null;
	}
	
	/**
	 * 修改订单的状态
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月12日 下午9:17:08
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oid = request.getParameter("oid");
		String state = request.getParameter("state");
		OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
		Order order = orderService.getById(oid);
		order.setState(2);
		orderService.updateOrder(order);
		//重定向到未发货的界面
		response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		return null;
	}

}
