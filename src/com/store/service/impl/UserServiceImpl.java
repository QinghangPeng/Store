package com.store.service.impl;

import com.store.dao.UserDao;
import com.store.dao.impl.UserDaoImpl;
import com.store.domain.User;
import com.store.service.UserService;
import com.store.utils.BeanFactory;
import com.store.utils.MailUtils;

public class UserServiceImpl implements UserService{

	@Override
	public void regist(User user) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDao");
		dao.add(user);
		//发送邮件
		String emailMsg = "欢迎注册成为我们的一员，<a href='http://localhost:8080/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}
	
	/**
	 * 用户激活
	 * @throws Exception 
	 */
	@Override
	public User active(String code) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDao");
		//通过code获取用户并修改用户的状态
		User user = dao.getByCode(code);
		
		if(user == null) {
			return null;
		}
		
		user.setState(1);
		dao.updateUser(user);
		
		return user;
		
	}

	@Override
	public User login(String username, String password) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDao");
		User user = dao.getByUsernameAndPwd(username,password);
		return user;
	}

}
