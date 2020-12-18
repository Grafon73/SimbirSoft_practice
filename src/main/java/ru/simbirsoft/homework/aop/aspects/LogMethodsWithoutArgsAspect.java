package ru.simbirsoft.homework.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled} == true && ${aspect.logmethodswithoutargs} == true")
public class LogMethodsWithoutArgsAspect {
    @AfterReturning(pointcut ="@within(ru.simbirsoft.homework.aop.annotations.LogMethodsWithoutArgs)")
    public void after(JoinPoint joinPoint){
        if(joinPoint.getArgs().length == 0){
            log.info("\n Был вызван метод "+ joinPoint.getSignature().getName() + " без аргументов");
        }
    }
}
