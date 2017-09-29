package com.store.dao;

import java.util.List;

import com.store.domain.Category;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月12日 下午10:00:43
 * 
 */
public interface CategoryDao {

	List<Category> findAll() throws Exception;

}
