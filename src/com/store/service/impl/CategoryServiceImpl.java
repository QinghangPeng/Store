package com.store.service.impl;

import java.util.List;

import com.store.dao.CategoryDao;
import com.store.dao.impl.CategoryDaoImpl;
import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BeanFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月12日 下午9:59:47
 * 
 */
public class CategoryServiceImpl implements CategoryService {
	
	/**
	 * 查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		//创建缓存管理器
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.
				getClassLoader().getResourceAsStream("ehcache.xml"));
		Cache cache = cm.getCache("categoryCache");
		Element element = cache.get("cList");
		List<Category> list = null;
		if (element == null) {
			//从数据库中获取并将list放入缓存
			CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
			list = dao.findAll();
			cache.put(new Element("cList",list));
			//System.out.println("从数据库中获取");
		} else {
			list = (List<Category>) element.getObjectValue();
			//System.out.println("缓存中有数据");
		}
		
		return list;
	}

	@Override
	public void add(Category category) throws Exception {
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		dao.add(category);
		
		//更新缓存(清空缓存即可)
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.
				getClassLoader().getResourceAsStream("ehcache.xml"));
		Cache cache = cm.getCache("categoryCache");
		cache.remove("cList");
	}

}
