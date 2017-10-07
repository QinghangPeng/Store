package com.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.store.dao.CategoryDao;
import com.store.domain.Category;
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
 * @since：2017年9月12日 下午10:01:10
 * 
 */
public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource()); 
		String sql = "select * from category";
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

	@Override
	public void add(Category category) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource()); 
		String sql = "insert into category values(?,?)";
		qr.update(sql,category.getCid(),category.getCname());
	}

	@Override
	public Category getById(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category where cid = ?";
		return qr.query(sql, new BeanHandler<>(Category.class),cid);
	}

	@Override
	public void update(Category category) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update category set cname = ? where cid = ?";
		qr.update(sql, category.getCname(),category.getCid());
	}

	@Override
	public void delete(String cid) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "delete from category where cid = ?";
		qr.update(DataSourceUtils.getConnection(),sql,cid);
	}

}
