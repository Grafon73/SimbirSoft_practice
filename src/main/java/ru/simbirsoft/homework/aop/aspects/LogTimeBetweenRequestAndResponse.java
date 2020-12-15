package ru.simbirsoft.homework.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled} == true && ${aspect.logtime} == true")
public class LogTimeBetweenRequestAndResponse {

    private Date start;

    @Before("@within(ru.simbirsoft.homework.aop.annotations.LogTime)")
    public void before(){
    start= new Date();
    }

    @AfterReturning(pointcut ="@within(ru.simbirsoft.homework.aop.annotations.LogTime)")
    public void after(JoinPoint joinPoint){
        log.debug("\n Контроллер: "+joinPoint.getTarget().getClass().getSimpleName()
                +"\n Вызванный метод: " +joinPoint.toShortString()
                +"\n Время ответа: " + (new Date().getTime()-start.getTime()) + "мс");
    }
}
