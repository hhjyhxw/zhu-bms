package com.zhumeng.config.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 手机端全局拦截器
 * zdh
 */
public class LoginInterceptorNew_bak implements HandlerInterceptor {
	
	public final static Logger log = LoggerFactory.getLogger(LoginInterceptorNew_bak.class);
//	private BeanFansService beanFansService = SpringContextHolder.getBean(BeanFansService.class);
	
	@Override
	public void afterCompletion(HttpServletRequest reqeust, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest reqeust, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
	
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		printlnVisitInfo(request,handler);
//		BeanFans beanFans = beanFansService.findByKey(16L);
		//更新缓存
		request.getSession().setAttribute("beanFans","userInfo");
//		//图片访问路径
		request.getSession().setAttribute("pictureVisitUrl","http://localhost:8080/");
		return true;
	}
	
	public Object getBean(String beanName, HttpServletRequest request){
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	    return factory.getBean(beanName); 
	}
	
	
	/**
	 * @Description: 打印日志
	 * @param        @param request
	 * @param        @param handler
	 * @param        @throws IOException    
	 * @return       void 
	 * @throws
	 * @author       z
	 * @datetime     2018年7月3日上午10:50:26
	 */
	private void printlnVisitInfo(HttpServletRequest request, Object handler) throws IOException {
		 // 所有请求第一个进入的方法  
        String reqURL = request.getRequestURL().toString();
//        String ip = IpUtil.getIpAddr(request);
        InputStream is = request.getInputStream ();
        StringBuilder responseStrBuilder = new StringBuilder();
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        String inputStr;
         while ((inputStr = streamReader.readLine ()) != null)  
         responseStrBuilder.append (inputStr);  
         String parmeter = responseStrBuilder.toString();
           
       long startTime = System.currentTimeMillis();
       request.setAttribute("startTime", startTime);  
       if (handler instanceof HandlerMethod) {
           StringBuilder sb = new StringBuilder(1000);
           HandlerMethod h = (HandlerMethod) handler;
           //Controller 的包名  
           sb.append("\nController: ").append(h.getBean().getClass().getName()).append("\n");  
           //方法名称  
           sb.append("Method    : ").append(h.getMethod().getName()).append("\n");  
           //请求方式  post\put\get 等等  
           sb.append("RequestMethod    : ").append(request.getMethod()).append("\n");  
           //所有的请求参数  
           sb.append("Params    : ").append(parmeter).append("\n");  
           //部分请求链接  
           sb.append("URI       : ").append(request.getRequestURI()).append("\n");  
            //完整的请求链接  
           sb.append("AllURI    : ").append(reqURL).append("\n");  
           //请求方的 ip地址  
//           sb.append("request IP: ").append(ip).append("\n");
		   log.info("URI==="+request.getRequestURI());
		   log.info(sb.toString());
       }
	}

	
}
