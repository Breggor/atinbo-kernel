package com.atinbo.core.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class WebUtil {

    /**
     * 从request获取所有的参数
     *
     * @param request
     * @return
     */
//	public static Map<String, Object> bindParamToMap(HttpServletRequest request)
//			throws FileUploadException {
//		Map<String, Object> map = new HashMap<String, Object>();
//		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//		if (isMultipart == true) {
//			FileItemFactory factory = new DiskFileItemFactory();
//			ServletFileUpload upload = new ServletFileUpload(factory);
//			List<FileItem> items;
//
//			items = upload.parseRequest(request);
//			Iterator<FileItem> itr = items.iterator();
//			while (itr.hasNext()) {
//				FileItem item = (FileItem) itr.next();
//
//				if (item.isFormField()) {
//					map.put(item.getFieldName(), item.getString());
//				} else {
//					MultipartFile multipartFile = new CommonsMultipartFile(item);
//					map.put(item.getFieldName(),multipartFile);
//				}
//			}
//		} else {
//			Enumeration<?> enumer = request.getParameterNames();
//			while (enumer.hasMoreElements()) {
//				String key = (String) enumer.nextElement();
//				map.put(key, request.getParameter(key));
//			}
//		}
//
//		return map;
//	}
    public static String getRequestParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        StringBuilder paramString = new StringBuilder();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                paramString.append(key).append("=").append(value).append("&");
            }
        }

        //去除末尾多加的一个&符号
        if (paramString.length() > 0) {
            return paramString.substring(0, paramString.length() - 1);
        } else {
            return "";
        }
    }
}
