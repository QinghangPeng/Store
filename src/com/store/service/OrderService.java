package com.store.service;

import java.util.List;

import com.store.domain.Order;
import com.store.domain.PageBean;
import com.store.domain.User;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月20日 下午10:50:00
 * 
 */
public interface OrderService {

	void add(Order order) throws Exception;

	PageBean<Order> finAllByPage(int currPage, int pageSize, User user) throws Exception;

	Order getById(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

	List<Order> findAllByState(String state) throws Exception;

}
