package com.zhumeng.config;

import com.alibaba.fastjson.JSONObject;
import com.zhumeng.annotation.SysLog;
import com.zhumeng.modules.sys.dto.UserInfo;
import com.zhumeng.modules.sys.entity.Log;
import com.zhumeng.modules.sys.service.ILogService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = Logger.getLogger(getClass());

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Autowired
    private ILogService ilogService;

	@Pointcut("execution(public * com.zhumeng.modules..web..*.*(..))")
	public void webLog() {
	}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 请求参数
        Object[] args = joinPoint.getArgs();
        String requestParam = "";
        if (args != null && args.length > 0){
            try {
                requestParam = JSONObject.toJSONString(args[0]);
            }catch (Exception e){

            }
        }

        // 记录下请求内容
        logger.info("url : " + request.getRequestURL().toString());
        logger.info("http_method : " + request.getMethod());
        logger.info("id : " + request.getRemoteAddr());
        logger.info("class_method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("args : " + requestParam);

        // 添加系统操作日志
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        SysLog sysLog = targetMethod.getAnnotation(SysLog.class);
        if (sysLog!=null){
            UserInfo userInfo = (UserInfo)SecurityUtils.getSubject().getPrincipal();
            Log log = new Log();
            log.setUserId(userInfo.getId());
            log.setUserName(userInfo.getUserName());
            log.setOperMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            log.setRequestParam(requestParam);
            log.setOperDesc(sysLog.value());
            ilogService.insert(log);
        }

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("respones : " + ret);
        logger.info("spend time : " + (System.currentTimeMillis() - startTime.get()));
    }

}