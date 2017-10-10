package com.store.service.impl;

import java.util.List;

import com.store.dao.ProductDao;
import com.store.dao.impl.ProductDaoImpl;
import com.store.domain.PageBean;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月15日 上午12:33:24
 * 
 */
public class ProductServiceImpl implements ProductService {
	
	/**
	 * 查询最新商品
	 */
	@Override
	public List<Product> findNew() throws Exception {
		ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
		return pdao.findNew();
	}
	
	/**
	 * 查询热门商品
	 */
	@Override
	public List<Product> findHot() throws Exception {
		ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
		return pdao.findHot();
	}
	
	/**
	 * 通过id查询单个商品详情
	 */
	@Override
	public Product getByPid(String pid) throws Exception {
		ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
		return pdao.getByPid(pid);
	}
	
	/**
	 * 按类别分页查询商品
	 */
	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
		//当前页数据
		List<Product> list = pdao.findByPage(currPage,pageSize,cid);
		//总条数
		int totalCount = pdao.getTotalCount(cid);
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	
	/**
	 * 查询所有
	 */
	@Override
	public List<Product> findAll() throws Exception {
		ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
		return pdao.findAll();
	}
	
	/**
	 * 添加商品
	 */
	@Override
	public void add(Product product) throws Exception {
		ProductDao pdao = (ProductDao) BeanFactory.getBean("ProductDao");
		pdao.add(product);
	}

}
