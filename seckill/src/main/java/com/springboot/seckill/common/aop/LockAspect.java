package com.springboot.seckill.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步锁 AOP
 * 创建
 * 创建时间
 */
@Component
@Scope
@Aspect
public class LockAspect {
	/**
     * 思考：为什么不用synchronized
     * service 默认是单例的，并发下lock只有一个实例
     */
	private static  Lock lock = new ReentrantLock(true);//互斥锁 参数默认false，不公平锁  
	
	//Service层切点     用于记录错误日志
	@Pointcut("@annotation(com.springboot.seckill.common.aop.Servicelock)")
	public void lockAspect() {
		
	}
	
    @Around("lockAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) { 
    	lock.lock();
    	Object obj = null;
		try {
			obj = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
    	return obj;
    } 
}
