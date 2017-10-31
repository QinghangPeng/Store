package com.store.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.store.domain.Category;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;
import com.store.utils.UUIDUtils;
import com.store.utils.UploadUtils;

/**
 * 商品后台修改
 */
public class EditProductServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//创建一个map,接收前台传递的数据
			HashMap<String, Object> map = new HashMap<>();
			
			//创建磁盘文件项
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem fileItem : list) {
				//判断是否为普通上传组件
				if(fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
				} else {
					//文件上传组件
					//获取文件名称
					String name = fileItem.getName();
					//获取文件真实名称
					String realName = UploadUtils.getRealName(name);
					//获取文件随机名称
					String uuidName = UploadUtils.getUUIDName(realName);
					//获取文件的存放路径
					String path = this.getServletContext().getRealPath("/products/1");
					
					//获取文件流
					InputStream iStream = fileItem.getInputStream();
					//保存图片
					FileOutputStream outputStream = new FileOutputStream(new File(path,uuidName));
					IOUtils.copy(iStream, outputStream);
					iStream.close();
					outputStream.close();
					
					//删除临时文件
					fileItem.delete();
					
					//在map中设置路径
					map.put(fileItem.getFieldName(), "products/1/"+uuidName);
				}
			}
			
			Product product = new Product();
			BeanUtils.populate(product, map);
			product.setPid((String)map.get("pid"));
			product.setPflag(Integer.parseInt(map.get("pflag").toString()));
			product.setPdate(new Date());
			Category category = new Category();
			category.setCid((String)map.get("cid"));
			product.setCategory(category);
			
			String[] image = product.getPimage().split("\\.");
			if (image.length <= 1) {
				product.setPimage((String)map.get("opimage"));
			}
			
			
			ProductService pService = (ProductService) BeanFactory.getBean("ProductService");
			pService.updateProduct(product);
			
			//重定向
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "商品修改失败");
			request.getRequestDispatcher("/jsp/msg.jsp");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
