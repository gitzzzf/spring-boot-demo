package com.example.demo.aop.aspect;

import com.example.demo.aop.DemoAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-17 下午9:32
 **/
@Component
@Aspect
public class DemoAnnotationAspect {

    @Pointcut("@annotation(com.example.demo.aop.DemoAnnotation)")
    public void demoAspect(){}

    @Around("demoAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        // 方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 类和方法名
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        System.out.println(String.format("正在执行的方法：%s - %s", className, methodName));
        // 注解信息
        DemoAnnotation annotation = method.getAnnotation(DemoAnnotation.class);
        int id = annotation.id();
        System.out.println(String.format("方法已经正常执行，id = %s", id));

        return joinPoint.proceed();
    }

}
