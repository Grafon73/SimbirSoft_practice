package ru.simbirsoft.homework.aop.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled} == true && ${aspect.logexception} == true")
public class LogExceptionAspect {

    @AfterThrowing(value = "@annotation(ru.simbirsoft.homework.aop.annotations.LogException)",throwing = "ex")
    public void logExceptions(Exception ex){
       log.error("\n Exception: "+ ex.getMessage());
    }
}
