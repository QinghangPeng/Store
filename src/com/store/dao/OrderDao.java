package com.store.dao;

import java.util.List;

import com.store.domain.Order;
import com.store.domain.OrderItem;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月20日 下午10:50:33
 * 
 */
public interface OrderDao {

	void add(Order order) throws Exception;

	void addItem(OrderItem orderItem) throws Exception;

	List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception;

	int getTotalCount(String uid) throws Exception;

	Order getById(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

	List<Order> findAllByState(String state) throws Exception;

}
