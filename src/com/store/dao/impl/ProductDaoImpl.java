package com.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.store.dao.ProductDao;
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
 * @since：2017年9月15日 上午12:34:07
 * 
 */
public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findNew() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	@Override
	public List<Product> findHot() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = 1 order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	@Override
	public Product getByPid(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ? limit 1";
		return qr.query(sql, new BeanHandler<>(Product.class),pid);
	}

	@Override
	public List<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Product.class),cid,(currPage - 1)*pageSize,pageSize);
	}

	@Override
	public int getTotalCount(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(),cid)).intValue();
	}
	
	/**
	 * 更新商品的cid，为删除分类时做准备
	 */
	@Override
	public void updateCid(String cid) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "update product set cid = null where cid = ?";
		qr.update(DataSourceUtils.getConnection(), sql, cid);
	}

	@Override
	public List<Product> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = 0";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	@Override
	public void add(Product product) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql,product.getPid(),product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),
				product.getIs_hot(),product.getPdesc(),product.getPflag(),
				product.getCategory().getCid());
	}

	@Override
	public void update(Product product) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pflag = ? where pid = ? ";
		qr.update(sql,product.getPflag(),product.getPid());
	}

	@Override
	public List<Product> findAllDown() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = 1";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}

	@Override
	public List<Product> selectProduct(String pname) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname like ?";
		return qr.query(sql, new BeanListHandler<>(Product.class),"%"+pname+"%");
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname = ?,market_price = ?,shop_price = ?,"
				+ "pimage = ?,pdate = ?,is_hot = ?,pdesc = ?,cid = ? where pid = ? ";
		qr.update(sql,product.getPname(),product.getMarket_price(),product.getShop_price(),
				product.getPimage(),product.getPdate(),product.getIs_hot(),
				product.getPdesc(),product.getCategory().getCid(),product.getPid());
	}

}
