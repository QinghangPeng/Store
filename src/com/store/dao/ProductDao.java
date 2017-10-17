package com.store.dao;

import java.util.List;

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
 * @since：2017年9月15日 上午12:33:43
 * 
 */
public interface ProductDao {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getByPid(String pid) throws Exception;

	List<Product> findByPage(int currPage, int pageSize, String cid) throws Exception;

	int getTotalCount(String cid) throws Exception;

	void updateCid(String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void add(Product product) throws Exception;

	void update(Product product) throws Exception;

	List<Product> findAllDown() throws Exception;

	List<Product> selectProduct(String pname) throws Exception;

}
