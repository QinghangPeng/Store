package com.store.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
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
			return Class.forName(value).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
