package com.store.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <p>Title: 实体工厂类</p>
 *
 * <p>Description: </p>
 *
 * <p>Company: OpenSource Company</p>
 *
 * @author 作者 penghao
 * 
 * @since：2017年9月18日 上午12:12:28
 * 
 */
public class BeanFactory {
	public static Object getBean(String id) {
		//通过id获取一个指定的实现类
		try {
			Document doc = new SAXReader().read(
					BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			Element ele = (Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			//获取bean对象的class属性
			String value = ele.attributeValue("class");
			//反射
			//return Class.forName(value).newInstance();
			//对service中的add操作进行加强，打印操作日志
			final Object object = Class.forName(value).newInstance();
			if(id.endsWith("Service")) {
				Object proxyObj = Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//继续判断是否调用的add或者regist
						if("add".equals(method.getName()) || "regist".equals(method.getName())){
							System.out.println("添加操作");
							return method.invoke(object, args);
						}
						
						return method.invoke(object, args);
					}
				});
				
				//若是service方法返回的是代理对象
				return proxyObj;
			}
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
