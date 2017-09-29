package com.store.dao;

import com.store.domain.User;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月7日 下午8:36:04
 * 
 */
public interface UserDao {

	void add(User user) throws Exception;

	User getByCode(String code) throws Exception;

	void updateUser(User user) throws Exception;

	User getByUsernameAndPwd(String username, String password) throws Exception;

}
