package com.cube.framework.aop;

import java.lang.reflect.Method;
import java.util.UUID;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.cube.framework.annotation.Log;

@Aspect
@Component
public class LogAspect {
	
	private static Logger logger = LogManager.getLogger(LogAspect.class);

	private ThreadLocal<Long> time = new ThreadLocal<Long>();
	private ThreadLocal<String> tag = new ThreadLocal<String>();
	private Log log;

	/**
	 * 在所有标注@Log的地方切入
	 * 
	 * @param joinPoint
	 */
	@Before("@annotation(com.cube.framework.annotation.Log)")
	public void beforeExec(JoinPoint joinPoint) {
		time.set(System.currentTimeMillis());
		tag.set(UUID.randomUUID().toString());
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		log = ms.getMethod().getAnnotation(Log.class);
		info(joinPoint);
	}

	@After("@annotation(com.cube.framework.annotation.Log)")
	public void afterExec(JoinPoint joinPoint) {
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		this.showLog("标记为" + tag.get() + "的方法" + method.getName() + "运行消耗" + (System.currentTimeMillis() - time.get()) + "ms");
	}

	private void info(JoinPoint joinPoint) {
		this.showLog("--------------------------------------------------");
		this.showLog("所属对象:\t" + joinPoint.getTarget().toString());
		this.showLog("调用方法:\t" + joinPoint.getSignature());
		Object[] os = joinPoint.getArgs();
		this.showLog("参数集:");
		for (int i = 0; i < os.length; i++) {
			this.showLog("\t==>参数[" + i + "]:\t" + os[i].toString());
		}
		this.showLog("调用标记:\t" + tag.get());
		this.showLog("--------------------------------------------------");
	}
	
	private void showLog(String content) {
		switch(log.level()) {
		case ALL:
			logger.log(Level.ALL, content);
			break;
		case DEBUG:
			logger.debug(content);
			break;
		case ERROR:
			logger.error(content);
			break;
		case FATAL:
			logger.fatal(content);
			break;
		case INFO:
			logger.info(content);
			break;
		case OFF:
			logger.log(Level.OFF, content);
			break;
		case TRACE:
			logger.trace(content);
			break;
		case WARN:
			logger.warn(content);
			break;
		default:
			logger.debug(content);
			break;
		}
	}

}
