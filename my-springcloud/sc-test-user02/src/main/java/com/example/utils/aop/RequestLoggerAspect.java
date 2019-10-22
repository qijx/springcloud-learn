package com.example.utils.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc 用于接口日志切面
 */
@Component
@Slf4j
@Aspect
public class RequestLoggerAspect {

    /**
     * 第一个*代表返回类型不限，第二个*表示所有类，第三个*表示所有方法，..两个点表示方法里的参数不限
     */
    @Pointcut("execution(public * com.example.controller.*.*(..))")
    public void pointCut(){}

    /**
     * 切入点
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object aroundAspectLogger(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature=pjp.getSignature();
        String className=pjp.getTarget().getClass().getSimpleName();
        String methodName=signature.getName();
        long startTime=System.currentTimeMillis();
        Object result=null;
        try {
             result=pjp.proceed();
        }finally {
            log.info("类名 : {} ,方法名 : {} ,参数 : {},耗时 : {},结果 : {}",className,methodName,pjp.getArgs(),System.currentTimeMillis()-startTime,result);
            JSONObject json=new JSONObject();
            json.put("logType", "promotionCoreApiMonitor");
            json.put("methodName",methodName);
            json.put("className", className);
            json.put("args[]", buildArgs(pjp.getArgs()));
            json.put("timeCosting", System.currentTimeMillis()-startTime);
            json.put("resultCode",result==null?-1:0);
            log.warn(json.toJSONString());
        }
        return result;
    }

    public String buildArgs(Object[] args){
        if(ArrayUtils.isEmpty(args)){
            return "";
        }
        List<Object> argList = Arrays.asList(args).stream().filter(arg ->
                (!(arg instanceof HttpServletResponse)&&(!(arg instanceof HttpServletRequest))
                )
        ).collect(Collectors.toList());
        return argList.toString();
    }
}


