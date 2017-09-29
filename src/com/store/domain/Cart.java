package com.store.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月18日 下午9:21:15
 * 
 */
public class Cart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//存放购物车项的集合 key:商品的id
	private Map<String, CartItem> map = new LinkedHashMap<>();
	
	//总金额
	private Double total = 0.0;
	
	/**
	 * 获取所有的购物车项
	 * @Description:
	 * @return
	 * @author 作者 penghao
	 * @since：2017年9月18日 下午11:05:07
	 */
	public Collection<CartItem> getItems() {
		return map.values();
	}
	
	//添加到购物车
	public void add2Cart(CartItem cartItem) {
		//判断购物车中有无该商品
		String pid = cartItem.getProduct().getPid();
		//有
		if(map.containsKey(pid)) {
			//设置购买数量  之前的购买数量+现在的购买数量
			CartItem oItem = map.get(pid);
			oItem.setCount(oItem.getCount()+cartItem.getCount());
		} else {
			//将购物车项添加进去
			map.put(pid, cartItem);
		}
		
		//修改金额
		total += cartItem.getSubtotal();
	}
	
	//从购物车删除
	public void removeFromCart(String pid) {
		//从集合中移除
		CartItem item = map.remove(pid);
		
		//修改金额
		total -= item.getSubtotal();
	}
	
	//清空购物车
	public void clearCart() {
		map.clear();
		total = 0.0;
	}
	
	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
