package com.store.service;

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
 * @since：2017年9月12日 下午9:58:59
 * 
 */
public interface CategoryService {

	List<Category> findAll() throws Exception;

}
