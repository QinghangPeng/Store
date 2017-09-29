package com.store.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.store.dao.OrderDao;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.Product;
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
 * @since：2017年9月20日 下午10:50:52
 * 
 */
public class OrderDaoImpl implements OrderDao {
	
	/**
	 * 添加一条订单
	 */
	@Override
	public void add(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(); 
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql,order.getOid(),order.getOrderTime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),order.getTelephone(),
				order.getUser().getUid());
	}
	
	/**
	 * 添加一条订单项
	 */
	@Override
	public void addItem(OrderItem orderItem) throws Exception {
		QueryRunner qr = new QueryRunner(); 
		String sql = "insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql, orderItem.getItemId(),orderItem.getCount(),orderItem.getSubtotal(),
				orderItem.getProduct().getPid(),orderItem.getOrder().getOid());
	}
	
	/**
	 * 查询我的订单
	 */
	@Override
	public List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid = ? order by ordertime limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class),uid,(currPage-1)*pageSize,pageSize);
		
		//遍历订单集合，封装每个订单的订单项列表
		sql = "select * from orderitem oi,product p where oi.pid = p.pid and oi.oid = ?";
		for (Order order : list) {
			//当前订单包含的所有内容
			List<Map<String, Object>> mList = qr.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : mList) {
				OrderItem oItem = new OrderItem();
				Product product = new Product();
				
				BeanUtils.populate(product, map);
				BeanUtils.populate(oItem, map);
				
				oItem.setProduct(product);
				
				//封装成一个orderitemList
				order.getItems().add(oItem);
			}
			
		}
		return list;
	}
	
	/**
	 * 获取我的订单总条数
	 */
	@Override
	public int getTotalCount(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(),uid)).intValue();
	}

	@Override
	public Order getById(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class),oid);
		//封装orderitems
		sql = "select * from orderitem oi,product p where oi.pid=p.pid and oi.oid = ?";
		List<Map<String, Object>> list = qr.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> map : list) {
			//封装product
			Product product = new Product();
			BeanUtils.populate(product, map);
			//封装orderitem
			OrderItem oItem = new OrderItem();
			BeanUtils.populate(oItem, map);
			oItem.setProduct(product);
			//将orderitem添加到order的items中
			order.getItems().add(oItem);
		}
		return order;
	}
	
	@Override
	public void updateOrder(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set state = ?,address = ?,name = ?,telephone = ? where oid = ?";
		qr.update(sql,order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}

}
