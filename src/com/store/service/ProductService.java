package com.store.service;

import java.util.List;

import com.store.domain.PageBean;
import com.store.domain.Product;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月15日 上午12:32:51
 * 
 */
public interface ProductService {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getByPid(String pid) throws Exception;

	PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void add(Product product) throws Exception;

	void update(Product product) throws Exception;

	List<Product> findAllDown() throws Exception;

	List<Product> selectProduct(String pname) throws Exception;

	void updateProduct(Product product) throws Exception;

}
