package com.smoke.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

/**
 * Created by CHEN on 2016/12/8.
 */
@Aspect
public class ErrorInterceptor {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.smoke.web.*.*(..))")
    private void sayError() {}

    @Before("sayError()")
    public void doBefore() {
        System.out.println("开始");
    }

    @AfterThrowing(pointcut = "sayError()",throwing = "e")
    public void doAfterThrow(Throwable e) {
        System.out.println("有异常");
        logger.error(e.getMessage());
    }


}
