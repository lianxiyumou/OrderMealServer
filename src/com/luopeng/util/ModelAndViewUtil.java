package com.luopeng.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.luopeng.comm.RC4;
import com.luopeng.model.ResponseModel;

public class ModelAndViewUtil {

	
	public static void write(ResponseModel rsp,HttpServletResponse response){
		String objstr = GsonUtil.obj2string(rsp);
		String enCodeStr = RC4.encryptionRC4String(objstr, RC4.KEY);
		System.out.println("write enCodeStr:"+enCodeStr);
		writeString(objstr, response);
	}
	
	// 获取写出流，写出StringBuffer
	public static void writeStringBuffer(StringBuffer stringBuffer,
			HttpServletResponse response) {
		writeString(stringBuffer.toString(), response);
	}

	// 获取写出流，写出StringBuilder
	public static void writeStringBuilder(StringBuilder stringBuilder,
			HttpServletResponse response) {
		writeString(stringBuilder.toString(), response);
	}

	// //获取写出流，写出String
	public static void writeString(String string, HttpServletResponse response) {
		PrintWriter print = null;
		try {
			print = response.getWriter();
			print.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			print.close();
		}
	}	
	
	
	public static Map<String, String> getMultiParamterMap(
			HttpServletRequest request, final List<FileItem> fileList)
			throws FileUploadException {
		Map<String, String> param = new TreeMap<String, String>();
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// upload.setFileSizeMax(10 * (1 << 20));//去掉
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (Exception e) {
//			log.warning(e.toString());
			// throw new LeagException("att_too_large");
		}
		for (int i = 0; i < items.size(); i++) {
			FileItem item = items.get(i);
			if (item.isFormField()) {
				try {
					param.put(item.getFieldName(), item.getString("utf-8"));
				} catch (UnsupportedEncodingException e) {
				}
			} else if (StringUtils.isNotEmpty(item.getName())) {
				fileList.add(item);
			}
		}
		return param;
	}	
	
	public static Map<String, String> getMultiParamterMap(HttpServletRequest request){
		Map<String,String> paramMap = null ;
		List<FileItem> files = new ArrayList<FileItem>();
		try {
			getMultiParamterMap(request, files);
			if(!files.isEmpty()){
				FileItem file = files.get(0);
				String params = RC4.decryptionRC4(file.get(), RC4.KEY);
				paramMap = MStringUtils.getMapFromString(params, "&");
				System.out.println("getMultiParamterMap :"+params);
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramMap;
	}
	public static Map<String, String> getMultiParamterMap2(HttpServletRequest request){
	   String params = request.getParameter("params");
	   System.out.println("--------------------");
	   String decodeParams = RC4.decryptionRC4(params, RC4.KEY);
	   System.out.println("decodeParams: "+decodeParams);
	   return MStringUtils.getMapFromString(decodeParams, "&");
	   
	}
	
}
