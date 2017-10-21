package com.store.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.store.constant.Constant;
import com.store.domain.User;
import com.store.service.UserService;
import com.store.service.impl.UserServiceImpl;
import com.store.utils.BeanFactory;
import com.store.utils.MD5Utils;
import com.store.utils.UUIDUtils;

/**
 * 和用户相关的servlet
 */
public class UserServlet extends BaseServlet {
	
	/**
	 * 跳转到注册界面
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author 作者 penghao
	 * @since：2017年9月7日 下午7:58:32
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "jsp/register.jsp";
	}
	
	/**
	 * 用户注册
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @author 作者 penghao
	 * @throws Exception 
	 * @since：2017年9月7日 下午8:10:57
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//封装数据
		User user = new User();
		BeanUtils.populate(user, request.getParameterMap());
		//设置用户id
		user.setUid(UUIDUtils.getId());
		//设置激活码
		user.setCode(UUIDUtils.getCode());
		//加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		
		//调用service完成注册
		UserService service = (UserService) BeanFactory.getBean("UserService");
		service.regist(user);
		//页面请求转发
		request.setAttribute("msg", "用户注册成功，请到邮箱激活~~~");
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 用户激活
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年9月11日 下午10:22:48
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取激活码
		String code = request.getParameter("code");
		//调用service完成激活
		UserService s = (UserService) BeanFactory.getBean("UserService");
		User user = s.active(code);
		if(user == null) {
			//通过激活码没有找到用户
			request.setAttribute("msg", "请重新激活");
		}else {
			request.setAttribute("msg", "激活成功，请登录");
		}
		//请求转发
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 跳转到登录界面
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author 作者 penghao
	 * @since：2017年9月12日 上午12:31:57
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "jsp/login.jsp";
	}
	
	/**
	 * 登录
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @author 作者 penghao
	 * @throws Exception 
	 * @since：2017年9月12日 上午12:33:17
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		
		UserService s = (UserService) BeanFactory.getBean("UserService");
		User user = s.login(username,password);
		
		if(user == null) {
			//用户密码不匹配
			request.setAttribute("msg", "用户名密码不匹配");
			return "/jsp/login.jsp";
		} else {
			//判断用户是否激活
			if (Constant.USER_IS_ACTIVE != user.getState()) {
				request.setAttribute("msg", "用户未激活");
				return "/jsp/login.jsp";
			}
		}
		
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/");
		
		return null;
	}
	
	/**
	 * 用户退出
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author 作者 penghao
	 * @since：2017年9月12日 上午1:06:41
	 */
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//干掉session并重定向
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath());
		return null;
	}
	
	/**
	 * 管理员登录
	 * @Description:
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author 作者 penghao
	 * @since：2017年10月19日 下午11:07:16
	 */
	public String adminUserLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		
		UserService s = (UserService) BeanFactory.getBean("UserService");
		User adminUser = s.login(username,password);
		
		if(adminUser == null) {
			//用户密码不匹配
			request.setAttribute("msg", "用户名密码不匹配");
			return "/admin/index.jsp";
		}
		
		request.getSession().setAttribute("adminUser", adminUser);
		response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
		
		return null;
	}

}
