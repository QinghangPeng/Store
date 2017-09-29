package com.store.service.impl;

import java.util.List;

import com.store.dao.OrderDao;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.PageBean;
import com.store.domain.User;
import com.store.service.OrderService;
import com.store.utils.BeanFactory;
import com.store.utils.DataSourceUtils;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月20日 下午10:50:20
 * 
 */
public class OrderServiceImpl implements OrderService {

	@Override
	public void add(Order order) throws Exception {
		try {
			//开启事务
			DataSourceUtils.startTransaction();
			OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
			orderDao.add(order);
			for (OrderItem orderItem : order.getItems()) {
				orderDao.addItem(orderItem);
			}
			//事务处理
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
	}
	
	/**
	 * 分页查询订单
	 */
	@Override
	public PageBean<Order> finAllByPage(int currPage, int pageSize, User user) throws Exception {
		//查询当前页数据以及总条数
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		List<Order> list = orderDao.findAllByPage(currPage,pageSize,user.getUid());
		int totalCount = orderDao.getTotalCount(user.getUid());
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	
	/**
	 * 查看订单详情
	 */
	@Override
	public Order getById(String oid) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		return orderDao.getById(oid);
	}
	
	/**
	 * 更新订单信息
	 */
	@Override
	public void updateOrder(Order order) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		orderDao.updateOrder(order);
	}

}
